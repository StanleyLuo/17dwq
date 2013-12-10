package com.l7dwq.l7playtennis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.l7dwq.l7playtennis.contract.L7UserInfo;
import com.l7dwq.l7playtennis.misc.Constants;
import com.stanley.core.caching.ImageFetcher;

public class FriendProfileActivity extends Activity implements OnClickListener {

    private TextView mName;
    private TextView mSignaure;

    private ImageView mAvator;
    private ImageFetcher mImageFetcher;
    
    private Button mSendMsgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.friend_profile);

        mName = (TextView) this.findViewById(R.id.friend_profile_tv_nickname);
        mSignaure = (TextView) this.findViewById(R.id.friend_profile_tv_signature);

        mAvator = (ImageView) this.findViewById(R.id.friend_profile_avator);
        
        mImageFetcher = ImageFetcher.getInstance(this);
        mImageFetcher.setImageCache(L7Application.getImageCache(this));
        
        mSendMsgButton = (Button) this.findViewById(R.id.friend_profile_btn_send_msg);
        mSendMsgButton.setOnClickListener(this);
        
        this.fillData();
    }

    private void fillData() {
        Intent intent = this.getIntent();
        L7UserInfo userInfo = (L7UserInfo) intent.getSerializableExtra(Constants.IntentKeys.SELECTED_USER_INFO);

        if (userInfo != null) {
            mName.setText(userInfo.userName);
            
            mImageFetcher.loadImage(userInfo.avatarUrl, mAvator);
            
        }
    }

    public void onClick(View v) {
       switch (v.getId()) {
    case R.id.friend_profile_btn_send_msg:
        Intent openActivity = new Intent(this, ChatActivity.class);
        this.startActivity(openActivity);
        break;

    default:
        break;
    }
        
    }
}
