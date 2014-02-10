package pl.poznan.put.cs.ify.api.core;

import java.util.HashMap;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.core.IActiveRecipesProvider;

public abstract class SampleActiveRecipesManager implements
		IActiveRecipesProvider {

	protected HashMap<Integer, YRecipe> map;

	public SampleActiveRecipesManager() {
		map = initMap();
	}

	protected abstract HashMap<Integer, YRecipe> initMap();

	@Override
	public YRecipe get(int id) {
		return map.get(id);
	}

	@Override
	public Map<Integer, YRecipe> getMap() {
		return map;
	}

	@Override
	public void put(int id, YRecipe recipe) {
		map.put(id, recipe);
	}

	@Override
	public void remove(Integer id) {
		map.remove(id);
	}

}
