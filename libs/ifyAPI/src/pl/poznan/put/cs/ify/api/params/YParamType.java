package pl.poznan.put.cs.ify.api.params;

import pl.poznan.put.cs.ify.api.exceptions.UnimplementedException;

/**
 * List of allowed param types TODO: Decide if we box standard types (Integer
 * into YInteger etc.)
 */
public enum YParamType {

	Position, Integer, String, Boolean, Group;

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
		default:
			throw new UnimplementedException();
		}
	}
}