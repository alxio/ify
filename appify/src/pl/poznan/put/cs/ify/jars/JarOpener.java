package pl.poznan.put.cs.ify.jars;

import java.io.File;
import java.lang.reflect.Constructor;

import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.log.YLog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class JarOpener {

	public static final String RECEIPTS_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/ifyReceipts";

	public YReceipt openJar(Context context, String classname) {
		return openJar(context, RECEIPTS_PATH + "/" + classname + ".jar",
				classname);
	}

	public YReceipt openJar(Context context, String filename, String classname) {
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
