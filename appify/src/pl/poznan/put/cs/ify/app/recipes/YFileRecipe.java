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
package pl.poznan.put.cs.ify.app.recipes;

import java.io.File;

import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YEvent;
import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.features.YFilesFeature;
import pl.poznan.put.cs.ify.api.params.YParamList;

public class YFileRecipe extends YRecipe {

	@Override
	public long requestFeatures() {
		return Y.Time | Y.Files;
	}

	@Override
	protected void init() throws Exception {
		super.init();
		YFilesFeature files = (YFilesFeature) getFeatures().get(Y.Files);
		File recipeDirectory = files.getRecipeDirectory(this, true);
		Log.d("FILE " + recipeDirectory.getAbsolutePath());
	}

	@Override
	public void requestParams(YParamList params) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void handleEvent(YEvent event) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "YFileRecipe";
	}

	@Override
	public YRecipe newInstance() {
		return new YFileRecipe();
	}
}
