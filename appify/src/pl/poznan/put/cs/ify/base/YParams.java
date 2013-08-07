package pl.poznan.put.cs.ify.base;

import java.util.HashMap;

public class YParams {
	private HashMap<String,YParam> mParams;
	
	public Object getValue(String name){
		return mParams.get(name).getValue();
	}
	public YParam.Type getType(String name){
		return mParams.get(name).getType();
	}
	public void setValue(String name, Object value){
		mParams.get(name).setValue(value);
	}
	public void add(String name,YParam.Type type){
		mParams.put(name, new YParam(type));
	}
}
