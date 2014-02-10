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
package pl.poznan.put.cs.ify.app.ui.server;

import java.util.ArrayList;

public class GroupModel {

	public final String name;
	public final String owner;
	public final boolean amIOwner;
	private ArrayList<UserModel> users;

	public ArrayList<UserModel> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserModel> users) {
		this.users = users;
	}

	public GroupModel(String groupName, String groupOwner, boolean amIOwner) {
		name = groupName;
		owner = groupOwner;
		this.amIOwner = amIOwner;
	}

}
