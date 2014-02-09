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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.core.IAvailableRecipesManager;
import pl.poznan.put.cs.ify.app.recipes.YBuildInRecipes;
import pl.poznan.put.cs.ify.jars.JarDatabaseOpenHelper;
import pl.poznan.put.cs.ify.jars.JarInfo;
import pl.poznan.put.cs.ify.jars.JarOpener;
import android.content.Context;

public class AvailableRecipesManager implements IAvailableRecipesManager {
	private Map<String, YRecipe> mAvaibleRecipes = new HashMap<String, YRecipe>();
	private YBuildInRecipes mBuildInList = new YBuildInRecipes();
	private Context mContext;

	public AvailableRecipesManager(Context ctx) {
		// loadSampleJar(ctx, "MyRecipe");
		// loadSampleJar(ctx, "SecondRecipe");
		// loadSampleJar(ctx, "SconyRecipe");
		// loadSampleJar(ctx, "YBadumRecipe");
		mContext = ctx;
		loadBuildIn();
		loadFromJars();

	}

	private void loadFromJars() {
		JarDatabaseOpenHelper db = new JarDatabaseOpenHelper(mContext);
		List<JarInfo> jars = db.getJars();
		for (JarInfo jarInfo : jars) {
			loadSampleJar(mContext, jarInfo.getClassName());
		}
		db.close();
	}

	public YRecipe getRecipe(String name) {
		return mAvaibleRecipes.get(name);
	}

	private void loadBuildIn() {
		for (YRecipe recipe : mBuildInList.getList()) {
			mAvaibleRecipes.put(recipe.getName(), recipe);
		}
	}

	private void loadSampleJar(Context ctx, String name) {
		JarOpener opener = new JarOpener(mContext);
		YRecipe recipe = opener.openJar(ctx, name);
		if (recipe != null) {
			mAvaibleRecipes.put(recipe.getName(), recipe);
		}
	}

	public void refresh() {
		mAvaibleRecipes.clear();
		loadBuildIn();
		loadFromJars();
	}

	@Override
	public Map<String, YRecipe> getAvailableRecipesMap() {
		return Collections.unmodifiableMap(mAvaibleRecipes);
	}

	@Override
	public void put(String name, YRecipe recipe) {
		mAvaibleRecipes.put(name, recipe);
	}

	@Override
	public void removeRecipe(String recipeName) {
		mAvaibleRecipes.remove(recipeName);
		JarDatabaseOpenHelper db = new JarDatabaseOpenHelper(mContext);
		db.removeJar(recipeName);
		JarOpener opener = new JarOpener(mContext);
		opener.removeJar(recipeName);
		db.close();
	}

}
