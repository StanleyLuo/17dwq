package com.l7dwq.l7playtennis.adapter;

public class DataPointViewMapping {

    public DataPointViewMapping(int viewId, int viewType, String dataPointName){
        this.dataPointViewId = viewId;
        this.dataPointName = dataPointName;
        this.viewType = viewType;
    }
    
    public final static int VIEW_TYPE_TEXT_VIEW = 1;
    
    public final static int VIEW_TYPE_IMAGE_VIEW = 2;
    
    public int dataPointViewId;
    
    public int viewType;
    
    /**
     * 数据点的名字，即使实体类的属性名
     */
    public String dataPointName;
}
