package pl.poznan.put.cs.ify.app.ui.params.contacts;

import java.util.List;

public interface OnContactsObtainedListener {

	void onContactsObtained(List<Contact> result);

	void onStarted();

}
