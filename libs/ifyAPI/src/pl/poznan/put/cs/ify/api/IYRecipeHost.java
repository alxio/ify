package pl.poznan.put.cs.ify.api;

import pl.poznan.put.cs.ify.api.core.ISecurity;
import pl.poznan.put.cs.ify.api.params.YParamList;
import android.content.Context;

public interface IYRecipeHost {

	public abstract int enableRecipe(String name, YParamList params);

	public abstract void disableRecipe(Integer id);

	public abstract Context getContext();

	public abstract ISecurity getSecurity();

	public int getNotificationIconId();

	public abstract void sendArchivedLogs(String tag);
}