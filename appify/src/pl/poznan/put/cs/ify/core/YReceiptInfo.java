package pl.poznan.put.cs.ify.core;

/**
 * Represents basic informations needed to create and present receipt.
 * 
 * @author Mateusz Sikora
 * 
 */
public class YReceiptInfo {
	private final String mName;

	public YReceiptInfo(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}
}
