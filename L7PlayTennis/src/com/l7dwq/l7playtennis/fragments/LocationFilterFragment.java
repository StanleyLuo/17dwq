package com.l7dwq.l7playtennis.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.l7dwq.l7playtennis.L7Application;
import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.adapter.BaseRegionInfoAdapter;
import com.l7dwq.l7playtennis.contract.BaseRegionInfo;
import com.l7dwq.l7playtennis.contract.QueryArg;
import com.l7dwq.l7playtennis.util.LocationHelper;
import com.stanley.uikit.FilterKeywordsPanel;

public class LocationFilterFragment extends Fragment {

    public LocationFilterFragment(){
        
    }
    
    private ListView mDistrictListView;
    private ListView mStreetListView;

    private BaseRegionInfoAdapter mDistrictAdapter;
    private BaseRegionInfoAdapter mStreetAdapter;
//    private String mSelectedCity = "深圳";
//    private String mSelectedDistrict;
//    private String mSelectedStreet;

    private FilterKeywordsPanel mFilterKeywordsPanel;
    

    
    public LocationFilterFragment(Context ctx, FilterKeywordsPanel pane){
        mFilterKeywordsPanel = pane;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentRoot = inflater.inflate(R.layout.location_list, container, false);
        mDistrictListView = (ListView)contentRoot.findViewById(R.id.location_list_lv_district);

        mDistrictListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseRegionInfo regionInfo = (BaseRegionInfo) view.getTag();
                if (regionInfo != null && regionInfo.getSubordinates() != null) {
                    //mFilterArgs.mSelectedDistrict = regionInfo.name;
                    mFilterKeywordsPanel.appendFilter("区", new QueryArg("district", regionInfo.name, QueryArg.QUERY_OPTION_LIKE));
                    mStreetAdapter.setData(regionInfo);
                    mStreetAdapter.notifyDataSetChanged();
                }
            }
        });
        mStreetListView = (ListView) contentRoot.findViewById(R.id.location_list_lv_street);
        mStreetListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseRegionInfo regionInfo = (BaseRegionInfo) view.getTag();
                if (regionInfo != null) {
                    mFilterKeywordsPanel.appendFilter("街道", new QueryArg("street", regionInfo.name, QueryArg.QUERY_OPTION_LIKE));
                }
                
            }
        });
        mDistrictAdapter = new BaseRegionInfoAdapter(this.getActivity(), null);
        mStreetAdapter = new BaseRegionInfoAdapter(this.getActivity(), null);
        mDistrictListView.setAdapter(mDistrictAdapter);
        mStreetListView.setAdapter(mStreetAdapter);

        if (L7Application.cacheData.currentCityInfo == null) {
            LoadDataTask task = new LoadDataTask();
            task.execute();
        } else {
            mDistrictAdapter.setData(L7Application.cacheData.currentCityInfo);
            mDistrictAdapter.notifyDataSetChanged();
        }
        return contentRoot;
    }
    

    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            
            L7Application.cacheData.currentCityInfo = 
                    LocationHelper.getCityInfo(L7Application.cacheData.currentCityCode);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mDistrictAdapter.setData(L7Application.cacheData.currentCityInfo);
            mDistrictAdapter.notifyDataSetChanged();
        }

    }


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        TextView selectedTextView = (TextView) view;
//        if(parent == mDistrictListView){
//            
//        }else if(parent == mStreetListView){
//            
//        }
//        
//    }

}
