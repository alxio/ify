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
package pl.poznan.put.cs.ify.api.params;

import pl.poznan.put.cs.ify.api.exceptions.UnimplementedException;

/**
 * List of allowed param types TODO: Decide if we box standard types (Integer
 * into YInteger etc.)
 */
public enum YParamType {

	Position, Integer, String, Boolean, Group, Number;

	public static YParamType getByOrdinal(int ordinal) {
		return YParamType.values()[ordinal];
	}

	static YParamType fromString(String name) {
		if ("Integer".equals(name))
			return Integer;
		if ("String".equals(name))
			return String;
		if ("Boolean".equals(name))
			return Boolean;
		if ("Group".equals(name))
			return Group;
		if ("Position".equals(name))
			return Position;
		if ("Number".equals(name)) {
			return Number;
		}
		throw new UnimplementedException();
	}

	@Override
	public String toString() {
		switch (this) {
		case Integer:
			return "Integer";
		case String:
			return "String";
		case Boolean:
			return "Boolean";
		case Group:
			return "Group";
		case Position:
			return "Position";
		case Number:
			return "Number";
		default:
			throw new UnimplementedException();
		}
	}
}
