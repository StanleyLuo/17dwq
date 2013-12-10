package com.l7dwq.l7playtennis;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.l7dwq.l7playtennis.util.GpsHelper;
import com.stanley.support.update.AppUpgradeManager;

public class MainActivity extends Activity implements OnClickListener, BDLocationListener{

	protected static final String TAG = MainActivity.class.getSimpleName();
	
    private ImageButton mSettingBtn = null;
	private ImageButton mUserCenterBtn = null;
	private ImageButton mFindFriendBtn = null;
	private ImageButton mFindCoachBtn = null;
	private ImageButton mFindCourtBtn = null;
	private ImageButton mNearByBtn = null;
	private TextView mCurrentLocationTips;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        mSettingBtn = (ImageButton) this.findViewById(R.id.home_btn_setting);
        mSettingBtn.setOnClickListener(this);
        
        mUserCenterBtn = (ImageButton) this.findViewById(R.id.home_btn_user_center);
        mUserCenterBtn.setOnClickListener(this);
        mFindFriendBtn = (ImageButton) this.findViewById(R.id.home_btn_find_friends);
        mFindFriendBtn.setOnClickListener(this);
        
        mFindCoachBtn = (ImageButton) this.findViewById(R.id.home_btn_find_coach);
        mFindCoachBtn.setOnClickListener(this);
        
        mFindCourtBtn = (ImageButton) this.findViewById(R.id.home_btn_find_court);
        mFindCourtBtn.setOnClickListener(this);
        
        mNearByBtn = (ImageButton) this.findViewById(R.id.home_btn_nearby);
        mNearByBtn.setOnClickListener(this);
        mCurrentLocationTips = (TextView) this.findViewById(R.id.home_tv_current_location);
        
        GpsHelper.startGpsLocating(this.getApplicationContext(), this);
        mCurrentLocationTips.setText(R.string.locating_your_position);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GpsHelper.stopGpsLocating();
    }
    
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_btn_setting:
			Toast.makeText(this, R.string.checking_new_version, Toast.LENGTH_LONG).show();
	        AppUpgradeManager updateManager = new AppUpgradeManager();
	        updateManager.startNewVersionCheck(this, ServerUrls.getServerUrl4Upgrade());
			break;
		case R.id.home_btn_user_center:
			Intent intent = new Intent(this, UserCenterActivity.class);
			this.startActivity(intent);
			break;
		case R.id.home_btn_find_friends:
			Intent open = new Intent(this, FriendListActivity.class);
			this.startActivity(open);			
			break;
		case R.id.home_btn_find_coach:
            Intent openCoach = new Intent(this, CoachListActivity.class);
            this.startActivity(openCoach);
		    
			break;
		case R.id.home_btn_find_court:
            Intent openCourt = new Intent(this, CourtListActivity.class);
            this.startActivity(openCourt);
			break;
		case R.id.home_btn_nearby:
			Intent open2 = new Intent(this, CityListActivity.class);
			this.startActivity(open2);				
			break;
		default:
			break;
		}
		
	}

    @Override
    public void onReceiveLocation(BDLocation location) {
        String addStr = location.getAddrStr();
        String city = location.getCity();
        L7Application.cacheData.currentCity = city;
        L7Application.cacheData.currentCityCode = location.getCityCode();
        mCurrentLocationTips.setText(addStr);
        
    }

    @Override
    public void onReceivePoi(BDLocation location) {

    }  

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){
            showSettingDialog();
        }
        return super.onKeyUp(keyCode, event);
    }
    

    
    private void showSettingDialog() {
        final Dialog  serverSettingDialog = new Dialog(this);
        serverSettingDialog.setContentView(R.layout.setting);
        serverSettingDialog.setCancelable(true);
        serverSettingDialog.setTitle(R.string.server_setting);
        final EditText severUrlEt = (EditText) serverSettingDialog.findViewById(R.id.setting_server_et_url);
        final Spinner  optionSp = (Spinner) serverSettingDialog.findViewById(R.id.setting_server_sp_option);
        final Button okButton = (Button)serverSettingDialog.findViewById(R.id.setting_ok);

        String[] options = new String[]{"Product", "Test"};
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);

        optionSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    severUrlEt.setText(ServerUrls.SERVER_URL);
                } else {
                    severUrlEt.setText(ServerUrls.SERVER_URL_TEST);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });
        optionSp.setAdapter(stringAdapter);
        severUrlEt.setText(ServerUrls.SERVER_URL);
        serverSettingDialog.setOnDismissListener(new OnDismissListener() {
            
            @Override
            public void onDismiss(DialogInterface dialog) {
                L7Application.serverUrl = severUrlEt.getText().toString();
                Log.d(TAG, "serverUrl: " + L7Application.serverUrl);
                
            }
        });
        okButton.setOnClickListener(new  OnClickListener() {
            
            @Override
            public void onClick(View v) {
                L7Application.serverUrl = severUrlEt.getText().toString();
                serverSettingDialog.dismiss();
            }
        });
        serverSettingDialog.show();
    }
}
