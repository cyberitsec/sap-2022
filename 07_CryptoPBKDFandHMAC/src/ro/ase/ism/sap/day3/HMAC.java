package ro.ase.ism.sap.day3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {
	
	public static byte[] getHMAC(byte[] input, String key, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
		
		Mac hmac= Mac.getInstance(algorithm);
		Key hmacKey = new SecretKeySpec(key.getBytes(), algorithm);
		hmac.init(hmacKey);
		
		//one step hmac
		return hmac.doFinal(input);	
	}
	
	public static byte[] getHMAC(String fileName, String key, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		byte[] buffer = new byte[10];
		int noBytes = 0;
		
		Mac mac = Mac.getInstance(algorithm);
		mac.init(new SecretKeySpec(key.getBytes(), algorithm));
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes != -1)
				mac.update(buffer,0,noBytes);
		}
		
		fis.close();
		
		return mac.doFinal();	
	}

}
