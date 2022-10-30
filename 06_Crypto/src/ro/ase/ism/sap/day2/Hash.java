package ro.ase.ism.sap.day2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Hash {
	
	public static byte[] getHashValue(String value, String algorithm) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance(algorithm);
		return md.digest(value.getBytes());
	}
	
	public static byte[] getHashValue(String value, String algorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
		
		MessageDigest md = MessageDigest.getInstance(algorithm, provider);
		return md.digest(value.getBytes());
	}
	
	public static byte[] getFileHashValue(String filename, String algorithm) throws IOException, NoSuchAlgorithmException {
		File file = new File(filename);
		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		MessageDigest md = MessageDigest.getInstance(algorithm);
		
		
		//read the file and process the hash
		byte[] buffer = new byte[10];
		int noBytes = 0;
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes > 0) {
				md.update(buffer,0,noBytes);
			}
		}
		
		bis.close();
		return md.digest();
		
		
	}
	
	
}
