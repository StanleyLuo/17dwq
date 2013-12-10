package com.l7dwq.l7playtennis;

import android.app.Activity;
import android.view.View;

import com.stanley.core.caching.ImageFetcher;

public class L7BaseActivity extends Activity {

    private ImageFetcher mImageFetcher;

    public ImageFetcher getImageFetcher() {
        if (mImageFetcher == null) {
            mImageFetcher = ImageFetcher.getInstance(this);
            mImageFetcher.setImageCache(L7Application.getImageCache(this));
        }
        return mImageFetcher;
    }

    public void onBack(View v){
        this.finish();
    }
}
