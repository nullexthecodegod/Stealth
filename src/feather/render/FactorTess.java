package feather.render;

// @Author: KNOX
import org.lwjgl.opengl.GL11;

public class FactorTess extends BasicTess {
	public float offset2d = 0;

	public FactorTess(int capacity) {
		super(capacity);
	}

	@Override
	public Tessellator draw(int mode) {
		System.out.println("VALSBALS");
		GL11.glTranslatef(offset2d, offset2d, 0);
		final Tessellator sup = super.draw(mode);
		GL11.glTranslatef(-offset2d, -offset2d, 0);
		return sup;
	}
}