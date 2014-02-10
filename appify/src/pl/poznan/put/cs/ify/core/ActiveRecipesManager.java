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
