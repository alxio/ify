package pl.poznan.put.cs.ify.app.market;

public class MarketInfo {
	private String name;
	private long timestamp;
	private String url;
	private String description;

	public MarketInfo(String name, long timestamp, String url, String description) {
		super();
		this.name = name;
		this.timestamp = timestamp;
		this.url = url;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}
}
