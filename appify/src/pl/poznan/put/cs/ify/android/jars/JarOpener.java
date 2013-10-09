package pl.poznan.put.cs.ify.android.jars;

import java.lang.reflect.Constructor;

import pl.poznan.put.cs.ify.features.YReceipt;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class JarOpener {

	public YReceipt openJar(Context context, JarInfo jarInfo) {
		try {
			String filename = jarInfo.getJarPath();
			String classname = jarInfo.getClassName();
			String dex_dir = context.getDir("dex", 0).getAbsolutePath();
			ClassLoader parent = getClass().getClassLoader();
			DexClassLoader loader = new DexClassLoader(filename, dex_dir, null,
					parent);

			Class c = loader.loadClass(classname);
			// Constructor[] cArr = c.getDeclaredConstructors();
			// for(Constructor cc : cArr) {
			// Class[] cl = cc.getParameterTypes();
			// Log.d("ify", "constr: " + cc.getName());
			// for(Class cls : cl) {
			// Log.d("ify", "c: " + cls.toString());
			// }
			// }
			Constructor ctor = c.getDeclaredConstructor();
			Object o = ctor.newInstance(context);
			return (YReceipt) o;
			// Log.d("ify", "AAa");
			// m.invoke(o2);
		} catch (Exception e) {
			Log.e("se.sdu", String.format("DLL failed: %s: %s", e.getClass()
					.getName(), e.getMessage()));
			return null;
		}
	}
}
