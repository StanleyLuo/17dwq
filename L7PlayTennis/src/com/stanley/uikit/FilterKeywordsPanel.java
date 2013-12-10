package com.stanley.uikit;

import java.util.Collection;
import java.util.HashMap;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.contract.QueryArg;

public class FilterKeywordsPanel extends LinearLayout {
    
    //private Drawable mRightDeleteIcon;
    private ColorStateList mTextColorState;
    
    private HashMap<String, TextView> mFilterKeyWordTextViews = new HashMap<String, TextView>();

    public FilterKeywordsPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(LinearLayout.VERTICAL);
        //mRightDeleteIcon = this.getContext().getResources().getDrawable(R.drawable.btn_delete);
        mTextColorState = this.getContext().getResources().getColorStateList(R.drawable.filter_keyword_text_color_selector);
        TextView title = new TextView(context);
        title.setText(R.string.your_choice);
        this.addView(title);
    }

    public void appendFilter(final String displayFilterName, QueryArg fiterArg) {
        TextView filterTextView = null;
        if(mFilterKeyWordTextViews.containsKey(displayFilterName)){
            filterTextView = mFilterKeyWordTextViews.get(displayFilterName);
            filterTextView.setTag(fiterArg);
        }else{
            filterTextView = new TextView(getContext());
            filterTextView.setTextColor(mTextColorState);
            filterTextView.setCompoundDrawablePadding(10);
            filterTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.btn_delete, 0);

            filterTextView.setTag(fiterArg);
            
            filterTextView.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    
                    TextView tv = mFilterKeyWordTextViews.remove(displayFilterName);
                    FilterKeywordsPanel.this.removeView(tv);
                    
                }
            });
            mFilterKeyWordTextViews.put(displayFilterName, filterTextView);
            this.addView(filterTextView);
        }
        filterTextView.setText(displayFilterName + ": " + fiterArg.value);
    }
    
    public void clearAllFilters(){
        this.removeAllViews();
        mFilterKeyWordTextViews.clear();
    }
    
    public QueryArg[] getQueryArgs(){
        Collection<TextView> views = mFilterKeyWordTextViews.values();
        QueryArg[] argArray = new QueryArg[views.size()];
        int i = 0;
        for (TextView tv : views) {
            argArray[i++] = (QueryArg) tv.getTag();
        }
        
        return argArray;
    }

}
