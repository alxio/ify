package pl.poznan.put.cs.ify.api.core;

import pl.poznan.put.cs.ify.api.security.User;
import pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback;

public interface ISecurity {

	User getCurrentUser();


	void logout(ILoginCallback cb);

	void login(String username, String password, ILoginCallback cb);

}
