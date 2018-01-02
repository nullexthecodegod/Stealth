package net.minecraft.client.stream;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.lwjgl.opengl.GL11;
import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.VideoParams;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserSubscription;

public class TwitchStream implements BroadcastController.BroadcastListener, ChatController.ChatListener, IngestServerTester.IngestTestListener, IStream
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Marker STREAM_MARKER = MarkerManager.getMarker("STREAM");
    private final BroadcastController broadcastController;
    private final ChatController chatController;
    private String field_176029_e;

    /** the minecraft instance */
    private final Minecraft mc;
    private final IChatComponent twitchComponent = new ChatComponentText("Twitch");
    private final Map field_152955_g = Maps.newHashMap();
    private Framebuffer framebuffer;
    private boolean field_152957_i;

    /** stream's target fps */
    private int targetFPS = 30;
    private long field_152959_k = 0L;
    private boolean field_152960_l = false;
    private boolean loggedIn;
    private boolean field_152962_n;
    private boolean field_152963_o;
    private IStream.AuthFailureReason authFailureReason;
    private static boolean field_152965_q;

    public TwitchStream(Minecraft mcIn, final Property streamProperty)
    {
        this.authFailureReason = IStream.AuthFailureReason.ERROR;
        this.mc = mcIn;
        this.broadcastController = new BroadcastController();
        this.chatController = new ChatController();
        this.broadcastController.func_152841_a(this);
        this.chatController.func_152990_a(this);
        this.broadcastController.func_152842_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.chatController.func_152984_a("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.twitchComponent.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);

        if (streamProperty != null && !Strings.isNullOrEmpty(streamProperty.getValue()) && OpenGlHelper.framebufferSupported)
        {
            Thread var3 = new Thread("Twitch authenticator")
            {
                public void run()
                {
                    try
                    {
                        URL var1 = new URL("https://api.twitch.tv/kraken?oauth_token=" + URLEncoder.encode(streamProperty.getValue(), "UTF-8"));
                        String var2 = HttpUtil.get(var1);
                        JsonObject var3 = JsonUtils.getJsonObject((new JsonParser()).parse(var2), "Response");
                        JsonObject var4 = JsonUtils.getJsonObject(var3, "token");

                        if (JsonUtils.getBoolean(var4, "valid"))
                        {
                            String var5 = JsonUtils.getString(var4, "user_name");
                            TwitchStream.LOGGER.debug(TwitchStream.STREAM_MARKER, "Authenticated with twitch; username is {}", new Object[] {var5});
                            AuthToken var6 = new AuthToken();
                            var6.data = streamProperty.getValue();
                            TwitchStream.this.broadcastController.func_152818_a(var5, var6);
                            TwitchStream.this.chatController.func_152998_c(var5);
                            TwitchStream.this.chatController.func_152994_a(var6);
                            Runtime.getRuntime().addShutdownHook(new Thread("Twitch shutdown hook")
                            {
                                public void run()
                                {
                                    TwitchStream.this.shutdownStream();
                                }
                            });
                            TwitchStream.this.broadcastController.func_152817_A();
                            TwitchStream.this.chatController.func_175984_n();
                        }
                        else
                        {
                            TwitchStream.this.authFailureReason = IStream.AuthFailureReason.INVALID_TOKEN;
                            TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, "Given twitch access token is invalid");
                        }
                    }
                    catch (IOException var7)
                    {
                        TwitchStream.this.authFailureReason = IStream.AuthFailureReason.ERROR;
                        TwitchStream.LOGGER.error(TwitchStream.STREAM_MARKER, "Could not authenticate with twitch", var7);
                    }
                }
            };
            var3.setDaemon(true);
            var3.start();
        }
    }

    /**
     * Shuts down a steam
     */
    public void shutdownStream()
    {
        LOGGER.debug(STREAM_MARKER, "Shutdown streaming");
        this.broadcastController.statCallback();
        this.chatController.func_175988_p();
    }

    public void func_152935_j()
    {
        int var1 = this.mc.gameSettings.streamChatEnabled;
        boolean var2 = this.field_176029_e != null && this.chatController.func_175990_d(this.field_176029_e);
        boolean var3 = this.chatController.func_153000_j() == ChatController.ChatState.Initialized && (this.field_176029_e == null || this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected);

        if (var1 == 2)
        {
            if (var2)
            {
                LOGGER.debug(STREAM_MARKER, "Disconnecting from twitch chat per user options");
                this.chatController.func_175991_l(this.field_176029_e);
            }
        }
        else if (var1 == 1)
        {
            if (var3 && this.broadcastController.func_152849_q())
            {
                LOGGER.debug(STREAM_MARKER, "Connecting to twitch chat per user options");
                this.func_152942_I();
            }
        }
        else if (var1 == 0)
        {
            if (var2 && !this.isBroadcasting())
            {
                LOGGER.debug(STREAM_MARKER, "Disconnecting from twitch chat as user is no longer streaming");
                this.chatController.func_175991_l(this.field_176029_e);
            }
            else if (var3 && this.isBroadcasting())
            {
                LOGGER.debug(STREAM_MARKER, "Connecting to twitch chat as user is streaming");
                this.func_152942_I();
            }
        }

        this.broadcastController.func_152821_H();
        this.chatController.func_152997_n();
    }

    protected void func_152942_I()
    {
        ChatController.ChatState var1 = this.chatController.func_153000_j();
        String var2 = this.broadcastController.getChannelInfo().name;
        this.field_176029_e = var2;

        if (var1 != ChatController.ChatState.Initialized)
        {
            LOGGER.warn("Invalid twitch chat state {}", new Object[] {var1});
        }
        else if (this.chatController.func_175989_e(this.field_176029_e) == ChatController.EnumChannelState.Disconnected)
        {
            this.chatController.func_152986_d(var2);
        }
        else
        {
            LOGGER.warn("Invalid twitch chat state {}", new Object[] {var1});
        }
    }

    public void func_152922_k()
    {
        if (this.broadcastController.isBroadcasting() && !this.broadcastController.isBroadcastPaused())
        {
            long var1 = System.nanoTime();
            long var3 = (long)(1000000000 / this.targetFPS);
            long var5 = var1 - this.field_152959_k;
            boolean var7 = var5 >= var3;

            if (var7)
            {
                FrameBuffer var8 = this.broadcastController.func_152822_N();
                Framebuffer var9 = this.mc.getFramebuffer();
                this.framebuffer.bindFramebuffer(true);
                GlStateManager.matrixMode(5889);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.ortho(0.0D, (double)this.framebuffer.framebufferWidth, (double)this.framebuffer.framebufferHeight, 0.0D, 1000.0D, 3000.0D);
                GlStateManager.matrixMode(5888);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, 0.0F, -2000.0F);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.viewport(0, 0, this.framebuffer.framebufferWidth, this.framebuffer.framebufferHeight);
                GlStateManager.enableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                float var10 = (float)this.framebuffer.framebufferWidth;
                float var11 = (float)this.framebuffer.framebufferHeight;
                float var12 = (float)var9.framebufferWidth / (float)var9.framebufferTextureWidth;
                float var13 = (float)var9.framebufferHeight / (float)var9.framebufferTextureHeight;
                var9.bindFramebufferTexture();
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, 9729.0F);
                GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, 9729.0F);
                Tessellator var14 = Tessellator.getInstance();
                WorldRenderer var15 = var14.getWorldRenderer();
                var15.startDrawingQuads();
                var15.addVertexWithUV(0.0D, (double)var11, 0.0D, 0.0D, (double)var13);
                var15.addVertexWithUV((double)var10, (double)var11, 0.0D, (double)var12, (double)var13);
                var15.addVertexWithUV((double)var10, 0.0D, 0.0D, (double)var12, 0.0D);
                var15.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
                var14.draw();
                var9.unbindFramebufferTexture();
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5889);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
                this.broadcastController.captureFramebuffer(var8);
                this.framebuffer.unbindFramebuffer();
                this.broadcastController.submitStreamFrame(var8);
                this.field_152959_k = var1;
            }
        }
    }

    public boolean func_152936_l()
    {
        return this.broadcastController.func_152849_q();
    }

    public boolean isReadyToBroadcast()
    {
        return this.broadcastController.isReadyToBroadcast();
    }

    public boolean isBroadcasting()
    {
        return this.broadcastController.isBroadcasting();
    }

    public void func_152911_a(Metadata p_152911_1_, long p_152911_2_)
    {
        if (this.isBroadcasting() && this.field_152957_i)
        {
            long var4 = this.broadcastController.func_152844_x();

            if (!this.broadcastController.func_152840_a(p_152911_1_.func_152810_c(), var4 + p_152911_2_, p_152911_1_.func_152809_a(), p_152911_1_.func_152806_b()))
            {
                LOGGER.warn(STREAM_MARKER, "Couldn\'t send stream metadata action at {}: {}", new Object[] {Long.valueOf(var4 + p_152911_2_), p_152911_1_});
            }
            else
            {
                LOGGER.debug(STREAM_MARKER, "Sent stream metadata action at {}: {}", new Object[] {Long.valueOf(var4 + p_152911_2_), p_152911_1_});
            }
        }
    }

    public void func_176026_a(Metadata p_176026_1_, long p_176026_2_, long p_176026_4_)
    {
        if (this.isBroadcasting() && this.field_152957_i)
        {
            long var6 = this.broadcastController.func_152844_x();
            String var8 = p_176026_1_.func_152809_a();
            String var9 = p_176026_1_.func_152806_b();
            long var10 = this.broadcastController.func_177946_b(p_176026_1_.func_152810_c(), var6 + p_176026_2_, var8, var9);

            if (var10 < 0L)
            {
                LOGGER.warn(STREAM_MARKER, "Could not send stream metadata sequence from {} to {}: {}", new Object[] {Long.valueOf(var6 + p_176026_2_), Long.valueOf(var6 + p_176026_4_), p_176026_1_});
            }
            else if (this.broadcastController.func_177947_a(p_176026_1_.func_152810_c(), var6 + p_176026_4_, var10, var8, var9))
            {
                LOGGER.debug(STREAM_MARKER, "Sent stream metadata sequence from {} to {}: {}", new Object[] {Long.valueOf(var6 + p_176026_2_), Long.valueOf(var6 + p_176026_4_), p_176026_1_});
            }
            else
            {
                LOGGER.warn(STREAM_MARKER, "Half-sent stream metadata sequence from {} to {}: {}", new Object[] {Long.valueOf(var6 + p_176026_2_), Long.valueOf(var6 + p_176026_4_), p_176026_1_});
            }
        }
    }

    public boolean isPaused()
    {
        return this.broadcastController.isBroadcastPaused();
    }

    public void requestCommercial()
    {
        if (this.broadcastController.requestCommercial())
        {
            LOGGER.debug(STREAM_MARKER, "Requested commercial from Twitch");
        }
        else
        {
            LOGGER.warn(STREAM_MARKER, "Could not request commercial from Twitch");
        }
    }

    /**
     * pauses a stream
     */
    public void pause()
    {
        this.broadcastController.func_152847_F();
        this.field_152962_n = true;
        this.updateStreamVolume();
    }

    /**
     * unpauses a stream
     */
    public void unpause()
    {
        this.broadcastController.func_152854_G();
        this.field_152962_n = false;
        this.updateStreamVolume();
    }

    public void updateStreamVolume()
    {
        if (this.isBroadcasting())
        {
            float var1 = this.mc.gameSettings.streamGameVolume;
            boolean var2 = this.field_152962_n || var1 <= 0.0F;
            this.broadcastController.setPlaybackDeviceVolume(var2 ? 0.0F : var1);
            this.broadcastController.setRecordingDeviceVolume(this.func_152929_G() ? 0.0F : this.mc.gameSettings.streamMicVolume);
        }
    }

    public void func_152930_t()
    {
        GameSettings var1 = this.mc.gameSettings;
        VideoParams var2 = this.broadcastController.func_152834_a(formatStreamKbps(var1.streamKbps), formatStreamFps(var1.streamFps), formatStreamBps(var1.streamBytesPerPixel), (float)this.mc.displayWidth / (float)this.mc.displayHeight);

        switch (var1.streamCompression)
        {
            case 0:
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
                break;

            case 1:
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
                break;

            case 2:
                var2.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
        }

        if (this.framebuffer == null)
        {
            this.framebuffer = new Framebuffer(var2.outputWidth, var2.outputHeight, false);
        }
        else
        {
            this.framebuffer.createBindFramebuffer(var2.outputWidth, var2.outputHeight);
        }

        if (var1.streamPreferredServer != null && var1.streamPreferredServer.length() > 0)
        {
            IngestServer[] var3 = this.func_152925_v();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5)
            {
                IngestServer var6 = var3[var5];

                if (var6.serverUrl.equals(var1.streamPreferredServer))
                {
                    this.broadcastController.func_152824_a(var6);
                    break;
                }
            }
        }

        this.targetFPS = var2.targetFps;
        this.field_152957_i = var1.streamSendMetadata;
        this.broadcastController.func_152836_a(var2);
        LOGGER.info(STREAM_MARKER, "Streaming at {}/{} at {} kbps to {}", new Object[] {Integer.valueOf(var2.outputWidth), Integer.valueOf(var2.outputHeight), Integer.valueOf(var2.maxKbps), this.broadcastController.func_152833_s().serverUrl});
        this.broadcastController.func_152828_a((String)null, "Minecraft", (String)null);
    }

    public void stopBroadcasting()
    {
        if (this.broadcastController.stopBroadcasting())
        {
            LOGGER.info(STREAM_MARKER, "Stopped streaming to Twitch");
        }
        else
        {
            LOGGER.warn(STREAM_MARKER, "Could not stop streaming to Twitch");
        }
    }

    public void func_152900_a(ErrorCode p_152900_1_, AuthToken p_152900_2_) {}

    public void func_152897_a(ErrorCode p_152897_1_)
    {
        if (ErrorCode.succeeded(p_152897_1_))
        {
            LOGGER.debug(STREAM_MARKER, "Login attempt successful");
            this.loggedIn = true;
        }
        else
        {
            LOGGER.warn(STREAM_MARKER, "Login attempt unsuccessful: {} (error code {})", new Object[] {ErrorCode.getString(p_152897_1_), Integer.valueOf(p_152897_1_.getValue())});
            this.loggedIn = false;
        }
    }

    public void func_152898_a(ErrorCode p_152898_1_, GameInfo[] p_152898_2_) {}

    public void func_152891_a(BroadcastController.BroadcastState p_152891_1_)
    {
        LOGGER.debug(STREAM_MARKER, "Broadcast state changed to {}", new Object[] {p_152891_1_});

        if (p_152891_1_ == BroadcastController.BroadcastState.Initialized)
        {
            this.broadcastController.func_152827_a(BroadcastController.BroadcastState.Authenticated);
        }
    }

    public void func_152895_a()
    {
        LOGGER.info(STREAM_MARKER, "Logged out of twitch");
    }

    public void func_152894_a(StreamInfo p_152894_1_)
    {
        LOGGER.debug(STREAM_MARKER, "Stream info updated; {} viewers on stream ID {}", new Object[] {Integer.valueOf(p_152894_1_.viewers), Long.valueOf(p_152894_1_.streamId)});
    }

    public void func_152896_a(IngestList p_152896_1_) {}

    public void func_152893_b(ErrorCode p_152893_1_)
    {
        LOGGER.warn(STREAM_MARKER, "Issue submitting frame: {} (Error code {})", new Object[] {ErrorCode.getString(p_152893_1_), Integer.valueOf(p_152893_1_.getValue())});
        this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText("Issue streaming frame: " + p_152893_1_ + " (" + ErrorCode.getString(p_152893_1_) + ")"), 2);
    }

    public void func_152899_b()
    {
        this.updateStreamVolume();
        LOGGER.info(STREAM_MARKER, "Broadcast to Twitch has started");
    }

    public void func_152901_c()
    {
        LOGGER.info(STREAM_MARKER, "Broadcast to Twitch has stopped");
    }

    public void func_152892_c(ErrorCode p_152892_1_)
    {
        ChatComponentTranslation var2;

        if (p_152892_1_ == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED)
        {
            var2 = new ChatComponentTranslation("stream.unavailable.soundflower.chat.link", new Object[0]);
            var2.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
            var2.getChatStyle().setUnderlined(Boolean.valueOf(true));
            ChatComponentTranslation var3 = new ChatComponentTranslation("stream.unavailable.soundflower.chat", new Object[] {var2});
            var3.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.mc.ingameGUI.getChatGUI().printChatMessage(var3);
        }
        else
        {
            var2 = new ChatComponentTranslation("stream.unavailable.unknown.chat", new Object[] {ErrorCode.getString(p_152892_1_)});
            var2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.mc.ingameGUI.getChatGUI().printChatMessage(var2);
        }
    }

    public void func_152907_a(IngestServerTester p_152907_1_, IngestServerTester.IngestTestState p_152907_2_)
    {
        LOGGER.debug(STREAM_MARKER, "Ingest test state changed to {}", new Object[] {p_152907_2_});

        if (p_152907_2_ == IngestServerTester.IngestTestState.Finished)
        {
            this.field_152960_l = true;
        }
    }

    public static int formatStreamFps(float p_152948_0_)
    {
        return MathHelper.floor_float(10.0F + p_152948_0_ * 50.0F);
    }

    public static int formatStreamKbps(float p_152946_0_)
    {
        return MathHelper.floor_float(230.0F + p_152946_0_ * 3270.0F);
    }

    public static float formatStreamBps(float p_152947_0_)
    {
        return 0.1F + p_152947_0_ * 0.1F;
    }

    public IngestServer[] func_152925_v()
    {
        return this.broadcastController.func_152855_t().getServers();
    }

    public void func_152909_x()
    {
        IngestServerTester var1 = this.broadcastController.func_152838_J();

        if (var1 != null)
        {
            var1.func_153042_a(this);
        }
    }

    public IngestServerTester func_152932_y()
    {
        return this.broadcastController.isReady();
    }

    public boolean func_152908_z()
    {
        return this.broadcastController.isIngestTesting();
    }

    public int func_152920_A()
    {
        return this.isBroadcasting() ? this.broadcastController.getStreamInfo().viewers : 0;
    }

    public void func_176023_d(ErrorCode p_176023_1_)
    {
        if (ErrorCode.failed(p_176023_1_))
        {
            LOGGER.error(STREAM_MARKER, "Chat failed to initialize");
        }
    }

    public void func_176022_e(ErrorCode p_176022_1_)
    {
        if (ErrorCode.failed(p_176022_1_))
        {
            LOGGER.error(STREAM_MARKER, "Chat failed to shutdown");
        }
    }

    public void func_176017_a(ChatController.ChatState p_176017_1_) {}

    public void func_180605_a(String p_180605_1_, ChatRawMessage[] p_180605_2_)
    {
        ChatRawMessage[] var3 = p_180605_2_;
        int var4 = p_180605_2_.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            ChatRawMessage var6 = var3[var5];
            this.func_176027_a(var6.userName, var6);

            if (this.func_176028_a(var6.modes, var6.subscriptions, this.mc.gameSettings.streamChatUserFilter))
            {
                ChatComponentText var7 = new ChatComponentText(var6.userName);
                ChatComponentTranslation var8 = new ChatComponentTranslation("chat.stream." + (var6.action ? "emote" : "text"), new Object[] {this.twitchComponent, var7, EnumChatFormatting.getTextWithoutFormattingCodes(var6.message)});

                if (var6.action)
                {
                    var8.getChatStyle().setItalic(Boolean.valueOf(true));
                }

                ChatComponentText var9 = new ChatComponentText("");
                var9.appendSibling(new ChatComponentTranslation("stream.userinfo.chatTooltip", new Object[0]));
                Iterator var10 = GuiTwitchUserMode.func_152328_a(var6.modes, var6.subscriptions, (IStream)null).iterator();

                while (var10.hasNext())
                {
                    IChatComponent var11 = (IChatComponent)var10.next();
                    var9.appendText("\n");
                    var9.appendSibling(var11);
                }

                var7.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, var9));
                var7.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.TWITCH_USER_INFO, var6.userName));
                this.mc.ingameGUI.getChatGUI().printChatMessage(var8);
            }
        }
    }

    public void func_176025_a(String p_176025_1_, ChatTokenizedMessage[] p_176025_2_) {}

    private void func_176027_a(String p_176027_1_, ChatRawMessage p_176027_2_)
    {
        ChatUserInfo var3 = (ChatUserInfo)this.field_152955_g.get(p_176027_1_);

        if (var3 == null)
        {
            var3 = new ChatUserInfo();
            var3.displayName = p_176027_1_;
            this.field_152955_g.put(p_176027_1_, var3);
        }

        var3.subscriptions = p_176027_2_.subscriptions;
        var3.modes = p_176027_2_.modes;
        var3.nameColorARGB = p_176027_2_.nameColorARGB;
    }

    private boolean func_176028_a(Set p_176028_1_, Set p_176028_2_, int p_176028_3_)
    {
        return p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED) ? false : (p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) ? true : (p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) ? true : (p_176028_1_.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF) ? true : (p_176028_3_ == 0 ? true : (p_176028_3_ == 1 ? p_176028_2_.contains(ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) : false)))));
    }

    public void func_176018_a(String p_176018_1_, ChatUserInfo[] p_176018_2_, ChatUserInfo[] p_176018_3_, ChatUserInfo[] p_176018_4_)
    {
        ChatUserInfo[] var5 = p_176018_3_;
        int var6 = p_176018_3_.length;
        int var7;
        ChatUserInfo var8;

        for (var7 = 0; var7 < var6; ++var7)
        {
            var8 = var5[var7];
            this.field_152955_g.remove(var8.displayName);
        }

        var5 = p_176018_4_;
        var6 = p_176018_4_.length;

        for (var7 = 0; var7 < var6; ++var7)
        {
            var8 = var5[var7];
            this.field_152955_g.put(var8.displayName, var8);
        }

        var5 = p_176018_2_;
        var6 = p_176018_2_.length;

        for (var7 = 0; var7 < var6; ++var7)
        {
            var8 = var5[var7];
            this.field_152955_g.put(var8.displayName, var8);
        }
    }

    public void func_180606_a(String p_180606_1_)
    {
        LOGGER.debug(STREAM_MARKER, "Chat connected");
    }

    public void func_180607_b(String p_180607_1_)
    {
        LOGGER.debug(STREAM_MARKER, "Chat disconnected");
        this.field_152955_g.clear();
    }

    public void func_176019_a(String p_176019_1_, String p_176019_2_) {}

    public void func_176021_d() {}

    public void func_176024_e() {}

    public void func_176016_c(String p_176016_1_) {}

    public void func_176020_d(String p_176020_1_) {}

    public boolean func_152927_B()
    {
        return this.field_176029_e != null && this.field_176029_e.equals(this.broadcastController.getChannelInfo().name);
    }

    public String func_152921_C()
    {
        return this.field_176029_e;
    }

    public ChatUserInfo func_152926_a(String p_152926_1_)
    {
        return (ChatUserInfo)this.field_152955_g.get(p_152926_1_);
    }

    public void func_152917_b(String p_152917_1_)
    {
        this.chatController.func_175986_a(this.field_176029_e, p_152917_1_);
    }

    public boolean func_152928_D()
    {
        return field_152965_q && this.broadcastController.func_152858_b();
    }

    public ErrorCode func_152912_E()
    {
        return !field_152965_q ? ErrorCode.TTV_EC_OS_TOO_OLD : this.broadcastController.getErrorCode();
    }

    public boolean func_152913_F()
    {
        return this.loggedIn;
    }

    /**
     * mutes or unmutes the microphone based on the boolean parameter passed into the method
     */
    public void muteMicrophone(boolean p_152910_1_)
    {
        this.field_152963_o = p_152910_1_;
        this.updateStreamVolume();
    }

    public boolean func_152929_G()
    {
        boolean var1 = this.mc.gameSettings.streamMicToggleBehavior == 1;
        return this.field_152962_n || this.mc.gameSettings.streamMicVolume <= 0.0F || var1 != this.field_152963_o;
    }

    public IStream.AuthFailureReason func_152918_H()
    {
        return this.authFailureReason;
    }

    static
    {
        try
        {
            if (Util.getOSType() == Util.EnumOS.WINDOWS)
            {
                System.loadLibrary("avutil-ttv-51");
                System.loadLibrary("swresample-ttv-0");
                System.loadLibrary("libmp3lame-ttv");

                if (System.getProperty("os.arch").contains("64"))
                {
                    System.loadLibrary("libmfxsw64");
                }
                else
                {
                    System.loadLibrary("libmfxsw32");
                }
            }

            field_152965_q = true;
        }
        catch (Throwable var1)
        {
            field_152965_q = false;
        }
    }
}
