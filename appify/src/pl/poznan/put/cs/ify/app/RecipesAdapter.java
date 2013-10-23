package pl.poznan.put.cs.ify.app;

import pl.poznan.put.cs.ify.appify.R;
import pl.poznan.put.cs.ify.core.AvailableRecipesManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class RecipesAdapter extends BaseAdapter implements ListAdapter {

	private LayoutInflater mInflater;
	private InitializedRecipesManager mInitializedRecipesManager;
	private AvailableRecipesManager mAvailableRecipesManager;

	public RecipesAdapter(Context context,
			InitializedRecipesManager initializedRecipesManager,
			AvailableRecipesManager availableRecipesManager) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mInitializedRecipesManager = initializedRecipesManager;
		mAvailableRecipesManager = availableRecipesManager;
	}

	@Override
	public int getCount() {
		return mAvailableRecipesManager.getAvailableReceipesList().size();
	}

	@Override
	public YReceiptInfo getItem(int pos) {
		return mAvailableRecipesManager.getAvailableReceipesList().get(pos);
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
			holder.label = (TextView) convertView
					.findViewById(R.id.recipe_label);
			// holder.checkBox = (CheckBox) convertView
			// .findViewById(R.id.recipe_checkbox);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		YReceiptInfo receipt = mAvailableRecipesManager
				.getAvailableReceipesList().get(position);
		holder.label.setText(receipt.getName());
		// holder.checkBox.setChecked(mInitializedRecipesManager
		// .isReceiptInitialized(receipt.getName()));
		return convertView;
	}

	private class ViewHolder {
		private TextView label;
		// private CheckBox checkBox;
	}
}
