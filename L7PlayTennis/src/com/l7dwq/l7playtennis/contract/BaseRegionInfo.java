package com.l7dwq.l7playtennis.contract;

import java.util.List;

public abstract class BaseRegionInfo {

	public int id;
	public int code;
	public String name;
	
	public abstract <T> List<? extends BaseRegionInfo> getSubordinates();
}
