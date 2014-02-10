package cs.put.poznan.pl.summoner.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cs.put.poznan.pl.summoner.MainActivity;
import cs.put.poznan.pl.summoner.R;
import cs.put.poznan.pl.summoner.adapter.UsersAdapter;
import cs.put.poznan.pl.summoner.model.UserInfo;

public class UsersListFragment extends Fragment {

	private static final String DATA = "cs.put.poznan.pl.summoner.data";
	private UsersAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAdapter = new UsersAdapter(getActivity());

		View v = inflater.inflate(R.layout.users_frag, null);
		ListView lv = (ListView) v.findViewById(R.id.users_list);
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				((MainActivity) getActivity()).onUserSelected(mAdapter
						.getItem(arg2));
			}
		});
		ArrayList<UserInfo> tempData = getArguments().getParcelableArrayList(
				DATA);
		if (tempData != null) {
			mAdapter.setData(tempData);
			mAdapter.notifyDataSetChanged();
		}
		return v;
	}

	public void updateData(ArrayList<UserInfo> data) {
		if (mAdapter != null) {
			mAdapter.setData(data);
			mAdapter.notifyDataSetChanged();
		}
		getArguments().putParcelableArrayList(DATA, data);
	}

	public static UsersListFragment getInstance() {
		Bundle args = new Bundle();
		UsersListFragment f = new UsersListFragment();
		f.setArguments(args);
		return f;
	}

	public static UsersListFragment getInstance(ArrayList<UserInfo> data) {
		Bundle args = new Bundle();
		args.putParcelableArrayList(DATA, data);
		UsersListFragment f = new UsersListFragment();
		f.setArguments(args);
		return f;
	}
}
