package com.l7dwq.l7playtennis;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.l7dwq.l7playtennis.contract.L7TennisCourt;
import com.l7dwq.l7playtennis.misc.Constants;
import com.stanley.core.sys.PhoneUtils;

public class CourtDetailActivity extends L7BaseActivity implements OnClickListener {

    private L7TennisCourt mCourtInfo;
    private TextView mTitle;
    private TextView mPrice;
    private TextView mTelephone;
    private ImageView mPoster;
    private TextView mAddress;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.court_detail);
        this.initViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.populateViews();
    }

    private void initViews() {
        mTitle = (TextView) this.findViewById(R.id.court_detail_tv_title);
        mAddress = (TextView) this.findViewById(R.id.court_detail_tv_address);
        mPrice = (TextView) this.findViewById(R.id.court_detail_tv_price);
        mTelephone = (TextView) this.findViewById(R.id.court_detail_tv_tel);
        mPoster = (ImageView) this.findViewById(R.id.court_detail_iv_poster);
        mDescription = (TextView) this.findViewById(R.id.court_detail_tv_description);

        mTelephone.setOnClickListener(this);
    }

    private void populateViews() {
        Intent intent = this.getIntent();
        if (intent != null) {
            mCourtInfo = (L7TennisCourt) intent.getSerializableExtra(Constants.IntentKeys.COURT_INFO);
            if (mCourtInfo != null) {
                mTitle.setText(mCourtInfo.name);
                mPrice.setText(mCourtInfo.price + "");
                mAddress.setText(mCourtInfo.province + "." + mCourtInfo.city + "." + mCourtInfo.district);
                mTelephone.setText(mCourtInfo.phone);
                if (!TextUtils.isEmpty(mCourtInfo.picture)) {
                    getImageFetcher().loadImage(mCourtInfo.picture, mPoster);
                }
                mDescription.setText(mCourtInfo.businessHours);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.court_detail_tv_tel:
            if (mCourtInfo != null) {
                PhoneUtils.dial(this, mCourtInfo.phone);
            }
            break;

        default:
            break;
        }

    }
}
