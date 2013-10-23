package pl.poznan.put.cs.ify.core;

import java.lang.reflect.Constructor;

import pl.poznan.put.cs.ify.api.YReceipt;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class JarOpener {

	public static final String RECEIPTS_PATH = "/mnt/sdcard/ifyReceipts/";

	public YReceipt openJar(Context context, String classname) {
		return openJar(context, classname, RECEIPTS_PATH + classname + ".jar");
	}

	public YReceipt openJar(Context context, String filename, String classname) {
		try {
			String dex_dir = context.getDir("dex", 0).getAbsolutePath();
			ClassLoader parent = getClass().getClassLoader();
			DexClassLoader loader = new DexClassLoader(filename, dex_dir, null, parent);
			Class<?> c = loader.loadClass(classname);
			Constructor<?> ctor = c.getDeclaredConstructor();
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
