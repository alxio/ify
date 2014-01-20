package pl.poznan.put.cs.ify.app;

import java.util.ArrayList;
import java.util.List;

import pl.poznan.put.cs.ify.appify.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class RecipesAdapter extends BaseAdapter implements ListAdapter {

	private LayoutInflater mInflater;

	private List<YRecipeInfo> mRecipesInfo = new ArrayList<YRecipeInfo>();

	public RecipesAdapter(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return mRecipesInfo.size();
	}

	@Override
	public YRecipeInfo getItem(int pos) {
		return mRecipesInfo.get(pos);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setData(List<YRecipeInfo> data) {
		mRecipesInfo = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.recipe_list_row, null);
			holder = new ViewHolder();
			holder.label = (TextView) convertView
					.findViewById(R.id.recipe_label);
			// holder.checkBox = (CheckBox) convertView
			// .findViewById(R.id.recipe_checkbox);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		YRecipeInfo recipe = getItem(position);
		holder.label.setText(recipe.getName());
		// holder.checkBox.setChecked(mInitializedRecipesManager
		// .isRecipeInitialized(recipe.getName()));
		return convertView;
	}

	private class ViewHolder {
		private TextView label;
		// private CheckBox checkBox;
	}
}
