package nl.x.api.utils.misc;

import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;

public class ServerPinger {
	private static final AtomicInteger threadNumber;
	public static final Logger logger;
	public ServerData server;
	private boolean done;
	private boolean failed;

	static {
		threadNumber = new AtomicInteger(0);
		logger = LogManager.getLogger();
	}

	public ServerPinger() {
		this.done = false;
		this.failed = false;
	}

	public void ping(final String ip) {
		this.ping(ip, 25565);
	}

	public void ping(final String ip, final int port) {
		this.server = new ServerData("", String.valueOf(ip) + ":" + port);
		new Thread("Server Connector #" + ServerPinger.threadNumber.incrementAndGet()) {
			@Override
			public void run() {
				final OldServerPinger pinger = new OldServerPinger();
				try {
					ServerPinger.logger.info("Pinging " + ip + ":" + port + "...");
					pinger.ping(ServerPinger.this.server);
					ServerPinger.logger.info("Ping successful: " + ip + ":" + port);
				} catch (UnknownHostException e) {
					ServerPinger.logger.info("Unknown host: " + ip + ":" + port);
					ServerPinger.access$0(ServerPinger.this, true);
				} catch (Exception e2) {
					ServerPinger.logger.info("Ping failed: " + ip + ":" + port);
					ServerPinger.access$0(ServerPinger.this, true);
				}
				pinger.clearPendingNetworks();
				ServerPinger.access$1(ServerPinger.this, true);
			}
		}.start();
	}

	public boolean isStillPinging() {
		return !this.done;
	}

	public boolean isWorking() {
		return !this.failed;
	}

	public boolean isOtherVersion() {
		return this.server.version != 47;
	}

	static /* synthetic */ void access$0(final ServerPinger serverPinger, final boolean failed) {
		serverPinger.failed = failed;
	}

	static /* synthetic */ void access$1(final ServerPinger serverPinger, final boolean done) {
		serverPinger.done = done;
	}
}
