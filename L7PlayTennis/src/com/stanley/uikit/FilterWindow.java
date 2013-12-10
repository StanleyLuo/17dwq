package com.stanley.uikit;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.adapter.FilterWindowPagerAdapter;
import com.l7dwq.l7playtennis.contract.QueryArg;
import com.stanley.core.util.StringEx;

public class FilterWindow extends PopupWindow implements View.OnClickListener {

    private View mContainer;
    //private ViewFlipper mViewFlipper;
    private ViewPager mViewPager;
    private TextView mTabBtn1;
    private TextView mTabBtn2;
    private TextView mTabBtn3;


    
    
//    private String mSelectedCity = "深圳";
//    private String mSelectedDistrict;
//    private String mSelectedStreet;
    
    //private FilterArgs mFilterArgs;
    private Button mOkButton;
    private Button mCancelButton;
    public Animation slideLeftIn;
    public Animation slideLeftOut;
    public Animation slideRightIn;
    public Animation slideRightOut;
    
    private static final int SWIPE_MIN_DISTANCE = 120;  
    private static final int SWIPE_MAX_OFF_PATH = 250;  
    private static final int SWIPE_THRESHOLD_VELOCITY = 200; 
    
    //private GestureDetector gestureDetector;
    
    private int mCurrentSelectedTabIndex = 0;
    private FilterWindowPagerAdapter mViewPagerAdapter;
    

    public FilterWindow(FragmentActivity ctx) {
        super(LayoutInflater.from(ctx).inflate(R.layout.filter_window,
                null), WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        mContainer = this.getContentView();
        
        // super(mContainer, WindowManager.LayoutParams.WRAP_CONTENT,
        // WindowManager.LayoutParams.WRAP_CONTENT, true);
        mViewPager = (ViewPager) this.mContainer
                .findViewById(R.id.filter_window_view_pager);
        //mViewPagerAdapter = new FilterWindowPagerAdapter(ctx);
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabBtn1 = (TextView) this.mContainer
                .findViewById(R.id.filter_window_tab1);
        mTabBtn1.setOnClickListener(this);

        mTabBtn2 = (TextView) this.mContainer
                .findViewById(R.id.filter_window_tab2);
        mTabBtn2.setOnClickListener(this);

        mTabBtn3 = (TextView) this.mContainer
                .findViewById(R.id.filter_window_tab3);
        mTabBtn3.setOnClickListener(this);


        
        mOkButton = (Button) this.mContainer.findViewById(R.id.filter_window_btn_ok);
        mOkButton.setOnClickListener(this);
        mCancelButton = (Button)this.mContainer.findViewById(R.id.filter_window_btn_cancel);
        mCancelButton.setOnClickListener(this);
        
        slideLeftIn = AnimationUtils.loadAnimation(ctx, R.anim.left_in);
        slideLeftOut = AnimationUtils.loadAnimation(ctx, R.anim.left_out);
        slideRightIn = AnimationUtils.loadAnimation(ctx, R.anim.right_in);
        
        slideRightOut = AnimationUtils.loadAnimation(ctx, R.anim.right_out);
        
        //gestureDetector = new GestureDetector(ctx, new SimpleGestureDector());
//        mViewFlipper.setOnTouchListener(new OnTouchListener() {
//            
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                gestureDetector.onTouchEvent(event);
//                
//                return true;
//            }
//        });
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
            this.dismiss();
            break;
        case R.id.filter_window_btn_cancel:
            //this.resetFilterArgs();
            this.dismiss();
            break;
        default:
            break;
        }

    }

   
   

//    class SimpleGestureDector extends SimpleOnGestureListener{
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)  
//                return false;  
//            // right to left swipe  
//            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {  
//                prepareAnimaton(true);
//                mViewFlipper.showNext();  
//            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {  
//                prepareAnimaton(false);
//                mViewFlipper.showPrevious();  
//            }  
//            return false;
//        }
//    }

//    private void prepareAnimaton(boolean nextOrPrevious){
//        if(nextOrPrevious){
//            mViewFlipper.setInAnimation(slideRightIn);  
//            mViewFlipper.setOutAnimation(slideLeftOut); 
//        }else{
//            mViewFlipper.setInAnimation(slideLeftIn);  
//            mViewFlipper.setOutAnimation(slideRightOut); 
//        }
//    }
    
}
