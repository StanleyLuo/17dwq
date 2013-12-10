package com.l7dwq.l7playtennis;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.l7dwq.l7playtennis.contract.L7CoachInfo;
import com.l7dwq.l7playtennis.misc.Constants;
import com.stanley.core.sys.PhoneUtils;

public class CoachDetailActivity extends L7BaseActivity implements OnClickListener {

    private L7CoachInfo mCoachInfo;
    private TextView mName;
    private TextView mQQ;
    private TextView mMobilePhone;
    private ImageView mPoster;
    private ImageView mGender;
    private TextView mAddress;
    private TextView mDescription;
    private Button mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.coach_detail);
        this.initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.populateViews();
    }

    private void initViews() {
        mName = (TextView) this.findViewById(R.id.coach_detail_tv_title);
        mAddress = (TextView) this.findViewById(R.id.coach_detail_tv_address);
        mQQ = (TextView) this.findViewById(R.id.coach_detail_tv_qq);
        mMobilePhone = (TextView) this.findViewById(R.id.coach_detail_tv_tel);
        mPoster = (ImageView) this.findViewById(R.id.coach_detail_iv_poster);
        mGender = (ImageView) this.findViewById(R.id.coach_detail_iv_gender);
        mDescription = (TextView) this.findViewById(R.id.coach_detail_tv_description);
        mCall = (Button) this.findViewById(R.id.coach_detail_btn_call);
        mCall.setOnClickListener(this);
    }

    private void populateViews() {
        Intent intent = this.getIntent();
        if (intent != null) {
            mCoachInfo = (L7CoachInfo) intent.getSerializableExtra(Constants.IntentKeys.COACH_INFO);
            if (mCoachInfo != null) {
                mName.setText(mCoachInfo.name);
                mAddress.setText(mCoachInfo.province + "." + mCoachInfo.city + "." + mCoachInfo.district);
                // mQQ.setText(mCoachInfo.)
                mMobilePhone.setText(mCoachInfo.phone);
                if (mCoachInfo.sex == 1) {
                    mGender.setImageResource(R.drawable.symbol_male);
                } else {
                    mGender.setImageResource(R.drawable.symbol_female);
                }
                if (!TextUtils.isEmpty(mCoachInfo.photo)) {
                    getImageFetcher().loadImage(mCoachInfo.photo, mPoster);
                }
                mDescription.setText(mCoachInfo.content);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.coach_detail_btn_call:
            if (mCoachInfo != null) {
                PhoneUtils.dial(this, mCoachInfo.phone);
            }
            break;

        default:
            break;
        }

    }
}
