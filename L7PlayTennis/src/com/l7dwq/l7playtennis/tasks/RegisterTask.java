package com.l7dwq.l7playtennis.tasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.kankan.logging.Logger;
import com.l7dwq.l7playtennis.L7Application;
import com.l7dwq.l7playtennis.contract.ActionResult;
import com.l7dwq.l7playtennis.contract.L7UserInfo;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.l7dwq.l7playtennis.util.JsonHelper;
import com.stanley.core.util.NetworkHelper;
import com.stanley.core.util.StringEx;

public class RegisterTask extends AsyncTask<L7UserInfo, Void, L7UserInfo> {

	private final static Logger LOG = Logger.getLogger(RegisterTask.class);

	private ActionResult mResult;

	public ActionResult getRegisterResult() {
		return mResult;
	}

	@Override
	protected L7UserInfo doInBackground(L7UserInfo... params) {
		List<NameValuePair> args = new ArrayList<NameValuePair>();
		args.add(new BasicNameValuePair("op", "reg"));
		if (params != null && params.length > 0) {
			String jsonData = JsonHelper.toJson(params[0]);
			args.add(new BasicNameValuePair("data", jsonData));
			String responseText = NetworkHelper.requestTextWithHttpPost(
					ServerUrls.getServerUrl4User(), args);
			try {
				mResult = JsonHelper.fromJson(responseText, ActionResult.class);
				if (mResult != null && !StringEx.isNullOrEmpty(mResult.tagJson)) {
					L7Application.cacheData.currentUserInfo = (L7UserInfo) JsonHelper
							.fromJson(mResult.tagJson, L7UserInfo.class);
				}
			} catch (Exception e) {
				LOG.error("Failed to parse response to Json Object", e);
				e.printStackTrace();
			}
		}
		return L7Application.cacheData.currentUserInfo;
	}

}
