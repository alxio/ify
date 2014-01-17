package pl.poznan.put.cs.ify.app.ui.params.contacts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

public class PickNumbersTask extends AsyncTask<Void, Void, List<Contact>> {

	private Activity mActivity;
	private OnContactsObtainedListener mListener;

	public PickNumbersTask(Activity activity,
			OnContactsObtainedListener onContactsObtainedListener) {
		mActivity = activity;
		mListener = onContactsObtainedListener;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mListener.onStarted();
	}

	@Override
	protected List<Contact> doInBackground(Void... arg0) {
		ArrayList<Contact> result = new ArrayList<Contact>();
		ContentResolver cr = mActivity.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				Contact c = new Contact();
				c.setName(name);
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						String phoneNo = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						c.addPhoneNumber(phoneNo);
					}
					pCur.close();
					result.add(c);
				}
			}
		}
		return result;
	}

	@Override
	protected void onPostExecute(List<Contact> result) {
		super.onPostExecute(result);
		mListener.onContactsObtained(result);
	}
}
