package pl.poznan.put.cs.ify.android.jars;

public class JarInfo {

	private String mClassName;
	private String mJarPath;

	public JarInfo(String className, String jarPath) {
		super();
		mClassName = className;
		mJarPath = jarPath;
	}

	public String getClassName() {
		return mClassName;
	}

	public void setClassName(String className) {
		mClassName = className;
	}

	public String getJarPath() {
		return mJarPath;
	}

	public void setJarPath(String jarPath) {
		mJarPath = jarPath;
	}
}
