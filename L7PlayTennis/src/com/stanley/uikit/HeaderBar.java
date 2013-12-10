package com.stanley.uikit;

import com.l7dwq.l7playtennis.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HeaderBar extends RelativeLayout {

	private TextView mTitleTextView;
	private Button mLeftButton;
	private Button mRightButton;
	private Drawable mLeftButtonBg;
	private Drawable mRightButtonBg;
	private Drawable mTitleTextViewButtonBg;
	private CharSequence mTitle;
	private CharSequence mLeftButtonText;
	private CharSequence mRightButtonText;
	private int mRightButtonVisible;
	private int mLeftButtonVisible;

	public HeaderBar(Context context, AttributeSet attrs) {
		this(context, attrs, R.style.headerTitleBarStyle);
	}

	public HeaderBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.headerbar, this, true);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);

		mLeftButtonBg = a.getDrawable(R.styleable.HeaderBar_leftButtonBackground);
		if (mLeftButtonBg == null) {
			mLeftButtonBg = context.getResources().getDrawable(R.drawable.back_btn_selector);
		}

		mRightButtonBg = a.getDrawable(R.styleable.HeaderBar_rightButtonBackground);
		if (mRightButtonBg == null) {
			mRightButtonBg = context.getResources().getDrawable(R.drawable.refresh);
		}

		mTitleTextViewButtonBg = a.getDrawable(R.styleable.HeaderBar_titleTextViewBackground);
		mTitle = a.getText(R.styleable.HeaderBar_title);

		mLeftButtonVisible = a.getInt(R.styleable.HeaderBar_leftButtonVisible, 0);
		mRightButtonVisible = a.getInt(R.styleable.HeaderBar_rightButtonVisible, 0);

		mLeftButtonText = a.getText(R.styleable.HeaderBar_leftButtonText);
		mRightButtonText = a.getText(R.styleable.HeaderBar_rightButtonText);
		a.recycle();
	}

	@Override
	protected void onFinishInflate() {
		//super.onFinishInflate();
		this.mTitleTextView = (TextView) this.findViewById(R.id.tv_header_title);
		this.mLeftButton = (Button) this.findViewById(R.id.btn_header_left);
		this.mRightButton = (Button) this.findViewById(R.id.btn_header_right);
		this.mLeftButton.setBackgroundDrawable(this.mLeftButtonBg);
		this.mRightButton.setBackgroundDrawable(this.mRightButtonBg);
		this.mLeftButton.setVisibility(mLeftButtonVisible);
		this.mRightButton.setVisibility(mRightButtonVisible);

		if (this.mTitleTextViewButtonBg != null) {
			//titleTextViewButtonBg = context.getResources().getDrawable(R.drawable.refresh);
			this.mTitleTextView.setBackgroundDrawable(this.mTitleTextViewButtonBg);
		}

		if (this.mTitle != null) {
			this.mTitleTextView.setText(this.mTitle);
		}

		if (this.mLeftButtonText != null) {
			this.mLeftButton.setText(mLeftButtonText);
		}
		if (this.mRightButtonText != null) {
			this.mRightButton.setText(mRightButtonText);
		}
		this.adjustButtonLayout();
	}

	/**
	 * In order to make sure the title is centered exactly at any condition, use this method.
	 */
	private void adjustButtonLayout() {
		if (this.mLeftButton.getVisibility() != View.GONE && 
				this.mRightButton.getVisibility() != View.GONE) {
			this.mLeftButton.measure(0, 0);
			this.mRightButton.measure(0, 0);
			MarginLayoutParams rbMlp = (MarginLayoutParams) this.mRightButton.getLayoutParams();
			MarginLayoutParams lbMlp = (MarginLayoutParams) this.mRightButton.getLayoutParams();
			int delta = this.mLeftButton.getMeasuredWidth() - this.mRightButton.getMeasuredWidth();
			if (delta > 0) {
				rbMlp.leftMargin = delta;
			} else {
				lbMlp.rightMargin = -delta;
			}
		}

	}

	/**
	 * Relative to layout: header.xml
	 * When be called when "btn_header_left" button is clicked.
	 */
	public void onLeftHeaderButtonClick(View view) {
	}

	/**
	 * Relative to layout: header.xml
	 * When be called when "btn_header_right" button is clicked.
	 */
	public void onRightHeaderButtonClick(View view) {

	}

	public TextView getTitleTextView() {
		return mTitleTextView;
	}

	public Button getLeftButton() {
		return mLeftButton;
	}

	public Button getRightButton() {
		return mRightButton;
	}

	@Override
	public boolean isInEditMode() {
		return true;
	}
}
