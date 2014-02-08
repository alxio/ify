package pl.poznan.put.cs.ify.core;

import java.util.HashMap;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.core.IActiveRecipesProvider;

public class ActiveRecipesManager implements IActiveRecipesProvider {

	private HashMap<Integer, YRecipe> mRecipes = new HashMap<Integer, YRecipe>();

	@Override
	public YRecipe get(int id) {
		return mRecipes.get(id);
	}

	@Override
	public Map<Integer, YRecipe> getMap() {
		return mRecipes;
	}

	@Override
	public void put(int id, YRecipe recipe) {
		mRecipes.put(id, recipe);
	}

	@Override
	public void remove(Integer id) {
		mRecipes.remove(id);
	}

}
