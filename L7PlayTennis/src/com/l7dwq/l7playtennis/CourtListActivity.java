package com.l7dwq.l7playtennis;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kankan.logging.Logger;
import com.l7dwq.l7playtennis.adapter.DataPointViewMapping;
import com.l7dwq.l7playtennis.adapter.L7QueryableListAdapter;
import com.l7dwq.l7playtennis.contract.L7TennisCourt;
import com.l7dwq.l7playtennis.misc.Constants;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.stanley.core.caching.ImageFetcher;

public class CourtListActivity extends L7BaseListActivty<L7TennisCourt> {

    private final static Logger LOG = Logger.getLogger(CourtListActivity.class);
    private DataPointViewMapping[] mViewDataMapping = new DataPointViewMapping[] {
            new DataPointViewMapping(R.id.court_iv_photo, DataPointViewMapping.VIEW_TYPE_IMAGE_VIEW, "picture"),
            new DataPointViewMapping(R.id.court_tv_adderss, DataPointViewMapping.VIEW_TYPE_TEXT_VIEW, "address"),
            new DataPointViewMapping(R.id.court_tv_title, DataPointViewMapping.VIEW_TYPE_TEXT_VIEW, "name"),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.court_list);
        LOG.debug("onCreate()");

    }

    @Override
    public int getListViewId() {
        return R.id.court_lv_list;
    }

    @Override
    public String getLoadDataUrl() {
        return ServerUrls.getServerUrl4Court();
    }

    @Override
    public L7QueryableListAdapter<L7TennisCourt> getListViewAdapter() {
        return new CourtListAdapter(getImageFetcher());
    }

    class CourtListAdapter extends L7QueryableListAdapter<L7TennisCourt> {

        public CourtListAdapter(ImageFetcher fetcher) {
            super(fetcher);
        }

        @Override
        public void onDataPointReady(View dataPointView, L7TennisCourt entry) {
            switch (dataPointView.getId()) {
            case R.id.court_iv_photo:
                ImageView iv = (ImageView) dataPointView;
                getImageFetcher().loadImage(entry.picture, iv);
                break;
            case R.id.court_tv_title:
                TextView tv = (TextView) dataPointView;
                tv.setText(entry.name);
                break;
            case R.id.court_tv_adderss:
                TextView address = (TextView) dataPointView;
                address.setText(entry.address);
                break;
            }

        }

        @Override
        public DataPointViewMapping[] getDataPointViewIdMapping() {
            return mViewDataMapping;
        }

        @Override
        public int getItemViewLayoutResId() {
            return R.layout.court_list_item;
        }

    }

    @Override
    public TypeToken<List<L7TennisCourt>> getTypeTokenType4Gson() {
        return new TypeToken<List<L7TennisCourt>>() {};
    }

    @Override
    public void onListViewItemClick(View currentItemView, int position, L7TennisCourt itemData) {

        Intent openDetail =new Intent(this, CourtDetailActivity.class);
        openDetail.putExtra(Constants.IntentKeys.COURT_INFO, itemData);
        this.startActivity(openDetail);
    }

}
