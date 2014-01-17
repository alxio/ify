package pl.poznan.put.cs.ify.app.ui.params.contacts;

import java.util.ArrayList;
import java.util.List;

public class Contact {

	private String mName;
	private ArrayList<String> phoneNumbers = new ArrayList<String>();

	public String getName() {
		return mName;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void addPhoneNumber(String phoneNo) {
		phoneNumbers.add(phoneNo);
	}

	public void setName(String name) {
		this.mName = name;
	}
}
