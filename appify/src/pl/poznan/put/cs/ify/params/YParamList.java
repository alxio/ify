package pl.poznan.put.cs.ify.params;

import java.util.HashMap;

import pl.poznan.put.cs.ify.params.YParam.Type;

public class YParamList {
	private HashMap<String,YParam> mParams;
	
	public YParam.Type getType(String name){
		return mParams.get(name).getType();
	}
	public void add(String name,YParam.Type type){
		mParams.put(name, new YParam(type));
	}
	public void setValue(String name, Object value){
		mParams.get(name).setValue(value);
	}
	public Object getValue(String name){
		return mParams.get(name).getValue();
	}
	
	public void setPosition(String name, YPosition value){
		if(getType(name) == Type.Position)
			mParams.get(name).setValue(value);
	}
	public YPosition getPosition(String name){
		if(getType(name) == Type.Position)
			return (YPosition) mParams.get(name).getValue();
		else
			return null;
	}
}
