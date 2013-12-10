package com.l7dwq.l7playtennis.contract;

import com.stanley.core.util.StringEx;

import android.os.Parcel;
import android.os.Parcelable;

public class QueryArg implements Parcelable{
    
    public final static int QUERY_OPTION_EQUAL = 1;
	public final static int QUERY_OPTION_LIKE = 2;
	public final static int QUERY_OPTION_GT = 3;
	public final static int QUERY_OPTION_LT = 4;
    public final static int QUERY_OPTION_BT = 5; // between 1.0~6.0
	
	public QueryArg(String name, String value, int option){
		this.name = name;
		this.value = value;
		this.option = option;
	}
	
	public String name;
	public String value;
	
	public int option;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(name);
        out.writeString(value);
        out.writeInt(option);
    }
    
    private QueryArg(Parcel in){
        //需要与WriteToParcel的顺序一样！
        name = in.readString();
        value = in.readString();
        option = in.readInt();
    }
    
    @Override
    public String toString() {
        String valueText = StringEx.Empty;
        switch (option) {
        case QUERY_OPTION_EQUAL:
            valueText = value;
            break;
        case QUERY_OPTION_BT:
            valueText = "在" + value + "之间";
            break;
        case QUERY_OPTION_GT:
            valueText = "大于 " + value;
            break;
        case QUERY_OPTION_LIKE:
            valueText = "包含\"" + value + "\"";
            break;
        case QUERY_OPTION_LT:
            valueText = "小于 " + value;
            break;
        default:
            break;
        }
        return name + ": " + valueText;
    }
    
    public static final Parcelable.Creator<QueryArg> CREATOR = new Parcelable.Creator<QueryArg>(){
        @Override
        public QueryArg createFromParcel(Parcel source) {
            return new QueryArg(source);
        }
        @Override
        public QueryArg[] newArray(int size) {
            return new QueryArg[size];
        }
    };
}
