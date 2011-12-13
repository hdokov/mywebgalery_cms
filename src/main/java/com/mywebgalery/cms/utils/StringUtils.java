package com.mywebgalery.cms.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtils {

	private static Logger _log = LoggerFactory.getLogger(StringUtils.class);

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	public static final String LINE_BREAK = "<br>";

	public static final String TEXT_LINE_BREAK = "\n";
	public static final int LINE_LENGTH = 76;


	/**
	 * Checks if the string is <code>null</code> or its length is 0
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		return s == null || s.length() == 0;
	}

	/**
	 * Checks if the string is <code>null</code> or it contains only whitespaces
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s){
		return s == null || s.trim().length() == 0;
	}

	public static String wrap(String strText) {
		StringBuffer strWrapText = new StringBuffer();
		StringTokenizer objToken = new StringTokenizer(strText, "\n", true);
		while (objToken.hasMoreTokens()) {
			String strParagraf = objToken.nextToken();
			String strWrapped = strParagraf;
			if (!strParagraf.equals("\n"))
				strWrapped = wrapParagraf(strParagraf);
			strWrapText.append(strWrapped);
		}
		return strWrapText.toString();
	}
	private static String wrapParagraf(String strText) {
		StringBuffer strWrappedText = new StringBuffer();
		while (strText.length() > LINE_LENGTH) {
			String strLine = strText.substring(0, LINE_LENGTH);
			if (strLine.endsWith(" ")) {
				strWrappedText.append(strLine.trim());
				strWrappedText.append(TEXT_LINE_BREAK);
				strText = strText.substring(LINE_LENGTH);
			} else {
				int nLastLineSpace = strLine.lastIndexOf(" ");
				if (nLastLineSpace > -1) {
					strWrappedText.append(strText.substring(0, nLastLineSpace + 1).trim());
					strWrappedText.append(TEXT_LINE_BREAK);
					strText = strText.substring(nLastLineSpace).trim();
				} else {
					int nFirstSpaceInText = strText.indexOf(" ");
					if (nFirstSpaceInText > -1) {
						strWrappedText.append(strText.substring(0, nFirstSpaceInText + 1).trim());
						strWrappedText.append(TEXT_LINE_BREAK);
						strText = strText.substring(nFirstSpaceInText + 1).trim();
					} else {
						strWrappedText.append(strText);
						strText = "";
					}
				}
			}
		}
		strWrappedText.append(strText.trim());
		return strWrappedText.toString();

	}

	public static String getCode(long id){
		String sid = String.valueOf(id);
		if(sid.length() > 9)
			return "0"+sid;
		StringBuffer result = new StringBuffer();
		result.append(sid.length());

	    String ihash = digest(sid);
	    result.append(ihash.substring(ihash.length() - (9-sid.length())));

		result.append(sid);
		return result.toString();
	}

	public static long getId(String code){
		if(code.startsWith("0"))
			return Long.parseLong(code);

		int digits = Integer.parseInt(code.substring(0,1));
		String id = code.substring(10-digits);
		String hash = code.substring(1, 10-digits);
		//verify code
		if(digits == 9)
			return Long.parseLong(id);

		if(digest(id).endsWith(hash))
			return Long.parseLong(id);

		return -1;
	}

	/**
	 * Encodes a string in md5 and returns a numeric string representing the hash
	 * @param s
	 * @return a numeric string
	 */
	public static String digest(String s){
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
		    digest.update(s.getBytes());
		    byte[] hash = digest.digest();
		    BigInteger i = new BigInteger(hash);
		    return i.toString();
		} catch (NoSuchAlgorithmException e) {
			_log.error(e.getMessage(), e);
		}
		return null;
	}

}
