package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.log.YLogger;

public abstract class YObject {
	protected YLogger log = new YLogger(getName());
	public abstract String getName();
}
