package com.l7dwq.l7playtennis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.l7dwq.l7playtennis.adapter.FilterWindowPagerAdapter;
import com.stanley.uikit.FilterKeywordsPanel;

public class FilterActivity extends FragmentActivity implements View.OnClickListener {
    // private View mContainer;
    // private ViewFlipper mViewFlipper;
    
    public final static String INTENT_KEY_QUERY_ARGS = "QueryArgs";
    
    public final static int RESULT_CODE = 1;
    
    private ViewPager mViewPager;
    private TextView mTabBtn1;
    private TextView mTabBtn2;
    private TextView mTabBtn3;

    private Button mOkButton;
    private Button mCancelButton;
    public Animation slideLeftIn;
    public Animation slideLeftOut;
    public Animation slideRightIn;
    public Animation slideRightOut;
    
    private FilterKeywordsPanel mKeywordsPane;

    private FilterWindowPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.filter_window);
        // WindowManager.LayoutParams.WRAP_CONTENT, true);
        
        mKeywordsPane = (FilterKeywordsPanel) this.findViewById(R.id.filter_window_keyword_pane);
        mViewPager = (ViewPager) this
                .findViewById(R.id.filter_window_view_pager);
        mViewPagerAdapter = new FilterWindowPagerAdapter(this, mKeywordsPane);
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabBtn1 = (TextView) this.findViewById(R.id.filter_window_tab1);
        mTabBtn1.setOnClickListener(this);

        mTabBtn2 = (TextView) this.findViewById(R.id.filter_window_tab2);
        mTabBtn2.setOnClickListener(this);

        mTabBtn3 = (TextView) this.findViewById(R.id.filter_window_tab3);
        mTabBtn3.setOnClickListener(this);

        mOkButton = (Button) this.findViewById(R.id.filter_window_btn_ok);
        mOkButton.setOnClickListener(this);
        mCancelButton = (Button) this.findViewById(R.id.filter_window_btn_cancel);
        mCancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.filter_window_tab1:
            mViewPager.setCurrentItem(0);
            break;

        case R.id.filter_window_tab2:
            mViewPager.setCurrentItem(1);
            break;

        case R.id.filter_window_tab3:
            mViewPager.setCurrentItem(2);

            break;
        case R.id.filter_window_btn_ok:
            Intent intent = new Intent();
            intent.putExtra(INTENT_KEY_QUERY_ARGS, mKeywordsPane.getQueryArgs());
            this.setResult(RESULT_CODE, intent);
            this.finish();
            break;
        case R.id.filter_window_btn_cancel:
            mKeywordsPane.clearAllFilters();
            this.setResult(RESULT_CODE);
            this.finish();
            break;
        default:
            break;
        }

    }


}
