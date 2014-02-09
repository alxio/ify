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
package pl.poznan.put.cs.ify.api.features;

import java.io.File;

import pl.poznan.put.cs.ify.api.IYRecipeHost;
import pl.poznan.put.cs.ify.api.Y;
import pl.poznan.put.cs.ify.api.YFeature;
import pl.poznan.put.cs.ify.api.YRecipe;
import android.content.Context;
import android.os.Environment;

public class YFilesFeature extends YFeature {

	private File mFilesDir;

	@Override
	public long getId() {
		return Y.Files;
	}

	@Override
	protected void init(IYRecipeHost srv) {
		Context context = srv.getContext();
		mFilesDir = context.getFilesDir();
		if (!mFilesDir.exists()) {
			mFilesDir.mkdirs();
		}
	}

	@Override
	public void uninitialize() {

	}

	public File getRecipeDirectory(YRecipe recipe, boolean preferExternal) {
		if (!preferExternal) {
			return getInternalRecipeDirectory(recipe);
		} else {
			if (isExternalStorageReadable() && isExternalStorageWritable()) {
				File ext = getExternalRecipeDirectory(recipe);
				if (ext == null) {
					ext = getInternalRecipeDirectory(recipe);
				}
				return ext;
			} else {
				return getInternalRecipeDirectory(recipe);
			}
		}
	}

	public File getInternalRecipeDirectory(YRecipe recipe) {
		int id = recipe.getTimestamp();
		File f = new File(mFilesDir.getAbsolutePath() + "/ify-recipes-data/" + id);
		if (!f.exists()) {
			f.mkdir();
		}
		return f;
	}

	public File getExternalRecipeDirectory(YRecipe recipe) {
		File dir = Environment.getExternalStorageDirectory();
		if (dir == null) {
			return null;
		} else {
			int id = recipe.getTimestamp();
			File f = new File(dir.getAbsolutePath() + "/" + id);
			if (!f.exists()) {
				f.mkdir();
			}
			return f;
		}
	}

	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

}
