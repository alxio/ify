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
package pl.poznan.put.cs.ify.jars;

import java.io.File;
import java.lang.reflect.Constructor;

import pl.poznan.put.cs.ify.api.YRecipe;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class JarOpener {

	public final String RecipeS_PATH;

	public JarOpener(Context context) {
		File dir = new File(context.getFilesDir() + "/ifyRecipes");
		dir.mkdirs();
		RecipeS_PATH = dir.getAbsolutePath();
	}

	public YRecipe openJar(Context context, String classname) {
		return openJar(context, RecipeS_PATH + "/" + classname + ".jar",
				classname);
	}

	public YRecipe openJar(Context context, String filename, String classname) {
		try {
			File file = new File(classname);
			Log.d("FILE", "file " + classname + " exists " + file.exists());
			// Do something else.
			String dex_dir = context.getDir("dex", 0).getAbsolutePath();
			ClassLoader parent = getClass().getClassLoader();
			DexClassLoader loader = new DexClassLoader(filename, dex_dir, null,
					parent);
			Class<?> c = loader.loadClass(classname);
			Constructor<?> ctor = c.getDeclaredConstructor();
			Object o = ctor.newInstance();
			Log.d("RecipeCreated", o.toString());
			return (YRecipe) o;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("se.sdu", String.format("DLL failed: %s: %s", e.getClass()
					.getName(), e.getMessage()));
			return null;
		}
	}

	public boolean removeJar(String recipeName) {
		File file = new File(RecipeS_PATH + "/" + recipeName + ".jar");
		boolean exists = file.exists();
		boolean delete = file.delete();
		return delete;
	}
}
