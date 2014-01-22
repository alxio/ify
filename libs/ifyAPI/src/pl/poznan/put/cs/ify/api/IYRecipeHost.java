package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.api.security.YSecurity;
import android.content.Context;

public interface IYRecipeHost {

	public abstract int enableRecipe(String name, YParamList params);

	public abstract void disableRecipe(Integer id);

	public abstract Context getContext();

	public abstract void sendLogs(String tag);

	public abstract YSecurity getSecurity();
	
	public int getNotificationIconId();
}