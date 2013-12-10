package com.l7dwq.l7playtennis.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.contract.BaseRegionInfo;

public class BaseRegionInfoAdapter extends BaseAdapter {

	private Context mContext;
	private BaseRegionInfo mRegionInfo;
	private Drawable mItemViewRightDrawableViewMore;

	public BaseRegionInfoAdapter(Context ctx, BaseRegionInfo regionInfo) {
		mContext = ctx;
		mRegionInfo = regionInfo;
		mItemViewRightDrawableViewMore = ctx.getResources().getDrawable(
				R.drawable.more_arrow);
	}

	public void setData(BaseRegionInfo data) {
		mRegionInfo = data;
	}

	@Override
	public int getCount() {

		return mRegionInfo != null && mRegionInfo.getSubordinates() != null ? mRegionInfo
				.getSubordinates().size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView itemView = null;
		if (convertView == null) {
			itemView = (TextView) LayoutInflater.from(mContext).inflate(
					R.layout.location_list_item, null);
		} else {
			itemView = (TextView) convertView;
		}
		if (mRegionInfo != null) {
			BaseRegionInfo subordinate = mRegionInfo.getSubordinates().get(
					position);
			itemView.setText(subordinate.name);
			itemView.setTag(subordinate);
			if (subordinate.getSubordinates() != null) {
				itemView.setCompoundDrawables(null, null,
						mItemViewRightDrawableViewMore, null);
			} else {
				itemView.setCompoundDrawables(null, null, null, null);
			}
		}

		return itemView;
	}

}
