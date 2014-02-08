package pl.poznan.put.cs.ify.app.ui.server;

public class GroupModel {

	public final String name;
	public final String owner;
	public final boolean amIOwner;

	public GroupModel(String groupName, String groupOwner, boolean amIOwner) {
		name = groupName;
		owner = groupOwner;
		this.amIOwner = amIOwner;
	}

}
