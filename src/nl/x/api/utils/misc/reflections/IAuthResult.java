package nl.x.api.utils.misc.reflections;

import nl.x.client.gui.alt.New.auth.AuthResponse;

public interface IAuthResult {

	String username();

	String password();

	void yes(AuthResponse auth);

	void no();

	void part();

}
