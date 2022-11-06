package ro.ase.ism.sap.day3;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

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
		
		//test symmetric encryption
		SymmetricEncryption.encryptECB("secret.txt", "passwordpassword", "AES", "secret.enc");
		SymmetricEncryption.decryptECB("secret.enc", "passwordpassword", "AES", "secret2.txt");
		
		//test CBC
		SymmetricEncryption.encryptCBC("secret.txt", "ism1pass", "DES", "secretCBC.enc");
		SymmetricEncryption.decryptCBC("secretCBC.enc", "ism1pass", "DES", "secret3.txt");
		
		//test CTR
		SymmetricEncryption.encryptCTR("secret.txt", "ism1pass", "DES", "secretCTR.enc");
		SymmetricEncryption.decryptCTR("secretCTR.enc", "ism1pass", "DES", "secret4.txt");
		
		//test CTS
		SymmetricEncryption.encryptCTS("secret.txt", "ism1pass", "DES", "secretCTS.enc");
		SymmetricEncryption.decryptCTS("secretCTS.enc", "ism1pass", "DES", "secret5.txt");
		
		System.out.println("The end");
	}

}
