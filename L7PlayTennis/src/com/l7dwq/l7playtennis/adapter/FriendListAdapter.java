package com.l7dwq.l7playtennis.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.contract.L7UserInfo;
import com.stanley.core.caching.ImageFetcher;

public class FriendListAdapter extends BaseAdapter {

    private List<L7UserInfo> mUsers;

    private Context mContext;

    private ImageFetcher mImageFetcher;

    public FriendListAdapter(Context ctx, ImageFetcher fetcher) {
        mContext = ctx;
        this.mImageFetcher = fetcher;
    }

    public void setData(List<L7UserInfo> users) {
        this.mUsers = users;
    }

    @Override
    public int getCount() {
        return this.mUsers != null ? this.mUsers.size() : 0;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup root) {
        View itemView = null;
        if (convertView != null) {
            itemView = convertView;
        } else {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.friend_list_item, null);
        }

        L7UserInfo thisUser = this.mUsers.get(position);
        if (thisUser != null) {
            itemView.setTag(thisUser);
        }

        ImageView headImage = (ImageView) itemView.findViewById(R.id.friend_list_item_iv_image);
        this.mImageFetcher.loadImage(thisUser.avatarUrl, headImage);
        TextView userName = (TextView) itemView.findViewById(R.id.friend_list_item_tv_name);

        TextView locaction = (TextView) itemView.findViewById(R.id.friend_list_item_tv_home_location);
        userName.setText(thisUser.userName);
        locaction.setText(thisUser.province + "." + thisUser.city + "." + thisUser.district);

        return itemView;
    }

}
