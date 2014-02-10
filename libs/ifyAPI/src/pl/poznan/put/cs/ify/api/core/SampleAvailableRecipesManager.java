package pl.poznan.put.cs.ify.api.core;

import java.util.Map;

import pl.poznan.put.cs.ify.api.YRecipe;

public abstract class SampleAvailableRecipesManager implements
		IAvailableRecipesManager {

	protected Map<String, YRecipe> map;

	public abstract Map<String, YRecipe> initMap();
	
	public SampleAvailableRecipesManager() {
		map = initMap();
	}

	@Override
	public Map<String, YRecipe> getAvailableRecipesMap() {
		return map;
	}

	@Override
	public YRecipe getRecipe(String name) {
		return map.get(name);
	}

	@Override
	public void put(String name, YRecipe recipe) {
		map.put(name, recipe);
	}

	@Override
	public void removeRecipe(String recipeName) {
		map.remove(recipeName);
	}

}
