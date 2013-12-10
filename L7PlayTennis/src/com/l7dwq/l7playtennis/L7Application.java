package com.l7dwq.l7playtennis;

import android.app.Activity;
import android.app.Application;

import com.kankan.logging.Logger;
import com.l7dwq.l7playtennis.misc.AppCacheData;
import com.l7dwq.l7playtennis.misc.Constants;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.stanley.core.caching.ImageCache;

public class L7Application extends Application {

    public static String serverUrl = ServerUrls.SERVER_URL;
    
    public static AppCacheData cacheData = new AppCacheData();
    
	public static Logger LOG = Logger.getLogger(L7Application.class);
	
	private ImageCache mImageCache;

	@Override
	public void onCreate() {
	    cacheData.currentCity = "深圳市"; //by default
		super.onCreate();
	}

	public ImageCache getImageCache() {
		if (mImageCache == null) {
			mImageCache = ImageCache.getPerfectCache(
					this.getApplicationContext(),
					Constants.Cache.IMAGE_CACHE_NAME,
					Constants.Cache.IMAGE_CACHE_SIZE_FACTOR);
		}
		return mImageCache;
	}

	public static ImageCache getImageCache(Activity act) {
		ImageCache imageCache = null;
		if (act.getApplication() instanceof L7Application) {
			imageCache = ((L7Application) act.getApplication()).getImageCache();
		}
		return imageCache;
	}

	@Override
	public void onLowMemory() {
		LOG.warn("The overall memory is low, will clear the cache...");
		if (this.getImageCache() != null) {
			this.getImageCache().clearMemeoryCache();
		}
		super.onLowMemory();
	}

}
