/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.app.ui.params.contacts;

import java.util.ArrayList;
import java.util.Collections;
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
		Collections.sort(result);
		return result;
	}

	@Override
	protected void onPostExecute(List<Contact> result) {
		super.onPostExecute(result);
		mListener.onContactsObtained(result);
	}
}
