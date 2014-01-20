package pl.poznan.put.cs.ify.app;

import java.util.List;

import pl.poznan.put.cs.ify.api.YRecipe;
import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.ActiveRecipeInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ActiveReceipesAdapter extends BaseAdapter implements ListAdapter {
	private LayoutInflater mInflater;
	private List<ActiveRecipeInfo> mData;

	public ActiveReceipesAdapter(Context context, List<ActiveRecipeInfo> receipes) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = receipes;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public ActiveRecipeInfo getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.recipe_list_row, null);
			holder = new ViewHolder();
			holder.label = (TextView) convertView.findViewById(R.id.recipe_label);
			// holder.checkBox = (CheckBox) convertView
			// .findViewById(R.id.recipe_checkbox);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		ActiveRecipeInfo recipe = getItem(position);
		holder.label.setText(YRecipe.createTag(recipe.getId(), recipe.getName()));
		// holder.checkBox.setChecked(mInitializedRecipesManager
		// .isRecipeInitialized(recipe.getName()));
		return convertView;
	}

	private class ViewHolder {
		private TextView label;
		// private CheckBox checkBox;
	}

	public void clear() {
		mData.clear();
	}
}
