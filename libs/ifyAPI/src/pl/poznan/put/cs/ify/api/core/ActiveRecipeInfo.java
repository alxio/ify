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
package pl.poznan.put.cs.ify.api.core;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Parcel;
import android.os.Parcelable;

public class ActiveRecipeInfo implements Parcelable {

	private String name;
	private int id;
	private YParamList params;

	public ActiveRecipeInfo(String name, YParamList params, int id) {
		super();
		this.name = name;
		this.params = params;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public ActiveRecipeInfo(Parcel in) {
		this.name = in.readString();
		this.params = in.readParcelable(YParamList.class.getClassLoader());
		this.id = in.readInt();
	}

	public String getName() {
		return name;
	}

	public YParamList getParams() {
		return params;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeParcelable(params, 0);
		dest.writeInt(id);
	}

	public static final Parcelable.Creator<ActiveRecipeInfo> CREATOR = new Parcelable.Creator<ActiveRecipeInfo>() {
		public ActiveRecipeInfo createFromParcel(Parcel in) {
			return new ActiveRecipeInfo(in);
		}

		public ActiveRecipeInfo[] newArray(int size) {
			return new ActiveRecipeInfo[size];
		}
	};
	public String getTag() {
		return YRecipe.createTag(id, name);
	}
}
