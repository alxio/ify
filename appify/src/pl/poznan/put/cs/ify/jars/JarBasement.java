package pl.poznan.put.cs.ify.jars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class JarBasement {

	private Context mContext;

	public JarBasement(Context context) {
		mContext = context;
	}

	public void putJar(byte[] data, String name) {
		JarDatabaseOpenHelper database = new JarDatabaseOpenHelper(mContext);
		File file = obtainFile(name);
		try {
			saveJar(data, file);
			database.putJar(name, file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveJar(byte[] data, File file) throws FileNotFoundException,
			IOException {
		FileOutputStream fos;
		fos = new FileOutputStream(file);
		fos.write(data);
		fos.flush();
		fos.close();
	}

	private File obtainFile(String name) {

		File dir = new File(mContext.getFilesDir() + "/ifyRecipes");
		dir.mkdirs();
		File file = new File(dir, name + ".jar");
		return file;
	}
}
