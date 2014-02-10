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
package pl.poznan.put.cs.ify.api.log;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.text.Html;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Overlay view showing YLogs.
 */
public class YLogView {
	private TextView[] mLines = new TextView[YLog.LIST_MAX_SIZE];
	private int mOldest = 0;
	private LinearLayout mLayout;
	private Context mContext;
	private boolean mEnabled = false;

	public YLogView(Context context) {
		mContext = context;
		mLayout = new LinearLayout(context);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		mLayout.setBackgroundColor(Color.TRANSPARENT);
		mLayout.setGravity(Gravity.BOTTOM);
		
		TextView tv = new TextView(mContext);
		tv.setText(Html.fromHtml("<b>YLog</b>"));
		tv.setTextSize(13);
		tv.setBackgroundColor(0xD0000000);
		tv.setSingleLine(true);
		tv.setGravity(Gravity.BOTTOM);
		mLayout.addView(tv);
	}

	public void show() {
		if (!mEnabled) {
			mEnabled = true;
			WindowManager.LayoutParams params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
					WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
							| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
							| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
			params.gravity = Gravity.BOTTOM;
			((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
					.addView(mLayout, params);
			for (YLogEntry entry : YLog.getHistory()) {
				add(entry);
			}
		}
	}

	public boolean isEnabled() {
		return mEnabled;
	}

	public void hide() {
		if (mEnabled) {
			mEnabled = false;
			((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
					.removeView(mLayout);
		}
	}

	/**
	 * Adds entry to displayed logs.
	 */
	public void add(YLogEntry entry) {
		if (mEnabled) {
			if (mLines[mOldest] != null) {
				mLayout.removeView(mLines[mOldest]);
			}
			TextView tv = new TextView(mContext);
			mLines[mOldest] = tv;
			if (++mOldest == mLines.length)
				mOldest = 0;

			tv.setText(Html.fromHtml(entry.toHtml()));
			tv.setTextSize(12);
			tv.setBackgroundColor(0x80000000);
			tv.setSingleLine(true);
			tv.setGravity(Gravity.BOTTOM);
			mLayout.addView(tv);
		}
	}
}
