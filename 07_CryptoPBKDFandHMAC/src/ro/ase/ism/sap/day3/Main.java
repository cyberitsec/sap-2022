package ro.ase.ism.sap.day3;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IOException {

		String password = "12345678";
		String salt = "ismsap";
		
		double tStart = System.currentTimeMillis();
		
		byte[] generatedHash = PBKDF.getPBKDFValue(
				password, salt.getBytes(), 100000, "PBKDF2WithHmacSHA256",160);
		
		double tFinal = System.currentTimeMillis();
		
		System.out.println("Required time: " + (tFinal - tStart)/1000);	
		System.out.println("Processed password with PBKDF");
		System.out.println(Util.getHexString(generatedHash));
		
		tStart = System.currentTimeMillis();
		byte[] sha1Hash = Util.getSHA1(password.getBytes());
		tFinal = System.currentTimeMillis();
		
		System.out.println("Required time: " + (tFinal - tStart)/1000);	
		System.out.println("Processed password with SHA1");
		System.out.println(Util.getHexString(sha1Hash));
		
		
		//test hmac
		String message = "Check ISM schedule";
		byte[] msgHmac = HMAC.getHMAC(message.getBytes(), "ismsecret", "HmacSHA1");
		System.out.println("Message sha1 hmac");
		System.out.println(Util.getHexString(msgHmac));
		
		sha1Hash = Util.getSHA1(message.getBytes());
		System.out.println("Message sha1 value");
		System.out.println(Util.getHexString(sha1Hash));
		
		
		byte[] fileHmac = HMAC.getHMAC("message.txt", "ismsecret", "HmacSHA1");
		System.out.println("File sha1 hmac");
		System.out.println(Util.getHexString(fileHmac));
		
	}

}
