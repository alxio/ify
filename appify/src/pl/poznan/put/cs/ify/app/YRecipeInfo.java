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
package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Bundle;

/**
 * Represents basic informations needed to create and present recipe.
 * 
 * @author Mateusz Sikora
 * 
 */
public class YRecipeInfo {
	private String mName;
	private YParamList mRequiredParams;

	@Deprecated
	protected YRecipeInfo() {
	}

	public YRecipeInfo(String name, YParamList params) {
		mName = name;
		mRequiredParams = params;
	}

	public YRecipeInfo(YRecipe recipe) {
		mName = recipe.getName();
		mRequiredParams = new YParamList();
		recipe.requestParams(mRequiredParams);
	}

	public void setName(String name) {
		mName = name;
	}

	public void setRequiredParams(YParamList requiredParams) {
		mRequiredParams = requiredParams;
	}

	public void setOptionalParams(YParamList optionalParams) {
		mOptionalParams = optionalParams;
	}

	private YParamList mOptionalParams;

	public String getName() {
		return mName;
	}

	public YParamList getRequiredParams() {
		return mRequiredParams;
	}

	public YParamList getOptionalParams() {
		return mOptionalParams;
	}

	public static List<YRecipeInfo> listFromBundle(Bundle b, ClassLoader classLoader) {
		List<YRecipeInfo> list = new ArrayList<YRecipeInfo>();
		b.setClassLoader(classLoader);
		for (String key : b.keySet()) {
			list.add(new YRecipeInfo(key, (YParamList) b.getParcelable(key)));
		}
		return list;
	}
}
