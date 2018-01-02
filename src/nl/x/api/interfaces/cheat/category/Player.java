package nl.x.api.interfaces.cheat.category;

import nl.x.api.cheat.Cheat.Category;
import nl.x.api.interfaces.cheat.extenders.CategoryInterface;

/**
 * @author NullEX
 *
 */
public interface Player extends CategoryInterface {

	@Override
	default Category getCategory() {
		return Category.Player;
	}

}
