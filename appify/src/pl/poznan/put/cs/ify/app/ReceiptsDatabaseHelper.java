package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import pl.poznan.put.cs.ify.api.YReceipt;
import pl.poznan.put.cs.ify.api.params.YParam;
import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.params.YParamType;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class ReceiptsDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "appify_receipts";
	private static final String TABLE = "RECEIPTS";
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_PARAMS = "PARAMS";
	private static final String COLUMN_NAME = "NAME";
	private static final String COLUMN_TIMESTAMP = "TIMESTAMP";
	private static final String DATABASE_CREATE = "create table " + TABLE + "(" + COLUMN_ID + " integer primary key, "
			+ COLUMN_PARAMS + " text not null, " + COLUMN_NAME + " text not null, " + COLUMN_TIMESTAMP
			+ " integer not null)";
	private static final String SEPARATOR = ";";
	private static final String SEP_PARAM = "\n";
	private static int DATABASE_VERSION = 8;

	public ReceiptsDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + TABLE);
		db.execSQL(DATABASE_CREATE);
	}

	public void saveReceipt(YReceipt receipt, int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		String resultFinal = "";
		YParamList params = receipt.getParams();
		for (Entry<String, YParam> param : params) {
			String key = param.getKey();
			YParam value = param.getValue();
			String result = key + SEPARATOR + value.getType().ordinal() + SEPARATOR + value.getValue();
			resultFinal += result + SEP_PARAM;
		}
		if (!TextUtils.isEmpty(resultFinal)) {
			resultFinal = resultFinal.substring(0, resultFinal.length() - 1);
		}
		values.put(COLUMN_PARAMS, resultFinal);
		values.put(COLUMN_NAME, receipt.getName());
		values.put(COLUMN_TIMESTAMP, receipt.getTimestamp());
		Log.d("RECEIPTS_DB", resultFinal);
		db.insert(TABLE, null, values);
		db.close();
	}

	public void removeReceipt(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String[] arr = { id + "" };
		db.delete(TABLE, "id = ?", arr);
		db.close();
	}

	public int getMaxId() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor query = db.query(TABLE, new String[] { "MAX(" + COLUMN_ID + ")" }, null, null, null, null, null);
		boolean moveToFirst = query.moveToFirst();
		if (!moveToFirst) {
			return 0;
		}
		db.close();
		return query.getInt(0);
	}

	public List<ReceiptFromDatabase> getActivatedReceipts() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor query = db.query(TABLE, new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_PARAMS, COLUMN_TIMESTAMP }, null,
				null, null, null, null);
		List<ReceiptFromDatabase> result = new ArrayList<ReceiptFromDatabase>();
		if (!query.moveToFirst()) {
			return result;
		}
		while (!query.isAfterLast()) {
			int id = query.getInt(0);
			String name = query.getString(1);
			int timestamp = query.getInt(3);
			String paramsString = query.getString(2);
			YParamList paramList = new YParamList();
			if (!TextUtils.isEmpty(paramsString)) {

				String[] split = paramsString.split(SEP_PARAM);
				int l = split.length;
				Log.d("RECEIPTS_DB", paramsString);

				for (int i = 0; i < l; ++i) {
					String[] paramArr = split[i].split(SEPARATOR);
					String paramName = paramArr[0];
					int paramTypeOrdinal = Integer.parseInt(paramArr[1]);
					YParamType paramType = YParamType.getByOrdinal(paramTypeOrdinal);
					YParam param = new YParam(paramType, YParam.getValueFromString(paramArr[2], paramType));
					paramList.add(paramName, param);
				}
			}
			result.add(new ReceiptFromDatabase(paramList, name, id, timestamp));
			query.moveToNext();
		}
		db.close();
		return result;
	}
}
