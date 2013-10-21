package pl.poznan.put.cs.ify.android.jars;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JarDatabaseOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "appify";
	private static final String JARS_TABLE = "JARS";
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_PATH = "PATH";
	private static final String COLUMN_CLASSNAME = "CLASSNAME";
	private static final String DATABASE_CREATE = "create table " + JARS_TABLE
			+ "(" + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_PATH + " text not null " + COLUMN_CLASSNAME
			+ "  text not null)";
	private static int DATABASE_VERSION = 1;

	public JarDatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void putJar(JarInfo jar) {

	}

	public void putJar(String classname, String path) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_CLASSNAME, classname);
		values.put(COLUMN_PATH, path);

		db.insert(JARS_TABLE, null, values);
		db.close();
	}

	public void removeJar(String classname) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(JARS_TABLE, COLUMN_CLASSNAME + "=?",
				new String[] { classname });
		db.close();
	}

	public void putJars(List<JarInfo> jars) {
		SQLiteDatabase db = this.getWritableDatabase();

		for (JarInfo j : jars) {
			ContentValues values = new ContentValues();
			values.put(COLUMN_CLASSNAME, j.getClassName());
			values.put(COLUMN_PATH, j.getJarPath());
		}
		db.close();
	}

	public List<JarInfo> getJars() {
		List<JarInfo> jars = new ArrayList<JarInfo>();
		String selectQuery = "SELECT " + COLUMN_CLASSNAME + "," + COLUMN_PATH
				+ " FROM " + JARS_TABLE;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				String className = cursor.getString(0);
				String jarPath = cursor.getString(1);
				jars.add(new JarInfo(className, jarPath));
			} while (cursor.moveToNext());
		}
		db.close();
		return jars;
	}
}
