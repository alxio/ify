package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.api.params.YParamList;

public class RecipeFromDatabase {
	public RecipeFromDatabase(YParamList yParams, String name, int id,
			int timestamp) {
		super();
		this.yParams = yParams;
		this.name = name;
		this.id = id;
		this.timestamp = timestamp;
	}

	public final YParamList yParams;
	public final String name;
	public final int id;
	public final int timestamp;
}
