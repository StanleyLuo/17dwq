package com.l7dwq.l7playtennis.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.l7dwq.l7playtennis.fragments.AgeFilterFragment;
import com.l7dwq.l7playtennis.fragments.LocationFilterFragment;
import com.l7dwq.l7playtennis.fragments.SkillLevelFilterFragement;
import com.stanley.uikit.FilterKeywordsPanel;

public class FilterWindowPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private List<View> mViewList = new ArrayList<View>();

    private FragmentManager mFragmentManager;

    public FilterWindowPagerAdapter(FragmentActivity ctx, FilterKeywordsPanel keywordPanel) {
        super(ctx.getSupportFragmentManager());
        mFragmentManager = ctx.getSupportFragmentManager();

        mFragmentList.add(new LocationFilterFragment(ctx, keywordPanel));
        mFragmentList.add(new SkillLevelFilterFragement(ctx, keywordPanel));
        mFragmentList.add(new AgeFilterFragment(ctx, keywordPanel));
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = mFragmentList.get(position);
//        switch (position) {
//        case 0:
//            f = mFragmentManager.findFragmentById(R.id.f1);
//            break;
//        case 1:
//            f = mFragmentManager.findFragmentById(R.id.f2);
//            break;
//        case 2:
//            f = mFragmentManager.findFragmentById(R.id.f3);
//            break;
//        default:
//            break;
//        }
        
        return f;
    }

}
