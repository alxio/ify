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
package pl.poznan.put.cs.ify.app.persistance;

import pl.poznan.put.cs.ify.api.params.YParamList;

public class RecipeFromDatabase {
	public RecipeFromDatabase(YParamList yParams, String name, int id,
			int timestamp) {
		super();
		this.yParams = yParams;
		this.name = name;
		this.id = id;
		this.timestamp = timestamp;
	}

	public final YParamList yParams;
	public final String name;
	public final int id;
	public final int timestamp;
}
