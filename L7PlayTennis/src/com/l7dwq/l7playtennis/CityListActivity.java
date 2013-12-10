package com.l7dwq.l7playtennis;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.kankan.logging.Logger;
import com.l7dwq.l7playtennis.adapter.BaseRegionInfoAdapter;
import com.l7dwq.l7playtennis.contract.BaseRegionInfo;
import com.l7dwq.l7playtennis.contract.CountryInfo;
import com.l7dwq.l7playtennis.util.GpsHelper;
import com.l7dwq.l7playtennis.util.LocationHelper;
import com.stanley.core.util.StringEx;
import com.stanley.core.util.ViewUtils;

public class CityListActivity extends Activity implements BDLocationListener {
    private final static Logger LOG = Logger.getLogger(FriendListActivity.class);
    private TextView mGpsCurrentCity;
    private TextView mCurrentSelectedCity;
    
    private ListView mProvinceListView;
    private ListView mCityListView;
    private BaseRegionInfoAdapter mProvinceAdapter;
    private BaseRegionInfoAdapter mCityAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.city_list);
        mCurrentSelectedCity =  (TextView)this.findViewById(R.id.city_list_current_selected_city);
        mGpsCurrentCity = (TextView) this.findViewById(R.id.city_list_gps_current_city);
        
        mProvinceListView = (ListView) this.findViewById(R.id.city_list_lv_provinces);
        mProvinceAdapter = new BaseRegionInfoAdapter(this, null);
        mProvinceListView.setAdapter(mProvinceAdapter);
        mProvinceListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseRegionInfo regionInfo = (BaseRegionInfo) view.getTag();
                if (regionInfo != null && regionInfo.getSubordinates() != null) {
                    mCityAdapter.setData(regionInfo);
                    mCityAdapter.notifyDataSetChanged();
                }                
            }
        });
        
        mCityListView = (ListView) this.findViewById(R.id.city_list_lv_cities);
        mCityAdapter = new BaseRegionInfoAdapter(this, null);
        mCityListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseRegionInfo regionInfo = (BaseRegionInfo) view.getTag();
                if (regionInfo != null) {
                    String selection = getString(R.string.your_choice_city) + regionInfo.name;
                    mCurrentSelectedCity.setText(selection);
                    L7Application.cacheData.currentCity = regionInfo.name;
                } 
                
            }
        });
        mCityListView.setAdapter(mCityAdapter);
        
        LoadDataTask task = new LoadDataTask();
        task.execute();
        if(StringEx.isNullOrEmpty(L7Application.cacheData.currentCity)){
            GpsHelper.startGpsLocating(this.getApplicationContext(), this);
        }else{
            String selection = getString(R.string.your_choice_city) + L7Application.cacheData.currentCity;
            mGpsCurrentCity.setText(L7Application.cacheData.currentCity);
            mCurrentSelectedCity.setText(selection);
        }
        
        ViewUtils.showLoadingDialog(this, R.string.loading);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        GpsHelper.stopGpsLocating();
    }
    
    class LoadDataTask extends AsyncTask<Void, Void, Void> {
        private CountryInfo countryInfo;
        @Override
        protected Void doInBackground(Void... params) {
            countryInfo = LocationHelper.getCountryInfo();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mProvinceAdapter.setData(countryInfo);
            mProvinceAdapter.notifyDataSetChanged();
            ViewUtils.dismissLoadingDialog(CityListActivity.this);
        }

    }

    @Override
    public void onReceiveLocation(BDLocation loc) {
        L7Application.cacheData.currentCityCode = loc.getCityCode();
        L7Application.cacheData.currentCity = loc.getCity();
        if(!StringEx.isNullOrEmpty(L7Application.cacheData.currentCity)){
            mGpsCurrentCity.setText(L7Application.cacheData.currentCity);
        }
        
    }

    @Override
    public void onReceivePoi(BDLocation loc) {
        // TODO Auto-generated method stub
        
    }
}
