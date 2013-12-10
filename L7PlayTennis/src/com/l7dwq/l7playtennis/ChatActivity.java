package com.l7dwq.l7playtennis;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.l7dwq.l7playtennis.adapter.ChatMessageListAdapter;
import com.l7dwq.l7playtennis.contract.L7Message;

public class ChatActivity extends Activity implements OnClickListener {

    private ListView mMessageListView;
    private Button mSendButton;
    private EditText mMessage2Send;
    private ChatMessageListAdapter mMessageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.chat);
        mMessageListView = (ListView) this.findViewById(R.id.chat_lv_message_list);
        mSendButton = (Button) this.findViewById(R.id.chat_btn_send);
        mMessage2Send = (EditText) this.findViewById(R.id.chat_et_msg2send);

        mMessageListAdapter = new ChatMessageListAdapter();
        mMessageListView.setAdapter(mMessageListAdapter);
        mSendButton.setOnClickListener(this);

    }

    private int toOrFromFlag = 0;

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.chat_btn_send:
            L7Message msg2Send = new L7Message();
            msg2Send.content = mMessage2Send.getText().toString();
            if (toOrFromFlag % 2 == 0) {
                msg2Send.fromUid = L7Application.cacheData.currentUserInfo.uid;
                msg2Send.fromUserName = L7Application.cacheData.currentUserInfo.userName;
            }else{
                msg2Send.toUid = L7Application.cacheData.currentUserInfo.uid;
                msg2Send.toUserName = L7Application.cacheData.currentUserInfo.userName;                
            }
            toOrFromFlag = toOrFromFlag + 1;
            msg2Send.sendTime = new Date();
            msg2Send.id = toOrFromFlag;

            mMessageListAdapter.appendMessage(msg2Send);
            mMessage2Send.setText("");
            break;

        default:
            break;
        }
    }
}
