package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.log.YLogger;

public abstract class YObject {
	protected YLogger Log = new YLogger(getName());
	public abstract String getName();
}
