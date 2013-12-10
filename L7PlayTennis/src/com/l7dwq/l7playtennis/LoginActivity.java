package com.l7dwq.l7playtennis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kankan.logging.Logger;
import com.l7dwq.l7playtennis.bean.SinaUserInfo;
import com.l7dwq.l7playtennis.bean.Uid;
import com.l7dwq.l7playtennis.contract.ActionResult;
import com.l7dwq.l7playtennis.contract.L7UserInfo;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.l7dwq.l7playtennis.util.AccessTokenKeeper;
import com.l7dwq.l7playtennis.util.JsonHelper;
import com.l7dwq.l7playtennis.util.UserUtils;
import com.stanley.core.util.NetworkHelper;
import com.stanley.core.util.StringEx;
import com.stanley.core.util.ViewUtils;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.AccountAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.sso.SsoHandler;

public class LoginActivity extends Activity implements OnClickListener {

	private final static Logger LOG = Logger.getLogger(LoginActivity.class);

	private Weibo mWeibo;
	private static final String CONSUMER_KEY = "2701291887";// 替换为开发者的appkey，例如"1646212860";
	private static final String REDIRECT_URL = "http://www.17dwq.com";
	private ImageButton mLoginWithWeibo;
	private ImageButton mLoginWithQQ;
	private Button mLogin;
	private int mWeiboUID = -1;
	private AccountAPI mAccountApi;
	private UsersAPI mUserApi;

	private static Oauth2AccessToken mAccessToken;
	private SsoHandler mSsoHandler;
	
	private EditText mEtAccountName;
	private EditText mEtPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);

		mLoginWithWeibo = (ImageButton) this
				.findViewById(R.id.login_btn_login_sina_weibo);
		mLoginWithWeibo.setOnClickListener(this);
		mLoginWithQQ = (ImageButton) this.findViewById(R.id.login_btn_login_qq);
		mLoginWithQQ.setOnClickListener(this);

		mLogin = (Button) this.findViewById(R.id.login_btn_login);
		mLogin.setOnClickListener(this);
		
		mEtAccountName = (EditText) this.findViewById(R.id.login_et_account);
		mEtPassword = (EditText) this.findViewById(R.id.login_et_password);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn_login_sina_weibo:
			mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
			// mWeibo.authorize(this, new AuthDialogListener());
			mSsoHandler = new SsoHandler(this, mWeibo);
			mSsoHandler.authorize(new AuthDialogListener());
			break;
		case R.id.login_btn_login_qq:
			break;
		case R.id.login_btn_login:
			LoginTask loginTask = new LoginTask();
			loginTask.execute();
			break;
		default:
			break;
		}

	}

	class AuthDialogListener implements WeiboAuthListener {

		private static final String TAG = "LoginActivity";

		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			mAccessToken = new Oauth2AccessToken(token, expires_in);
			if (mAccessToken.isSessionValid()) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date(mAccessToken
								.getExpiresTime()));
				Toast.makeText(
						LoginActivity.this,
						"认证成功: \r\n access_token: " + token + "\r\n"
								+ "expires_in: " + expires_in + "\r\n有效期："
								+ date, Toast.LENGTH_LONG).show();

				// cancelBtn.setVisibility(View.VISIBLE);
				AccessTokenKeeper.keepAccessToken(LoginActivity.this,
						mAccessToken);
				Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_SHORT)
						.show();
				StatusesAPI statusApi = new StatusesAPI(mAccessToken);
				mAccountApi = new AccountAPI(mAccessToken);
				mAccountApi.getUid(new RequestListener() {

					public void onIOException(IOException arg0) {
						// TODO Auto-generated method stub

					}

					public void onError(WeiboException arg0) {
						// TODO Auto-generated method stub

					}

					public void onComplete(String str) {
						Log.d(TAG, "getUid: " + str);
						Gson gson = new Gson();
						Uid u = gson.fromJson(str, Uid.class);
						final long uid = u.uid;
						if (mUserApi != null) {
							mUserApi.show(uid, new RequestListener() {

								@Override
								public void onIOException(IOException arg0) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(WeiboException arg0) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onComplete(String value) {
									Log.d(TAG, "mUserApi.show: " + value);
									SinaUserInfo userInfo = new Gson()
											.fromJson(value, SinaUserInfo.class);
									L7Application.cacheData.currentUserInfo = UserUtils
											.fromSinaUser(userInfo);

									startMainActivity();

									Log.d(TAG, "screen name: "
											+ userInfo.screen_name);
								}
							});
						}
					}
				});
				mUserApi = new UsersAPI(mAccessToken);
			}
		}

		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mSsoHandler.authorizeCallBack(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
		    
		}
	}

	private void startMainActivity() {
		Intent startMain = new Intent(
				LoginActivity.this,
				MainActivity.class);
		LoginActivity.this.startActivity(startMain);
		this.finish();		
	}

	class LoginTask extends AsyncTask<Void, Void, ActionResult> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			ViewUtils.showLoadingDialog(LoginActivity.this);
		}
		
		@Override
		protected ActionResult doInBackground(Void... params) {
			ActionResult result = null;
			List<NameValuePair> args = new ArrayList<NameValuePair>();
			args.add(new BasicNameValuePair("op", "login"));
			String account = mEtAccountName.getText().toString();
			String password = mEtPassword.getText().toString();
			String passwordMd5 = StringEx.toMD5(password);
			args.add(new BasicNameValuePair("account", account));
			args.add(new BasicNameValuePair("password", passwordMd5));
			
			String responseText = NetworkHelper.requestTextWithHttpPost(
					ServerUrls.getServerUrl4User(), args);
			try {
				result = JsonHelper.fromJson(responseText, ActionResult.class);
				if (result != null && !StringEx.isNullOrEmpty(result.tagJson)) {
					L7Application.cacheData.currentUserInfo = (L7UserInfo) JsonHelper
							.fromJson(result.tagJson, L7UserInfo.class);
				}
			} catch (Exception e) {
				LOG.error("Failed to parse response to Json Object", e);
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(ActionResult result) {
			ViewUtils.dismissLoadingDialog(LoginActivity.this);
			if (result == null) {
				ViewUtils.showToast(LoginActivity.this, R.string.no_response);
			} else {
				if (L7Application.cacheData.currentUserInfo != null) {
					startMainActivity();
				} else {
					switch (result.resultCode) {
					case ActionResult.RESULT_CODE_LOGIN_USER_NOT_EXISTS:
						ViewUtils.showAlertDialog(LoginActivity.this, R.string.user_not_exits);
						break;
					case ActionResult.RESULT_CODE_LOGIN_PASSWORD_INCORRECT:
						ViewUtils.showAlertDialog(LoginActivity.this, R.string.password_incorrect);
						break;
					default:
						break;
					}
				}
			}
			super.onPostExecute(result);
		}

	}
}
