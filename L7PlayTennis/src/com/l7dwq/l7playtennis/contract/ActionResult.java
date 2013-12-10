package com.l7dwq.l7playtennis.contract;

public class ActionResult {

	public final static int RESULT_CODE_SUCCESS = 0;
	public final static int RESULT_CODE_DULPICATED_USER = 1;
	public final static int RESULT_CODE_DATABASE_ERROR = 2;
	public final static int RESULT_CODE_UNKNOW_ERROR = 3;
	public final static int RESULT_CODE_LOGIN_USER_NOT_EXISTS = 4;
	public final static int RESULT_CODE_LOGIN_PASSWORD_INCORRECT = 5;
	
	public ActionResult(){
	}
	
	public ActionResult(int code){
		resultCode = code;
	}
	public ActionResult(int code, String msg){
		resultCode = code;
		message = msg;
	}	
	public int resultCode;
	public String message;
	
	public String tagJson;
}
