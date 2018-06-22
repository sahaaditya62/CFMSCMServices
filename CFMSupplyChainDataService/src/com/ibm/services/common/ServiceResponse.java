package com.ibm.services.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceResponse {

	public static final SimpleDateFormat  _DT_FMT = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS.zzz");
	private int status;
	private String message;
	private Object payload;
	private String timeStamp;
	
	/**
	 * Constructor
	 * @param status
	 * @param message
	 * @param payload
	 */
	public ServiceResponse(int status, String message, Object payload) {
		super();
		this.status = status;
		this.message = message;
		this.payload = payload;
		this.timeStamp = _DT_FMT.format(new Date());
	}
	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the payload
	 */
	public Object getPayload() {
		return payload;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Object payload) {
		this.payload = payload;
	}
	
}
