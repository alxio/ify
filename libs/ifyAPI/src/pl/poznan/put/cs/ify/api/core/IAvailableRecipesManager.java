package pl.poznan.put.cs.ify.api.core;

import java.util.List;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YRecipe;

public interface IAvailableRecipesManager {
	Map<String, YRecipe> getAvailableRecipesMap();

	YRecipe getRecipe(String name);

	void put(String name, YRecipe recipe);

	void refresh();
	
	YRecipe get(String name);
}
