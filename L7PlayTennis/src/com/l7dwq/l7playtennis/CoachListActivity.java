package com.l7dwq.l7playtennis;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.l7dwq.l7playtennis.adapter.DataPointViewMapping;
import com.l7dwq.l7playtennis.adapter.L7QueryableListAdapter;
import com.l7dwq.l7playtennis.contract.L7CoachInfo;
import com.l7dwq.l7playtennis.misc.Constants;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.stanley.core.caching.ImageFetcher;

public class CoachListActivity extends L7BaseListActivty<L7CoachInfo> {

    private DataPointViewMapping[] mViewDataMapping = new DataPointViewMapping[] {
            new DataPointViewMapping(R.id.coach_iv_avatar, DataPointViewMapping.VIEW_TYPE_IMAGE_VIEW, "photo"),
            new DataPointViewMapping(R.id.coach_tv_adderss, DataPointViewMapping.VIEW_TYPE_TEXT_VIEW, "address"),
            new DataPointViewMapping(R.id.coach_tv_name, DataPointViewMapping.VIEW_TYPE_TEXT_VIEW, "name"),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.coach_list);
    }

    @Override
    public int getListViewId() {
        return R.id.coach_lv_list;
    }

    @Override
    public String getLoadDataUrl() {
        return ServerUrls.getServerUrl4Coach();
    }

    @Override
    public L7QueryableListAdapter<L7CoachInfo> getListViewAdapter() {
        return new CoachListAdapter(getImageFetcher());
    }

    class CoachListAdapter extends L7QueryableListAdapter<L7CoachInfo> {

        public CoachListAdapter(ImageFetcher fetcher) {
            super(fetcher);
        }

        @Override
        public void onDataPointReady(View dataPointView, L7CoachInfo entry) {
            switch (dataPointView.getId()) {
            case R.id.coach_iv_avatar:
                ImageView avatar = (ImageView) dataPointView;
                getImageFetcher().loadImage(entry.photo, avatar);
                break;
            case R.id.coach_iv_gender:
                ImageView genderImageView = (ImageView) dataPointView;
                if (entry.sex == 1) {
                    genderImageView.setImageResource(R.drawable.symbol_male);
                } else {
                    genderImageView.setImageResource(R.drawable.symbol_female);
                }

                break;
            case R.id.coach_tv_name:
                TextView nameTextView = (TextView) dataPointView;
                nameTextView.setText(entry.name);
                break;
            case R.id.coach_tv_adderss:
                TextView addrTextView = (TextView) dataPointView;
                addrTextView.setText(entry.province + "-" + entry.city + "-" + entry.district);
                break;
            case R.id.coach_tv_age:
                TextView ageTextView = (TextView) dataPointView;
                ageTextView.setText(getString(R.string.age_num, entry.age));
                break;
            case R.id.coach_tv_distance:
                TextView distanceTextView = (TextView) dataPointView;
                distanceTextView.setText(getString(R.string.distance_num, entry.age));
                break;

            default:
                break;
            }
        }

        @Override
        public DataPointViewMapping[] getDataPointViewIdMapping() {
            return mViewDataMapping;
        }

        @Override
        public int getItemViewLayoutResId() {
            return R.layout.coach_list_item;
        }

    }

    @Override
    public TypeToken<List<L7CoachInfo>> getTypeTokenType4Gson() {

        return new TypeToken<List<L7CoachInfo>>() {
        };
    }

    
    @Override
    public void onListViewItemClick(View currentItemView, int position, L7CoachInfo itemData) {

        Intent openDetail =new Intent(this, CoachDetailActivity.class);
        openDetail.putExtra(Constants.IntentKeys.COACH_INFO, itemData);
        this.startActivity(openDetail);
    }
}
