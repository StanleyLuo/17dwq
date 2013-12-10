package com.stanley.support.update;

public class VersionControlInfo {

	public final static int FLAG_NO_UPDATE = 0;
	public final static int FLAG_PROMPT = 1;
	public final static int FLAG_RECOMMEND_UPDATE = 2;
	public final static int FLAG_MANDATORY_UPDATE = 3;
	
	public String appName;
	public long apkSize;
	public String downloadUrl;
	public int versionCode;
	public String version;
	public String apkPackage;
	
	/**
		<!-- 0�������� ����ʾ -->
		<!-- 1���ɲ����� ������ʾ -->
		<!-- 2���Ƽ�����	-->
		<!-- 3��ǿ������ -->	 
	 */
	public int upgradeFlag;
}
