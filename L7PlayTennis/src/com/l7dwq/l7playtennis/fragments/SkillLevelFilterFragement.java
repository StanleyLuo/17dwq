package com.l7dwq.l7playtennis.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.l7dwq.l7playtennis.R;
import com.stanley.uikit.FilterKeywordsPanel;

public class SkillLevelFilterFragement extends Fragment {
    
    public SkillLevelFilterFragement(){
        
    }
    
    private FilterKeywordsPanel mFilterKeywordsPanel;
    

    
    public SkillLevelFilterFragement(Context ctx, FilterKeywordsPanel pane){
        mFilterKeywordsPanel = pane;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.skill_level_list, container, false);
    }
}
