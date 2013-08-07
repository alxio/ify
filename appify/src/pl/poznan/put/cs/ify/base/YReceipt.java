package pl.poznan.put.cs.ify.base;


public abstract class YReceipt {
	protected YParams mParams;
	public YReceipt(YParams params){
		mParams = params;
	}
	public abstract void handleTrigger(YTrigger trigger);
}
