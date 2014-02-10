package cs.put.poznan.pl.summoner.ify;

import java.util.HashMap;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.core.SampleActiveRecipesManager;

public class ActiveRecipesManager extends SampleActiveRecipesManager {

	@Override
	protected HashMap<Integer, YRecipe> initMap() {
		return new HashMap<Integer, YRecipe>();
	}

}
