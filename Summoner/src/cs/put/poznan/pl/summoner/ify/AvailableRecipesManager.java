package cs.put.poznan.pl.summoner.ify;

import java.util.HashMap;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.core.SampleAvailableRecipesManager;

public class AvailableRecipesManager extends SampleAvailableRecipesManager {

	@Override
	public void refresh() {

	}

	@Override
	public Map<String, YRecipe> initMap() {
		HashMap<String, YRecipe> map = new HashMap<String, YRecipe>();
		SummonerRecipe recipe = new SummonerRecipe();
		map.put(recipe.getName(), recipe);
		return map;
	}

}
