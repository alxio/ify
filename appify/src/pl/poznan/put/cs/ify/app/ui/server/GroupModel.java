package pl.poznan.put.cs.ify.app.ui.server;

import java.util.ArrayList;

public class GroupModel {

	public final String name;
	public final String owner;
	public final boolean amIOwner;
	private ArrayList<UserModel> users;

	public ArrayList<UserModel> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserModel> users) {
		this.users = users;
	}

	public GroupModel(String groupName, String groupOwner, boolean amIOwner) {
		name = groupName;
		owner = groupOwner;
		this.amIOwner = amIOwner;
	}

}
