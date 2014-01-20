package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.os.Bundle;

/**
 * Represents basic informations needed to create and present recipe.
 * 
 * @author Mateusz Sikora
 * 
 */
public class YRecipeInfo {
	private String mName;
	private YParamList mRequiredParams;

	@Deprecated
	protected YRecipeInfo() {
	}

	public YRecipeInfo(String name, YParamList params) {
		mName = name;
		mRequiredParams = params;
	}

	public YRecipeInfo(YRecipe recipe) {
		mName = recipe.getName();
		mRequiredParams = new YParamList();
		recipe.requestParams(mRequiredParams);
	}

	public void setName(String name) {
		mName = name;
	}

	public void setRequiredParams(YParamList requiredParams) {
		mRequiredParams = requiredParams;
	}

	public void setOptionalParams(YParamList optionalParams) {
		mOptionalParams = optionalParams;
	}

	private YParamList mOptionalParams;

	public String getName() {
		return mName;
	}

	public YParamList getRequiredParams() {
		return mRequiredParams;
	}

	public YParamList getOptionalParams() {
		return mOptionalParams;
	}

	public static List<YRecipeInfo> listFromBundle(Bundle b, ClassLoader classLoader) {
		List<YRecipeInfo> list = new ArrayList<YRecipeInfo>();
		b.setClassLoader(classLoader);
		for (String key : b.keySet()) {
			list.add(new YRecipeInfo(key, (YParamList) b.getParcelable(key)));
		}
		return list;
	}
}
