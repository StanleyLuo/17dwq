package com.l7dwq.l7playtennis.adapter;

import java.text.DateFormat;
import java.util.Date;

import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.l7dwq.l7playtennis.L7Application;
import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.contract.L7Message;

public class ChatMessageListAdapter extends BaseAdapter {

    private SparseArray<L7Message> mMessageList = new SparseArray<L7Message>(100);

    private DateFormat dateFormat = DateFormat.getDateInstance();
    private DateFormat timeFormat = DateFormat.getTimeInstance();

    public int getCount() {
        return mMessageList.size();
    }

    public void appendMessage(L7Message msg) {
        mMessageList.put(msg.id, msg);
        this.notifyDataSetChanged();
    }

    public void removeMessage(int msgId) {
        mMessageList.remove(msgId);
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout itemView = null;
        int key = mMessageList.keyAt(position);
        L7Message msg = mMessageList.get(key);

        if (convertView == null) {
            if (msg.fromUid == L7Application.cacheData.currentUserInfo.uid) {
                itemView = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_to,
                        parent, false);
            } else if (msg.toUid == L7Application.cacheData.currentUserInfo.uid) {
                itemView = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_from,
                        parent, false);
            }
        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView contentView = (TextView) itemView.findViewById(R.id.chat_item_from_content);
        TextView sendTimeTextView = (TextView) itemView.findViewById(R.id.chat_item_from_time);
        LinearLayout.LayoutParams lp = (LayoutParams) contentView.getLayoutParams();

        if (L7Application.cacheData.currentUserInfo.uid == msg.fromUid) {
            contentView.setBackgroundResource(R.drawable.chat_to_bg_selector);
            lp.gravity = Gravity.RIGHT;
            lp.leftMargin = 40;
        } else if (L7Application.cacheData.currentUserInfo.uid == msg.toUid) {
            contentView.setBackgroundResource(R.drawable.chat_from_bg_selector);
            lp.gravity = Gravity.LEFT;
            lp.rightMargin = 40;
        }

        contentView.setText(msg.content);
        Date today = new Date();
        if (msg.sendTime.getDate() == today.getDate() && msg.sendTime.getMonth() == today.getMonth()) {
            // Today, only show time.
            sendTimeTextView.setText(timeFormat.format(msg.sendTime));
        } else {
            sendTimeTextView.setText(dateFormat.format(msg.sendTime));
        }

        return itemView;
    }

}
