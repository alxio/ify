package pl.poznan.put.cs.ify.app.ui.server;

import pl.poznan.put.cs.ify.api.PreferencesProvider;
import android.content.Context;

public class ServerURLBuilder {

	private final String mBaseURL;

	public ServerURLBuilder(Context context) {
		PreferencesProvider prefs = PreferencesProvider.getInstance(context);
		mBaseURL = prefs.getString(PreferencesProvider.KEY_SERVER_URL);
	}

	public String getMyGroups(String user, String pswdHash) {
		return mBaseURL + "groups/" + user + "/" + pswdHash;
	}

	public String getMyInvites(String user, String pswdHash) {
		return mBaseURL + "invitations/" + user + "/" + pswdHash;
	}

	public String postAcceptInvite(String user, String pswdHash, String group) {
		return mBaseURL + "invitations/accept/" + group + "/" + user + "/"
				+ pswdHash;
	}

	public String sendInvite(String user, String pswdHash, String group,
			String userToInvite) {
		return mBaseURL + "invite/" + userToInvite + "/" + group + "/" + user
				+ "/" + pswdHash;
	}

	public String getUserInGroup(String user, String pswdHash, String group) {
		return mBaseURL + "groups/members/" + "/" + group + "/" + user + "/"
				+ pswdHash;
	}

	public String register(String user, String pswd, String firstName,
			String secondName) {
		return mBaseURL + "register/" + user + "/" + pswd + "/" + firstName
				+ "/" + secondName;
	}

	public String createGroup(String user, String pswd, String group) {
		return mBaseURL + "groups/create/" + group + "/" + user + "/" + pswd;
	}
}
