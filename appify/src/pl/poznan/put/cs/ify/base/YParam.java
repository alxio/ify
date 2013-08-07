package pl.poznan.put.cs.ify.base;

public class YParam {
	private Type mType;
	private Object mValue;
	
	public enum Type{
		String,
		Phone,
		Place
	}
	public YParam(Type type){
		mType = type;
		mValue = null;
	}

	public Type getType(){
		return mType;
	}
	public Object getValue(){
		return mValue;
	}
	public void setValue(Object value){
		mValue = value;
	}
}
