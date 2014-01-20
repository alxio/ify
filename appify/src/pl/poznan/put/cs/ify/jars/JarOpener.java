package pl.poznan.put.cs.ify.jars;

import java.io.File;
import java.lang.reflect.Constructor;

import pl.poznan.put.cs.ify.api.YRecipe;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class JarOpener {

	public static final String RecipeS_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/ifyRecipes";

	public YRecipe openJar(Context context, String classname) {
		return openJar(context, RecipeS_PATH + "/" + classname + ".jar", classname);
	}

	public YRecipe openJar(Context context, String filename, String classname) {
		try {
			File file = new File(classname);
			Log.d("FILE", "file " + classname + " exists " + file.exists());
			// Do something else.
			String dex_dir = context.getDir("dex", 0).getAbsolutePath();
			ClassLoader parent = getClass().getClassLoader();
			DexClassLoader loader = new DexClassLoader(filename, dex_dir, null, parent);
			Class<?> c = loader.loadClass(classname);
			Constructor<?> ctor = c.getDeclaredConstructor();
			Object o = ctor.newInstance();
			Log.d("RecipeCreated", o.toString());
			return (YRecipe) o;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("se.sdu", String.format("DLL failed: %s: %s", e.getClass().getName(), e.getMessage()));
			return null;
		}
	}
}
