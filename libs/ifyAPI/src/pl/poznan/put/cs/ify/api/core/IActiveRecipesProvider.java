package pl.poznan.put.cs.ify.api.core;

import java.util.List;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YRecipe;

public interface IActiveRecipesProvider {

	YRecipe get(int id);

	Map<Integer, YRecipe> getMap();

	void put(int id, YRecipe recipe);

	void remove(Integer id);

}
