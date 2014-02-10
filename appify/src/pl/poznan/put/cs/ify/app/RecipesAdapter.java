/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
