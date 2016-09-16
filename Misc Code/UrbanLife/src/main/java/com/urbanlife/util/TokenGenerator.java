package com.urbanlife.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import org.apache.commons.codec.binary.Hex;

public class TokenGenerator {
	
public TokenGenerator(){}

public String generateToken(){
	 SecureRandom sr=null;
	try {
		sr = SecureRandom.getInstance("SHA1PRNG");
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    byte[] temp=new byte[10];
	    sr.nextBytes(temp);
	    String token=new String(Hex.encodeHex(temp));
	return token;
}

public long generateExpTime(){
	
	Calendar cal = Calendar.getInstance();
	cal.getTime();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	sdf.format(cal.getTime());
   long expTime= cal.getTimeInMillis()+(24*1*3600000);
	return expTime;
}
public String responseToken(String tokenid,long expTime){
	return tokenid+":"+expTime;
}
public String getPrefix(String text ){
	String prefix = text.substring(text.lastIndexOf('_') + 1);
	return prefix;
	
}
public long getCurrentTime(){
	
	Calendar cal = Calendar.getInstance();
	cal.getTime();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	sdf.format(cal.getTime());
   long currentTime= cal.getTimeInMillis();
	return currentTime;
}


}