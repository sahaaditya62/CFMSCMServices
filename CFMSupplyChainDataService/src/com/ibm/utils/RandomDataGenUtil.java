package com.ibm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomDataGenUtil {
	private static final SimpleDateFormat _TIME_FMT=new SimpleDateFormat("HH:mm");
	private static final Random _RAND = new Random(System.currentTimeMillis());
	private static final String _CHARTECER="ABCDEFGHIJKLMNOPUVWXYZ0123456789";
	public static String getRandomTimeOfDay(){
		long time = System.currentTimeMillis();
		int hrs = getRandomNumberBelow(4)*6;
		int mins = getRandomNumberBelow(4)*15;
		
		return _TIME_FMT.format(new Date(time+hrs*3600000+mins*60000));
	}
	public static int getRandomNumberBelow(int limit){
		return _RAND.nextInt(limit);
	}
	public static String pickupFromList(String[] list){
		return list[getRandomNumberBelow(list.length)];
	}
	public static String generateRandomString(String str,int length){
		String encoder  = null;
		if(str==null || str.trim().length()==0){
			encoder=_CHARTECER;
		}
		else
		{
			encoder=str.trim();
		}
		StringBuilder randomString = new StringBuilder();
		int limit = encoder.length();
		for(int index=0;index<length;index++){
			randomString.append(encoder.charAt(_RAND.nextInt(limit)));
		}
		return randomString.toString();
	}
	public static String generateRandomUUID(String str,int[] length){
		String encoder  = null;
		if(str==null || str.trim().length()==0){
			encoder=_CHARTECER;
		}
		else
		{
			encoder=str.trim();
		}
		StringBuilder randomString = new StringBuilder();
		for(int len: length){
			randomString.append("-").append(generateRandomString(encoder,len));
		}
		
		return randomString.toString().substring(1);
	}
	
}
