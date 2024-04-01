package net.SereinTeam;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HWIDUtils {
	public static String PWD1;
	public static int PWD3;
	public static String PWD4;

	public static String getHWID() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		//ת��
		StringBuilder s = new StringBuilder();
		String main = System.getenv("PROCESS_IDENTIFIER") + System.getenv("COMPUTERNAME");
		byte[] bytes = main.getBytes("UTF-8");
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] md5 = messageDigest.digest(bytes);
		int i = 0;
		for (byte b : md5) {
			s.append(Integer.toHexString((b & 0xFF) | 0x300), 0, 3);
			if (i != md5.length - 1) {
				s.append("");
			}
			i++;

		}
	return (s.toString()).substring(s.length() - 15, s.length());}
	public static String hwidkey(){
		try {
			PWD1 = getHWID();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		int temp = 0;
		int tempsum = 0;
		for (int j = 0; j < PWD1.length(); j++) {
			char item = PWD1.charAt(j);
			temp = Integer.valueOf(item);
			tempsum += temp * (j+temp^4);
			PWD3=tempsum;
		}
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		String dateString = dateFormat.format(currentDate);

		PWD4 = Integer.toString(PWD3*Integer.parseInt(dateString));
		return PWD1+PWD4;
	}
}
//源码by秦始皇

