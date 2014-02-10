/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.api;

import java.util.HashSet;
import java.util.Set;

/**
 * Aggregates subset of Android functionality.
 *
 */
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

	/**
	 * Sends event to concrete YRecipe.
	 * @param event
	 * @param recipeId
	 */
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
