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
import java.util.List;

public class Contact implements Comparable<Contact> {

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

	@Override
	public int compareTo(Contact another) {
		return this.mName.compareTo(another.getName());
	}
}
