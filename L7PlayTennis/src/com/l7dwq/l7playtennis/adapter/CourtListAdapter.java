package com.l7dwq.l7playtennis.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.l7dwq.l7playtennis.R;
import com.l7dwq.l7playtennis.contract.L7TennisCourt;
import com.stanley.core.caching.ImageFetcher;

public class CourtListAdapter extends BaseAdapter {

    private List<L7TennisCourt> mData;

    private ImageFetcher mImageFetcher;

    public CourtListAdapter(ImageFetcher fetcher) {
        mImageFetcher = fetcher;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setData(List<L7TennisCourt> courtList) {
        mData = courtList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;
        if (convertView == null) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.court_list_item, null);
        } else {
            itemView = convertView;
        }

        L7TennisCourt courtInfo = mData.get(position);

        TextView title = (TextView) itemView.findViewById(R.id.court_tv_title);
        TextView address = (TextView) itemView.findViewById(R.id.court_tv_adderss);
        ImageView photo = (ImageView) itemView.findViewById(R.id.court_iv_photo);

        ImageView serviceShopping = (ImageView) itemView.findViewById(R.id.court_iv_service_shopping);
        ImageView serviceWC = (ImageView) itemView.findViewById(R.id.court_iv_service_wc);
        ImageView serviceParking = (ImageView) itemView.findViewById(R.id.court_iv_service_parking);
        ImageView serviceCoach = (ImageView) itemView.findViewById(R.id.court_iv_service_coach);

        if (L7TennisCourt.COURT_SERVICE_SHOPPING == 
                (courtInfo.service & L7TennisCourt.COURT_SERVICE_SHOPPING)) {
            serviceShopping.setVisibility(View.VISIBLE);
        } else {
            serviceShopping.setVisibility(View.GONE);
        }

        if (L7TennisCourt.COURT_SERVICE_PARKING== 
                (courtInfo.service & L7TennisCourt.COURT_SERVICE_PARKING)) {
            serviceParking.setVisibility(View.VISIBLE);
        } else {
            serviceParking.setVisibility(View.GONE);
        }

        if (L7TennisCourt.COURT_SERVICE_TEACH== 
                (courtInfo.service & L7TennisCourt.COURT_SERVICE_TEACH)) {
            serviceWC.setVisibility(View.VISIBLE);
        } else {
            serviceWC.setVisibility(View.GONE);
        }

        if (L7TennisCourt.COURT_SERVICE_WC== 
                (courtInfo.service & L7TennisCourt.COURT_SERVICE_WC)) {
            serviceCoach.setVisibility(View.VISIBLE);
        } else {
            serviceCoach.setVisibility(View.GONE);
        }         

        title.setText(courtInfo.name);
        address.setText(courtInfo.address);
        if (mImageFetcher != null) {
            mImageFetcher.loadImage(courtInfo.picture, photo);
        }

        return itemView;
    }

}
