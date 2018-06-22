package com.ibm.utils;

/**
 * Response bean class to hold http resonse
 * 
 * @author SUDDUTT1
 * 
 */
public class HTTPResponse {

	public static final int CLIENT_SIDE_ERROR = -1;
	private int responseCode;
	private String result;
	private boolean ok;
	private boolean serverError;
	private boolean clientError;
	private boolean unknown;
	

	/**
	 * Constructor 
	 * @param code
	 * @param rslt
	 */
	public HTTPResponse(int code, String rslt) {
		this.responseCode = code;
		this.result = rslt;
		if(this.responseCode>=200 && this.responseCode<300) {
			this.ok = true;
		}
		else if(this.responseCode>=400 && this.responseCode<600) {
			this.serverError = true;
		}
		else if (this.responseCode== CLIENT_SIDE_ERROR){
			clientError = true;
		}
		else
		{
			unknown = true;
		}
	}

	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return the serverError
	 */
	public boolean isServerError() {
		return serverError;
	}

	/**
	 * @param serverError the serverError to set
	 */
	public void setServerError(boolean serverError) {
		this.serverError = serverError;
	}

	/**
	 * @return the clientError
	 */
	public boolean isClientError() {
		return clientError;
	}

	/**
	 * @param clientError the clientError to set
	 */
	public void setClientError(boolean clientError) {
		this.clientError = clientError;
	}

	/**
	 * @return the unknown
	 */
	public boolean isUnknown() {
		return unknown;
	}

	/**
	 * @param unknown the unknown to set
	 */
	public void setUnknown(boolean unknown) {
		this.unknown = unknown;
	}

}
