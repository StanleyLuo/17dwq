package com.l7dwq.l7playtennis.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.contract.QueryArg;
import com.stanley.uikit.FilterKeywordsPanel;

public class AgeFilterFragment extends Fragment {

    public AgeFilterFragment(){
        
    }
    
    private FilterKeywordsPanel mFilterKeywordsPanel;
    
    private String titleAge;
    private String titleGender;
    
    public AgeFilterFragment(Context ctx, FilterKeywordsPanel pane){
        mFilterKeywordsPanel = pane;
        titleAge = ctx.getString(R.string.age_is);
        titleGender = ctx.getString(R.string.gender_is);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RadioGroup contentView = (RadioGroup) inflater.inflate(R.layout.age_list, container, false);
        
        contentView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                case R.id.age_list_rb_under_10:
                    mFilterKeywordsPanel.appendFilter(titleAge, new QueryArg("age", "10", QueryArg.QUERY_OPTION_LT));
                    break;
                case R.id.age_list_rb_10_18:
                    mFilterKeywordsPanel.appendFilter(titleAge, new QueryArg("age", "10~18", QueryArg.QUERY_OPTION_BT));
                    break;
                case R.id.age_list_rb_19_25:
                    mFilterKeywordsPanel.appendFilter(titleAge, new QueryArg("age", "19~25", QueryArg.QUERY_OPTION_BT));
                    break;
                case R.id.age_list_rb_26_30:
                    mFilterKeywordsPanel.appendFilter(titleAge, new QueryArg("age", "26~30", QueryArg.QUERY_OPTION_BT));
                    break;
                case R.id.age_list_rb_31_40:
                    mFilterKeywordsPanel.appendFilter(titleAge, new QueryArg("age", "31~40", QueryArg.QUERY_OPTION_BT));
                    break;
                case R.id.age_list_rb_41_50:
                    mFilterKeywordsPanel.appendFilter(titleAge, new QueryArg("age", "41~50", QueryArg.QUERY_OPTION_BT));
                    break;
                case R.id.age_list_rb_above_50:
                    mFilterKeywordsPanel.appendFilter(titleAge, new QueryArg("age", "50", QueryArg.QUERY_OPTION_GT));
                    break;
                default:
                    break;
                }
                
            }
        });
        
        return contentView;
    }
}
