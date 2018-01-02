/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2017 Team Pepsi
 *
 * Permission is hereby granted to any persons and/or organizations using this
 * software to copy, modify, merge, publish, and distribute it. Said persons
 * and/or organizations are not allowed to use the software or any derivatives
 * of the work for commercial use or any other means to generate income, nor are
 * they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing
 * and/or trademarking this software without explicit permission from Team
 * Pepsi.
 *
 * Any persons and/or organizations using this software must disclose their
 * source code and have it publicly available, include this license, provide
 * sufficient credit to the original authors of the project (IE: Team Pepsi), as
 * well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package nl.x.client.gui.click.api;

import net.minecraft.client.Minecraft;

public abstract class EntryImplBase implements IEntry {
	public Minecraft mc = Minecraft.getMinecraft();
	public final int width;
	public final int height;
	public int x;
	public int y;
	protected boolean isHoveredCached = false;

	public EntryImplBase(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public static void drawString(int x, int y, String text, int color) {
		Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, false);
	}

	@Override
	public boolean isMouseHovered() {
		return isHoveredCached;
	}

	protected void updateIsMouseHovered(int mouseX, int mouseY) {
		int x = getX(), y = getY();
		int maxX = x + width, maxY = y + height;
		isHoveredCached = (x <= mouseX && mouseX <= maxX && y <= mouseY && mouseY <= maxY);
	}
}
