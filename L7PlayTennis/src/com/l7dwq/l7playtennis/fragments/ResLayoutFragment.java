package com.l7dwq.l7playtennis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResLayoutFragment extends Fragment {
    
    private int mLayoutResId;
    public ResLayoutFragment(int layoutResId){
        mLayoutResId = layoutResId;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(mLayoutResId, container, false);
    }
}
