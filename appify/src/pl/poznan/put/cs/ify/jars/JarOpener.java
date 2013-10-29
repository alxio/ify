package pl.poznan.put.cs.ify.jars;

import java.lang.reflect.Constructor;

import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.Context;
import android.os.Environment;
import dalvik.system.DexClassLoader;

public class JarOpener {

	public static final String RECEIPTS_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/ifyReceipts";

	public YReceipt openJar(Context context, String classname) {
		return openJar(context, classname, RECEIPTS_PATH + classname + ".jar");
	}

	public YReceipt openJar(Context context, String filename, String classname) {
		try {
			String dex_dir = context.getDir("dex", 0).getAbsolutePath();
			ClassLoader parent = getClass().getClassLoader();
			DexClassLoader loader = new DexClassLoader(filename, dex_dir, null,
					parent);
			Class<?> c = loader.loadClass(classname);
			Constructor<?> ctor = c.getDeclaredConstructor();
			Object o = ctor.newInstance();
			YLog.d("ReceiptCreated", o.toString());
			return (YReceipt) o;
		} catch (Exception e) {
			e.printStackTrace();
			YLog.e("se.sdu", String.format("DLL failed: %s: %s", e.getClass()
					.getName(), e.getMessage()));
			return null;
		}
	}
}
