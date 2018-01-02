package nl.x.api.friend;

import java.util.ArrayList;

import net.minecraft.util.StringUtils;

public class FriendManager {
	public static ArrayList<Friend> friends = new ArrayList<Friend>();

	public static void addFriend(final String name, final String alias) {
		final Friend meme = new Friend(name, alias);
		if (!FriendManager.friends.contains(meme)) {
			FriendManager.friends.add(meme);
		}
	}

	public static void addFriend(final Friend f) {
		if (!FriendManager.friends.contains(f)) {
			FriendManager.friends.add(f);
		}
	}

	public static void deleteFriend(final String name) {
		for (final Friend friend : FriendManager.friends) {
			if (friend.getName().equalsIgnoreCase(name)
					|| friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
				FriendManager.friends.remove(friend);
				break;
			}
		}
	}

	/**
	 * Returns an object from the friends list
	 * 
	 * @param name
	 * @return Friend
	 */
	public static Friend getFriendByName(String name) {
		return friends.stream().filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	/**
	 * Returns a friend inside of the friend buffer if there alias is the same
	 * as the given variable
	 * 
	 * @param alias
	 * @return Friend
	 */
	public static Friend getFriendByAlias(String alias) {
		return friends.stream().filter(f -> f.getAlias().equalsIgnoreCase(alias)).findFirst().orElse(null);
	}

	/**
	 * Checks if a player is a friend
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isFriend(String name) {
		for (final Friend friend : FriendManager.friends) {
			if (friend.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name))
					|| friend.getAlias().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
				return true;
			}
		}
		return false;
	}

}
