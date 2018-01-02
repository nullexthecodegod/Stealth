package nl.x.client.gui.alt.New;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {

	public final List<Account> accounts = new ArrayList<>();
	public boolean showPasswords;

	public boolean add(Account account) {
		return accounts.add(account);
	}

	public boolean remove(Account account) {
		return accounts.remove(account);
	}

	public void sort() {
		accounts.sort((a, b) -> (a.username.isEmpty() ? a.login : a.username)
				.compareTo(b.username.isEmpty() ? b.login : b.username));
	}

}
