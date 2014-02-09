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
package pl.poznan.put.cs.ify.app.fragments;

import java.util.List;

import pl.poznan.put.cs.ify.api.params.YParamList;
import pl.poznan.put.cs.ify.app.MainActivity;
import pl.poznan.put.cs.ify.app.RecipesAdapter;
import pl.poznan.put.cs.ify.app.YRecipeInfo;
import pl.poznan.put.cs.ify.app.ui.IOnParamsProvidedListener;
import pl.poznan.put.cs.ify.app.ui.OptionsDialog;
import pl.poznan.put.cs.ify.appify.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RecipesListFragment extends Fragment {

	private RecipesAdapter recipesAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_recipes_list, null);
		ListView recipesListView = (ListView) v.findViewById(R.id.list_recipes);
		recipesAdapter = new RecipesAdapter(getActivity());
		recipesListView.setAdapter(recipesAdapter);
		recipesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				YRecipeInfo item = recipesAdapter.getItem(pos);
				initOptionsDialog(item.getRequiredParams(),
						item.getOptionalParams(), item.getName());
			}

			private void initOptionsDialog(YParamList required,
					YParamList optional, String name) {
				FragmentTransaction ft = getActivity()
						.getSupportFragmentManager().beginTransaction();
				OptionsDialog dialog = OptionsDialog.getInstance(required,
						optional, name, required.getFeatures());
				dialog.setOnParamsProvidedListener(new IOnParamsProvidedListener() {

					@Override
					public void onParamsProvided(YParamList requiredParams,
							YParamList optionalParams, String recipe) {
						((MainActivity) getActivity()).activateRecipe(recipe,
								requiredParams);
					}

					@Override
					public void onRemoveRecipeRequested(String name) {
						((MainActivity) getActivity()).removeAvailableRecipe(name);
					}

				});
				ft.add(dialog, "Recipe_OPTIONS").commit();
			}

		});

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainActivity) getActivity()).requestRecipesList();
	}

	public void onRecipesListUpdated(List<YRecipeInfo> recipesList) {
		recipesAdapter.setData(recipesList);
		recipesAdapter.notifyDataSetChanged();
	}
}
