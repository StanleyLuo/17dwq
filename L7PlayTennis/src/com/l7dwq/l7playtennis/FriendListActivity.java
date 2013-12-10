package com.l7dwq.l7playtennis;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kankan.logging.Logger;
import com.l7dwq.l7playtennis.adapter.FriendListAdapter;
import com.l7dwq.l7playtennis.contract.ActionResult;
import com.l7dwq.l7playtennis.contract.L7UserInfo;
import com.l7dwq.l7playtennis.contract.QueryArg;
import com.l7dwq.l7playtennis.misc.Constants;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.l7dwq.l7playtennis.util.JsonHelper;
import com.stanley.core.caching.ImageFetcher;
import com.stanley.core.util.NetworkHelper;
import com.stanley.core.util.StringEx;
import com.stanley.core.util.ViewUtils;

public class FriendListActivity extends FragmentActivity implements OnClickListener, OnItemClickListener {

    private final static Logger LOG = Logger.getLogger(FriendListActivity.class);

    private TextView mCurrentCityTextView;
    
    private ListView mFriendListView;
    private FriendListAdapter mFriendAdapter;

    private ImageFetcher mImageFetcher;

    private ImageButton mFilterButton;
    private ImageButton mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.friend_list);

        mCurrentCityTextView = (TextView) this.findViewById(R.id.friend_list_tv_header_title);
        
        String selection = getString(R.string.friend_list_tile) + L7Application.cacheData.currentCity;
        mCurrentCityTextView.setText(selection);
        
        mFriendListView = (ListView) this
                .findViewById(R.id.friend_list_lv_friends);

        mFilterButton = (ImageButton) this.findViewById(R.id.friend_list_btn_filter);
        mFilterButton.setOnClickListener(this);
        mBackButton = (ImageButton)this.findViewById(R.id.friend_list_btn_back);
        mBackButton.setOnClickListener(this);

        mImageFetcher = ImageFetcher.getInstance(this);
        mImageFetcher.setImageCache(L7Application.getImageCache(this));
        mFriendAdapter = new FriendListAdapter(this, mImageFetcher);
        // mFriendAdapter.setData(users);

        mFriendListView.setOnItemClickListener(this);
        mFriendListView.setAdapter(mFriendAdapter);

        mImageFetcher = ImageFetcher.getInstance(this);
        
        LoadDataTask loadTask = new LoadDataTask(ServerUrls.getServerUrl4User());
        
        QueryArg defaultArg = new QueryArg("city", L7Application.cacheData.currentCity, QueryArg.QUERY_OPTION_EQUAL);
        LOG.debug("defaultArg: " + defaultArg);
        loadTask.execute(defaultArg);

    }
    
    class LoadDataTask extends AsyncTask<QueryArg, Void, List<L7UserInfo>> {

        private String mRequestUrl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewUtils.showLoadingDialog(FriendListActivity.this);
        }
        
        public LoadDataTask(String requestUrl) {
            mRequestUrl = requestUrl;
        }

        @Override
        protected List<L7UserInfo> doInBackground(QueryArg... params) {
            L7UserInfo[] userInfos = null;
            List<L7UserInfo> list = new ArrayList<L7UserInfo>();
            List<NameValuePair> args = new ArrayList<NameValuePair>();
            args.add(new BasicNameValuePair("op", "query"));
            if (params != null && params.length > 0) {
                String queryArgsjson = JsonHelper.toJson(params);
                args.add(new BasicNameValuePair("data", queryArgsjson));
            }
            String responseText = NetworkHelper.requestTextWithHttpPost(
                    mRequestUrl, args);
            try {
                ActionResult result = JsonHelper.fromJson(responseText,
                        ActionResult.class);
                if (result != null && !StringEx.isNullOrEmpty(result.tagJson)) {
//                    userInfos = (L7UserInfo[]) JsonHelper.fromJson(result.tagJson,
//                            L7UserInfo[].class);
                    list = JsonHelper.fromJson(result.tagJson, new TypeToken<List<L7UserInfo>>(){});
                }
                

//                if (userInfos != null) {
//                    for (L7UserInfo l7UserInfo : userInfos) {
//                        list.add(l7UserInfo);
//                    }
//                }
            } catch (Exception e) {
                LOG.error("Failed to parse response to Json Object", e);
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<L7UserInfo> result) {
            ViewUtils.dismissLoadingDialog(FriendListActivity.this);
            if (result != null && result.size() > 0) {
                mFriendAdapter.setData(result);
                mFriendAdapter.notifyDataSetChanged();
            } else {
                ViewUtils.showAlertDialog(FriendListActivity.this, R.string.no_friend_result);
            }
        }

    }

    public void onBack(View v){
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ViewUtils.dismissLoadingDialog(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.friend_list_btn_filter:
            Intent intent = new Intent(this, FilterActivity.class);
            this.startActivityForResult(intent, 1);
            break;
        case R.id.friend_list_btn_back:
            this.finish();
            break;
        default:
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Parcelable[] array = new QueryArg[] {};
        QueryArg[] queryArgArray = null;
        if (intent!=null && resultCode == FilterActivity.RESULT_CODE) {
            array = intent.getParcelableArrayExtra(FilterActivity.INTENT_KEY_QUERY_ARGS);
            if (array != null && array.length > 0) {
                queryArgArray = new QueryArg[array.length];
                for (int i = 0; i < array.length; i++) {
                    queryArgArray[i] = (QueryArg) array[i];
                }
                
                if (queryArgArray.length > 0) {
                    ViewUtils.showLoadingDialog(this, R.string.fetching_data_from_server);

                    LoadDataTask loadTask = new LoadDataTask( ServerUrls.getServerUrl4User());
                    loadTask.execute(queryArgArray);
                    
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        L7UserInfo userInfo = (L7UserInfo) view.getTag();
        if(parent == mFriendListView && userInfo!=null){
            Intent opentActivity = new Intent(this, FriendProfileActivity.class);
            opentActivity.putExtra(Constants.IntentKeys.SELECTED_USER_INFO, userInfo);
            this.startActivity(opentActivity);
        }
        
    }
}
