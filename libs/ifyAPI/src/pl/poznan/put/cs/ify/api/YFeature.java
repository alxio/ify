package pl.poznan.put.cs.ify.api;

import java.util.HashSet;
import java.util.Set;

public abstract class YFeature {
	// TODO: Weak reference
	protected IYRecipeHost mHost = null;
	protected Set<YRecipe> mListeners = new HashSet<YRecipe>();

	/**
	 * Id of feature, it's one bit and can be used in bitmasks.
	 * 
	 * @return Id of feature
	 */
	public abstract long getId();

	/**
	 * Called by host to initialize feature.
	 * 
	 * @param host
	 */
	public void initialize(IYRecipeHost host) {
		mHost = host;
		init(mHost);
	}

	/**
	 * Internal initialization of specific feature.
	 * 
	 * @param host
	 */
	protected abstract void init(IYRecipeHost srv);

	/**
	 * Internal uninitialization of specific feature.
	 * 
	 * @param host
	 */
	public abstract void uninitialize();

	/**
	 * Called when initializing recipe using this feature.
	 * 
	 * @param recipe
	 */
	public void registerRecipe(YRecipe recipe) {
		mListeners.add(recipe);
	}

	/**
	 * Called when uninitializing recipe using this feature.
	 * 
	 * @param recipe
	 */
	public void unregisterRecipe(YRecipe recipe) {
		mListeners.remove(recipe);
	}

	/**
	 * @return true if any recipe uses it.
	 */
	public boolean isUsed() {
		return !mListeners.isEmpty();
	}

	/**
	 * Sends event to all recipes using this feature.
	 * 
	 * @see pl.poznan.put.cs.ify.api.YRecipe#handleEvent(YEvent)
	 * @param event
	 */
	public void sendNotification(YEvent event) {
		Set<YRecipe> toDelete = new HashSet<YRecipe>();
		for (YRecipe recipe : mListeners) {
			if (recipe == null || !recipe.tryHandleEvent(event))
				toDelete.add(recipe);
		}
		mListeners.removeAll(toDelete);
	}

	public void sendNotification(YEvent event, int recipeId) {
		Set<YRecipe> toDelete = new HashSet<YRecipe>();
		for (YRecipe recipe : mListeners) {
			if (recipe == null) {
				toDelete.add(recipe);
			} else if (recipe.getId() == recipeId) {
				if (!recipe.tryHandleEvent(event)) {
					toDelete.add(recipe);
				}
			}
		}
		mListeners.removeAll(toDelete);
	}
}
