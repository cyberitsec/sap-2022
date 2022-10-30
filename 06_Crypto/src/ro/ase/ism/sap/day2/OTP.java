package ro.ase.ism.sap.day2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class OTP {
	
	public static void encrypt(
			String plaintextFileName, 
			String cipherTextFilename,
			String keyFilename) throws NoSuchAlgorithmException, IOException {
		
		File plaintext = new File(plaintextFileName);
		if(!plaintext.exists())
			throw new UnsupportedOperationException();
		
		File ciphertext = new File(cipherTextFilename);
		if(!ciphertext.exists())
			ciphertext.createNewFile();
		
		File keyFile = new File(keyFilename);
		if(!keyFile.exists())
			keyFile.createNewFile();
		
		long plaintextSize = plaintext.length();
		
		byte[] randomKey = RandomGenerator.GetRandomBytes((int)plaintextSize);
		
		FileInputStream fis = new FileInputStream(plaintext);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		FileOutputStream fos = new FileOutputStream(ciphertext);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		byte[] buffer = new byte[1];
		int noBytesFromFile = 0;
		int keyByteCounter = 0;
		
		
		//reading and xor-ing each byte from plaintext
		while(noBytesFromFile != -1) {
			noBytesFromFile = bis.read(buffer);
			if(noBytesFromFile == 1) {
				buffer[0] = (byte) (buffer[0]^randomKey[keyByteCounter]);
				keyByteCounter+=1;
				bos.write(buffer);
			}
		}
		
		bis.close();
		bos.close();
		
		//write the key into the file
		fos = new FileOutputStream(keyFile);
		bos = new BufferedOutputStream(fos);
		bos.write(randomKey);
		
		bos.close();
			
	}
	
	public static void decrypt(
			String cipherTextFilename,
			String keyFilename,
			String plaintextFileName) throws NoSuchAlgorithmException, IOException {
		
		File plaintext = new File(plaintextFileName);
		if(!plaintext.exists())
			plaintext.createNewFile();
		
		File ciphertext = new File(cipherTextFilename);
		if(!ciphertext.exists())
			throw new FileNotFoundException("Cipher file missing");
		
		File keyFile = new File(keyFilename);
		if(!keyFile.exists())
			throw new FileNotFoundException("Key file missing");
		
		if(ciphertext.length() != keyFile.length()) {
			throw new UnsupportedOperationException("File sizes are different");
		}
		
		
		FileInputStream fis = new FileInputStream(ciphertext);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		FileInputStream fisKey = new FileInputStream(keyFile);
		BufferedInputStream bisKey = new BufferedInputStream(fisKey);
		
		FileOutputStream fos = new FileOutputStream(plaintext);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		byte[] cipherBuffer = new byte[1];
		byte[] keyBuffer = new byte[1];
		int noBytesFromFile = 0;	
		
		//reading and xor-ing each byte from ciphetext with the key one
		while(noBytesFromFile != -1) {
			noBytesFromFile = bis.read(cipherBuffer);
			bisKey.read(keyBuffer);
			if(noBytesFromFile == 1) {
				cipherBuffer[0] = (byte) (cipherBuffer[0]^keyBuffer[0]);
				bos.write(cipherBuffer);
			}
		}
		
		bis.close();
		bisKey.close();
		bos.close();		
	}
	
	
}
