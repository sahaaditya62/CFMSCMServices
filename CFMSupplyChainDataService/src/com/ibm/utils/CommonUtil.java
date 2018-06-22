package com.ibm.utils;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class CommonUtil {

	private static final Gson _SERIALIZER = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	private static final Gson _SERIALIZER_NO_PP = new GsonBuilder().serializeNulls().create();
	
	private static final Gson _DERIALIZER = new GsonBuilder().create();
	private static final SimpleDateFormat _TS_FMT = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");

	
	public static String getTimeStamp()
	{
		return _TS_FMT.format(new Date());
	}
	public static <T extends Object> T fromJson(String json,Class<T> clazz)
	{
		return _DERIALIZER.fromJson(json, clazz);
	}
	public static <T extends Object> T fromJson(JsonObject obj,Class<T> clazz)
	{
		return _DERIALIZER.fromJson(obj, clazz);
	}
	public static String toJson(Object obj) {
		return (obj != null ? _SERIALIZER.toJson(obj) : "{}");
	}
	public static String toJsonNoPP(Object obj) {
		return (obj != null ? _SERIALIZER_NO_PP.toJson(obj) : "{}");
	}

	public static String serializeThowable(Throwable th) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		th.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
	}

	public static String getPostBody (HttpServletRequest request) {
		StringBuilder jb = new StringBuilder();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
			reader.close();
		} catch (Exception e) {
			jb = new StringBuilder("{}");
		}
		return jb.toString();
	}
	
	public static String encodePassword(String password)
	{
		return Base64.getEncoder().encodeToString(password.getBytes());
	}
}
