package com.stanley.support.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;

import com.l7dwq.l7playtennis.R;

public class AppUpgradeManager {

    private static final String TAG = "AppUpgradeManager";

    private static final int PROGRESS_MAX = 100;
    private static final int NOTIFICATION_ID = 11;
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    public void startNewVersionCheck(Context ctx, String versionCheckUrl) {
        CheckTask task = new CheckTask(ctx);
        task.execute(versionCheckUrl);
    }

    private void showConfirmDialog(final Context ctx, final VersionControlInfo vc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        String title = ctx.getResources().getString(R.string.upgrade) + ":" + vc.version;
        AlertDialog dialog = builder.setTitle(title).setNegativeButton(R.string.ignore, new OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton(R.string.ok, new OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                prepareNotification(ctx);
                DownloadTask downloadTask = new DownloadTask(ctx);
                downloadTask.execute(vc);
                dialog.dismiss();
            }
        }).setCancelable(true).create();

        dialog.show();
    }

    private VersionControlInfo getLatestAppVersionInfo(String versionCheckUrl, String $package) {
        VersionControlInfo latestApkInfo = null;
        HttpClient client = new DefaultHttpClient();
        HttpParams httpParams = client.getParams();
        // �������糬ʱ����
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        HttpResponse response;
        try {
            String fullUrl = versionCheckUrl + "?package=" + $package;
            response = client.execute(new HttpGet(fullUrl));
            HttpEntity entity = response.getEntity();
            InputStream in = entity.getContent();
            if (entity != null && in != null) {
                BeanUtil<VersionControlInfo> beanUtil = new BeanUtil<VersionControlInfo>(VersionControlInfo.class);
                InputStreamReader reader = new InputStreamReader(in);
                latestApkInfo = beanUtil.fromJson(reader);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return latestApkInfo;
    }

    private void prepareNotification(Context ctx) {
        mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        String title = ctx.getString(R.string.downloading_new_version);
        mNotification = new Notification(R.drawable.ic_launcher, title, System.currentTimeMillis());
        RemoteViews rvs = new RemoteViews(ctx.getPackageName(), R.layout.upgrade_progress);
        mNotification.contentView = rvs;
        rvs.setProgressBar(R.id.upgrade_pb_progress, PROGRESS_MAX, 0, false);
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    private void showNotification(Context ctx, int progress) {
        mNotification.contentView.setProgressBar(R.id.upgrade_pb_progress, PROGRESS_MAX, progress, false);
        String downloading = ctx.getString(R.string.downloading);
        mNotification.contentView.setTextViewText(R.id.upgrade_tv_progress, downloading + progress + "%");
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    private void showReadyInstallNotification(Context ctx, String localApkFilePath) {
        String downloaded = ctx.getString(R.string.download_finished);
        mNotification.contentView.setTextViewText(R.id.upgrade_tv_progress, downloaded);
        Intent installIntent = new Intent();
        installIntent.setAction(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.parse(localApkFilePath), "application/vnd.android.package-archive");
        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, installIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFICATION_ID, mNotification);
    }

    class DownloadTask extends AsyncTask<VersionControlInfo, Integer, Boolean> {

        private Context mContext;
        private String downloadedApkFilePath;

        public DownloadTask(Context ctx) {
            mContext = ctx;
        }

        @Override
        protected Boolean doInBackground(VersionControlInfo... params) {
            boolean downloadSucceed = false;
            if (params != null && params.length > 0) {
                VersionControlInfo v = params[0];
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(v.downloadUrl);
                HttpResponse response = null;
                FileOutputStream fileOutputStream = null;
                InputStream inputStream = null;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    Log.d(TAG, "apkLength=" + entity.getContentLength());
                    inputStream = entity.getContent();
                    if (inputStream != null) {
                        Uri uri = Uri.parse(v.downloadUrl);

                        String apkFileName = uri.getLastPathSegment();
                        File dir = new File(Environment.getExternalStorageDirectory() + "/Download/");
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        File saveAsApkFile = new File(dir.getAbsolutePath(), apkFileName);
                        fileOutputStream = new FileOutputStream(saveAsApkFile);
                        byte[] buf = new byte[1024];
                        int ch = -1;
                        long downloadedSize = 0;
                        long total = v.apkSize;
                        int lastProgress = 0;
                        while ((ch = inputStream.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            downloadedSize += ch;
                            int progress = (int) ((double) downloadedSize * 100 / (double) total);
                            if (progress > lastProgress) {
                                this.publishProgress(progress);
                                lastProgress = progress;
                            }
                            Log.d(TAG, "downloadedSize=" + downloadedSize);
                        }

                        fileOutputStream.flush();

                        if (saveAsApkFile.exists()) {
                            downloadedApkFilePath = saveAsApkFile.getAbsolutePath();
                            downloadSucceed = true;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Failed to save apk file.", e);
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fileOutputStream = null;
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        inputStream = null;
                    }
                }
            }
            return downloadSucceed;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values != null && values.length > 0) {
                showNotification(mContext, values[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean downloadSucceed) {
            if (downloadSucceed) {
                showReadyInstallNotification(mContext, downloadedApkFilePath);
            }
        }

    }

    class CheckTask extends AsyncTask<String, Void, Void> {
        private Context mContext;
        private VersionControlInfo mLatestInfo;
        private int currentVersionCode;

        public CheckTask(Context ctx) {
            mContext = ctx;
        }

        @Override
        protected Void doInBackground(String... params) {
            String versionCheckUrl = params[0];
            try {
                // String currentVersionName = packageInfo.versionName;
                mLatestInfo = getLatestAppVersionInfo(versionCheckUrl, mContext.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            PackageInfo packageInfo = null;
            try {
                packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            if (mLatestInfo != null && packageInfo != null) {
                currentVersionCode = packageInfo.versionCode;
                if (mLatestInfo.versionCode > currentVersionCode
                        && mLatestInfo.upgradeFlag >= VersionControlInfo.FLAG_RECOMMEND_UPDATE) {
                    showConfirmDialog(mContext, mLatestInfo);
                }
            }
        }
    }
}
