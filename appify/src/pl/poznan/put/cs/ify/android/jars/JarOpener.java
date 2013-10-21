package pl.poznan.put.cs.ify.android.jars;

import java.lang.reflect.Constructor;

import pl.poznan.put.cs.ify.api.features.YReceipt;
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
			DexClassLoader loader = new DexClassLoader(filename, dex_dir, null, parent);
			Class c = loader.loadClass(classname);
			// Constructor[] cArr = c.getDeclaredConstructors();
			// for(Constructor cc : cArr) {
			// Class[] cl = cc.getParameterTypes();
			// Log.d("ify", "constr: " + cc.getName());
			// for(Class cls : cl) {
			// Log.d("ify", "c: " + cls.toString());
			// }
			// }
			for (Constructor ctor : c.getDeclaredConstructors()) {
				Log.d("Constructor", "params: " + ctor.getParameterTypes().length);
			}
			Constructor ctor = c.getDeclaredConstructor();
			Object o = ctor.newInstance();
			Log.d("ReceiptCreated", o.toString());
			return (YReceipt) o;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("se.sdu", String.format("DLL failed: %s: %s", e.getClass().getName(), e.getMessage()));
			return null;
		}
	}
}
