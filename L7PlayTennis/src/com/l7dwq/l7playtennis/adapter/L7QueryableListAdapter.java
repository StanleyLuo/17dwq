package com.l7dwq.l7playtennis.adapter;

import java.lang.reflect.Field;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.stanley.core.caching.ImageFetcher;

public abstract class L7QueryableListAdapter<T> extends BaseAdapter {

    public L7QueryableListAdapter(ImageFetcher fetcher) {
        mImageFetcher = fetcher;
    }

    private List<T> mData;
    protected ImageFetcher mImageFetcher;

    public void setData(List<T> list) {
        mData = list;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {

        return mData != null ? mData.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup itemView = null;
        if (convertView == null) {
            itemView = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(getItemViewLayoutResId(), null);
        } else {
            itemView = (ViewGroup) convertView;
        }

        T dataPoint = mData.get(position);
        int childCount = itemView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View dataPointView = itemView.getChildAt(i);
            onDataPointReady(dataPointView, dataPoint);
        }

        return itemView;
    }

    public abstract void onDataPointReady(View dataPointView, T entry);

    public abstract DataPointViewMapping[] getDataPointViewIdMapping();

    public abstract int getItemViewLayoutResId();
}
