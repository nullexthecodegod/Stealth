package nl.x.api.friend;

public class Friend {
	private String name;
	private String alias;

	/**
	 * @param name
	 * @param alias
	 */
	public Friend(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
