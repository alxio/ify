package pl.poznan.put.cs.ify.api.params;

/**
 * List of allowed param types TODO: Decide if we box standard types
 * (Integer into YInteger etc.)
 */
public enum YParamType {

	YPosition, Integer, String, Boolean;

	public static YParamType getByOrdinal(int ordinal) {
		return YParamType.values()[ordinal];
	}
}