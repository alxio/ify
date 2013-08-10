package pl.poznan.put.cs.ify.core;

public class UninitializedException extends Exception {
	private String mName;
	public UninitializedException(String name) {
		mName = name;
	}
	public String toString(){
		return mName;
	}
	private static final long serialVersionUID = 1L;
}
