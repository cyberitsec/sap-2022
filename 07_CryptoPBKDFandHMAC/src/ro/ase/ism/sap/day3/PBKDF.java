package ro.ase.ism.sap.day3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class PBKDF {
	
	public static byte[] getPBKDFValue(
			String password, byte[] salt, int noIterations, 
			String algorithm, int outputSize) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		SecretKeyFactory pbkdf = SecretKeyFactory.getInstance(algorithm);
		
		PBEKeySpec pbkdfSpecifications = 
				new PBEKeySpec(password.toCharArray(),salt,noIterations, outputSize);
		
		SecretKey generatedKey = pbkdf.generateSecret(pbkdfSpecifications);
		
		return generatedKey.getEncoded();
		
	}
	
	
}
