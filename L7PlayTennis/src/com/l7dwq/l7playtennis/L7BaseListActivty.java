package com.l7dwq.l7playtennis;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.kankan.logging.Logger;
import com.l7dwq.l7playtennis.adapter.L7QueryableListAdapter;
import com.l7dwq.l7playtennis.contract.ActionResult;
import com.l7dwq.l7playtennis.contract.L7UserInfo;
import com.l7dwq.l7playtennis.contract.QueryArg;
import com.l7dwq.l7playtennis.util.JsonHelper;
import com.stanley.core.caching.ImageFetcher;
import com.stanley.core.util.NetworkHelper;
import com.stanley.core.util.StringEx;
import com.stanley.core.util.ViewUtils;

public abstract class L7BaseListActivty<T> extends Activity implements OnItemClickListener {

    private final static Logger LOG = Logger.getLogger(L7BaseListActivty.class);

    protected ListView mListView;
    protected L7QueryableListAdapter<T> mDataAdapter;
    private ImageFetcher mImageFetcher;

    private LoadDataTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataAdapter = this.getListViewAdapter();
        mImageFetcher = ImageFetcher.getInstance(this);
    }

    public ImageFetcher getImageFetcher() {
        return mImageFetcher;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTask = new LoadDataTask();
        QueryArg defaultArg = new QueryArg("city", L7Application.cacheData.currentCity, QueryArg.QUERY_OPTION_EQUAL);
        LOG.debug("defaultArg: " + defaultArg);
        mTask.execute(defaultArg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.initCoreViews();
    }

    private void initCoreViews() {
        mListView = (ListView) this.findViewById(this.getListViewId());
        mListView.setAdapter(mDataAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        this.initCoreViews();
    }

    public abstract int getListViewId();

    public abstract String getLoadDataUrl();

    public abstract TypeToken<List<T>> getTypeTokenType4Gson();

    class LoadDataTask extends AsyncTask<QueryArg, Void, List<T>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewUtils.showLoadingDialog(L7BaseListActivty.this);
        }

        @Override
        protected List<T> doInBackground(QueryArg... params) {
            List<T> list = new ArrayList<T>();
            List<NameValuePair> args = new ArrayList<NameValuePair>();
            args.add(new BasicNameValuePair("op", "query"));
            if (params != null && params.length > 0) {
                String queryArgsjson = JsonHelper.toJson(params);
                args.add(new BasicNameValuePair("data", queryArgsjson));
            }
            String responseText = NetworkHelper.requestTextWithHttpPost(
                    getLoadDataUrl(), args);
            try {
                ActionResult result = JsonHelper.fromJson(responseText,
                        ActionResult.class);
                if (result != null && !StringEx.isNullOrEmpty(result.tagJson)) {
                    list = JsonHelper.fromJson(result.tagJson, getTypeTokenType4Gson());
                    // courtInfos = (L7TennisCourt[]) JsonHelper.fromJson(result.tagJson,
                    // L7TennisCourt[].class);
                }

                // if (courtInfos != null) {
                // for (L7TennisCourt l7UserInfo : courtInfos) {
                // list.add(l7UserInfo);
                // }
                // }
            } catch (Exception e) {
                LOG.error("Failed to parse response to Json Object", e);
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<T> result) {
            ViewUtils.dismissLoadingDialog(L7BaseListActivty.this);
            if (result != null && result.size() > 0) {
                mDataAdapter.setData(result);
                mDataAdapter.notifyDataSetChanged();
            } else {
                ViewUtils.showAlertDialog(L7BaseListActivty.this, R.string.no_friend_result);
            }
        }

    }

    public abstract L7QueryableListAdapter<T> getListViewAdapter();

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mDataAdapter != null && mDataAdapter.getItem(position) != null) {
            T itemData = (T) mDataAdapter.getItem(position);
            if (itemData != null) {
                this.onListViewItemClick(view, position, itemData);
            }
        }
    }

    public abstract void onListViewItemClick(View currentItemView, int position, T itemData);

    public void onBack(View v) {
        this.finish();
    }
}
