package ro.ase.ism.sap.day3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static String getHexString(byte[] values) {
		StringBuilder sb = new StringBuilder();
		for(byte value : values) {
			sb.append(String.format(" %02x", value));
		}
		return sb.toString();
	}
	
	public static byte[] getSHA1(byte[] values) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		return md.digest(values);
	}
	
}
