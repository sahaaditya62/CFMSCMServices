package com.ibm.hlfabric.client;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HLFabricRequest {
	private static final Gson _REQUEST_SERIALIZER = new GsonBuilder().setPrettyPrinting().create();
	private String jsonrpc = "2.0";
	private String method;
	private String secureContext;
	private String chainCodeName;
	private String chainCodePath;
	private String callFunction;
	private String[] functionArgs;
	private String id="2";
	public HLFabricRequest()
	{
		super();
	}
	/**
	 * @return the jsonrpc
	 */
	public String getJsonrpc() {
		return jsonrpc;
	}
	/**
	 * @param jsonrpc the jsonrpc to set
	 */
	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the secureContext
	 */
	public String getSecureContext() {
		return secureContext;
	}
	/**
	 * @param secureContext the secureContext to set
	 */
	public void setSecureContext(String secureContext) {
		this.secureContext = secureContext;
	}
	/**
	 * @return the chainCodeName
	 */
	public String getChainCodeName() {
		return chainCodeName;
	}
	/**
	 * @param chainCodeName the chainCodeName to set
	 */
	public void setChainCodeName(String chainCodeName) {
		this.chainCodeName = chainCodeName;
	}
	/**
	 * @return the chainCodePath
	 */
	public String getChainCodePath() {
		return chainCodePath;
	}
	/**
	 * @param chainCodePath the chainCodePath to set
	 */
	public void setChainCodePath(String chainCodePath) {
		this.chainCodePath = chainCodePath;
	}
	/**
	 * @return the callFunction
	 */
	public String getCallFunction() {
		return callFunction;
	}
	/**
	 * @param callFunction the callFunction to set
	 */
	public void setCallFunction(String callFunction) {
		this.callFunction = callFunction;
	}
	/**
	 * @return the functionArgs
	 */
	public String[] getFunctionArgs() {
		return functionArgs;
	}
	/**
	 * @param functionArgs the functionArgs to set
	 */
	public void setFunctionArgs(String[] functionArgs) {
		this.functionArgs = functionArgs;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Build the request json 
	 * @return
	 */
	public String buildRequest()
	{
		String postBody = null;
		Map<String,Object> requestObject = new LinkedHashMap<>();
		requestObject.put("jsonrpc", this.jsonrpc);
		requestObject.put("method", this.method);
		Map<String,Object> params = new LinkedHashMap<>();
		params.put("type", 1);
		Map<String,String> chainCode = new LinkedHashMap<>();
		if(this.chainCodePath==null)
		{
			chainCode.put("name", this.chainCodeName);
		}
		else
		{
			chainCode.put("path", this.chainCodePath);
		}
		
		params.put("chaincodeID", chainCode);
		Map<String,Object> ctorMsg = new LinkedHashMap<>();
		ctorMsg.put("function",this.callFunction);
		ctorMsg.put("args",this.functionArgs);
		params.put("ctorMsg", ctorMsg);
		params.put("secureContext", this.secureContext);
		requestObject.put("params", params);
		requestObject.put("id", 0);
		postBody = _REQUEST_SERIALIZER.toJson(requestObject);
		return postBody;
	}
}
