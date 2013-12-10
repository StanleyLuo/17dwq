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
		<!-- 0：不升级 无提示 -->
		<!-- 1：可不升级 但有提示 -->
		<!-- 2：推荐升级	-->
		<!-- 3：强制升级 -->	 
	 */
	public int upgradeFlag;
}
