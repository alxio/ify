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
		saveJar(data, file);
		database.putJar(name, file.getAbsolutePath());
	}

	private void saveJar(byte[] data, File file) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// handle exception
		} catch (IOException e) {
			// handle exception
		}
	}

	private File obtainFile(String name) {

		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/ifyReceipts");
		dir.mkdirs();
		File file = new File(dir, name + ".jar");
		return file;
	}
}
