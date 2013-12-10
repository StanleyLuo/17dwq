package com.l7dwq.l7playtennis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.stanley.core.caching.ImageFetcher;
import com.stanley.core.util.StringEx;

public class UserCenterActivity extends Activity {

	private ImageView mProfileImage;
	private EditText mNickName;
	private EditText mAccount;
	private RadioGroup mGender;
	private TextView mAge;
	private TextView mRegion;
	private ImageFetcher mImageFetcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.user_center);

		if (L7Application.cacheData.currentUserInfo == null) {
			this.finish();
		}
		this.initViews();
		this.populateViews();
	}

	private void initViews() {
		mProfileImage = (ImageView) this.findViewById(R.id.user_center_iv_head);
		mNickName = (EditText) this.findViewById(R.id.user_center_et_nick_name);
		mAccount = (EditText) this.findViewById(R.id.user_center_et_account);
		mAge = (TextView) this.findViewById(R.id.user_center_tv_age);
		mRegion = (TextView) this.findViewById(R.id.user_center_region);
		mGender = (RadioGroup) this.findViewById(R.id.user_center_rg_gender);

		mImageFetcher = ImageFetcher.getInstance(this);
		mImageFetcher.setImageCache(L7Application.getImageCache(this));

	}

	private void populateViews() {
		if (L7Application.cacheData.currentUserInfo != null) {
			mNickName.setText(L7Application.cacheData.currentUserInfo.userName);
			mRegion.setText(L7Application.cacheData.currentUserInfo.getLocation());
			if (!StringEx
					.isNullOrEmpty(L7Application.cacheData.currentUserInfo.avatarUrl)) {
				mImageFetcher.loadImage(
						L7Application.cacheData.currentUserInfo.avatarUrl,
						mProfileImage);
			}
		}

	}

	/**
	 * Relative to layout: header.xml When be called when "common_back" button
	 * is clicked.
	 */
	public void onLeftHeaderButtonClick(View view) {
		this.finish();
	}

	public void onRightHeaderButtonClick(View view) {

	}
}
