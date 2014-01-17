package pl.poznan.put.cs.ify.app.ui.params.contacts;

import java.util.List;

import pl.poznan.put.cs.ify.appify.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class NumberDialogFragment extends DialogFragment {

	public interface OnNumberChosenListener {
		void onChosen(String name, String number);
	}

	private ContactsAdapter mAdapter;
	protected List<Contact> mTempData;
	protected View mLoadingLayout;
	private OnContactsObtainedListener mOnContactsObtainedListener = new OnContactsObtainedListener() {

		@Override
		public void onStarted() {
			if (mLoadingLayout != null) {
				mLoadingLayout.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onContactsObtained(List<Contact> result) {
			if (mLoadingLayout != null) {
				mLoadingLayout.setVisibility(View.GONE);
			}
			if (mAdapter == null) {
				mTempData = result;
			} else {
				mAdapter.setData(result);
				mAdapter.notifyDataSetChanged();
			}
		}
	};
	private OnNumberChosenListener mListener;
	private ExpandableListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_number_picker, null);
		mAdapter = new ContactsAdapter(inflater);
		mLoadingLayout = v.findViewById(R.id.loading_layout);
		lv = (ExpandableListView) v.findViewById(R.id.numbers_list);
		lv.setAdapter(mAdapter);
		lv.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				mListener.onChosen(mAdapter.getGroup(groupPosition).getName(),
						mAdapter.getChild(groupPosition, childPosition));
				getDialog().dismiss();
				return true;
			}
		});
		if (mTempData != null) {
			mAdapter.setData(mTempData);
			mAdapter.notifyDataSetChanged();
			int count = mAdapter.getGroupCount();
			for (int position = 1; position <= count; position++)
				lv.expandGroup(position - 1);
			mTempData = null;
		}
		getDialog().setTitle("Contacts");
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NORMAL, R.style.AppTheme);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		new PickNumbersTask(activity, mOnContactsObtainedListener).execute();
	}

	public void setOnDismissListener(OnNumberChosenListener l) {
		mListener = l;
	}
}
