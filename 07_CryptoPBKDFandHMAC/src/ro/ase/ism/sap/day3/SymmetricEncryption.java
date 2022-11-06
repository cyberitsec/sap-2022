package ro.ase.ism.sap.day3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricEncryption {

	public static void encryptECB(
			String inputFile, String key, String algorithm, String cipherFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		File input = new File(inputFile);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(cipherFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		Cipher cipher = Cipher.getInstance(algorithm + "/ECB/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBuffer;
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes != -1) {
				cipherBuffer = cipher.update(buffer, 0, noBytes);
				bos.write(cipherBuffer);
			}
		}
		cipherBuffer = cipher.doFinal();
		bos.write(cipherBuffer);
		
		fis.close();
		bos.close();	
	}
	
	public static void decryptECB(
			String cipherFile, String key, String algorithm, String plainFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		File input = new File(cipherFile);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(plainFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		Cipher cipher = Cipher.getInstance(algorithm + "/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), algorithm));
		
		byte[] inputBuffer = new byte[cipher.getBlockSize()];
		byte[] outputBuffer;
		int noBytes = 0;
		
		while(noBytes != -1) {
			noBytes = bis.read(inputBuffer);
			if(noBytes != -1) {
				outputBuffer = cipher.update(inputBuffer,0,noBytes);
				bos.write(outputBuffer);
			}
		}
		
		outputBuffer = cipher.doFinal();
		bos.write(outputBuffer);
		
		bos.close();
		fis.close();		
	}
	
	public static void encryptCBC(
			String inputFile, String key, String algorithm, String cipherFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		File input = new File(inputFile);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(cipherFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		
		//generate a new random IV for each encryption
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] IV = secureRandom.getSeed(cipher.getBlockSize());
		
		IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
		
		//write IV in the cipher file
		bos.write(IV);
		
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBuffer;
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes != -1) {
				cipherBuffer = cipher.update(buffer, 0, noBytes);
				bos.write(cipherBuffer);
			}
		}
		cipherBuffer = cipher.doFinal();
		bos.write(cipherBuffer);
		
		fis.close();
		bos.close();	
	}
	
	public static void decryptCBC(
			String inputCipher, String key, String algorithm, String plainFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		File input = new File(inputCipher);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(plainFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		
		//get IV from the cipher file - the 1st block

		byte[] IV = new byte[cipher.getBlockSize()];
		bis.read(IV);
		
		IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
		
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBuffer;
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes != -1) {
				cipherBuffer = cipher.update(buffer, 0, noBytes);
				bos.write(cipherBuffer);
			}
		}
		cipherBuffer = cipher.doFinal();
		bos.write(cipherBuffer);
		
		fis.close();
		bos.close();	
	}
	
	
	public static void encryptCTR(
			String inputFile, String key, String algorithm, String cipherFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		File input = new File(inputFile);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(cipherFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		
		Cipher cipher = Cipher.getInstance(algorithm + "/CTR/NoPadding");
		
		
		//define Counter initial value
		byte[] counterInitialValue = new byte[cipher.getBlockSize()];
		//our choice
		counterInitialValue[counterInitialValue.length-1] =
				(byte) 0xff;
		
		IvParameterSpec ivParamSpec = new IvParameterSpec(counterInitialValue);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
		
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBuffer;
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes != -1) {
				cipherBuffer = cipher.update(buffer, 0, noBytes);
				bos.write(cipherBuffer);
			}
		}
		cipherBuffer = cipher.doFinal();
		bos.write(cipherBuffer);
		
		fis.close();
		bos.close();	
	}
	
	public static void decryptCTR(
			String inputFile, String key, String algorithm, String plainFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		File input = new File(inputFile);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(plainFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		
		Cipher cipher = Cipher.getInstance(algorithm + "/CTR/NoPadding");
		
		
		//define Counter initial value
		byte[] counterInitialValue = new byte[cipher.getBlockSize()];
		//our choice
		counterInitialValue[counterInitialValue.length-1] =
				(byte) 0xff;
		
		IvParameterSpec ivParamSpec = new IvParameterSpec(counterInitialValue);
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
		
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBuffer;
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes != -1) {
				cipherBuffer = cipher.update(buffer, 0, noBytes);
				bos.write(cipherBuffer);
			}
		}
		cipherBuffer = cipher.doFinal();
		bos.write(cipherBuffer);
		
		fis.close();
		bos.close();	
	}
	
	public static void encryptCTS(
			String inputFile, String key, String algorithm, String cipherFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		File input = new File(inputFile);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(cipherFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		Cipher cipher = Cipher.getInstance(algorithm + "/CTS/NoPadding");
		
		//you need an IV because CTS is implemented in CBC mode
		IvParameterSpec iv = new IvParameterSpec(new byte[cipher.getBlockSize()]);
				
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
		
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBuffer;
		
		while(noBytes != -1) {
			noBytes = bis.read(buffer);
			if(noBytes != -1) {
				cipherBuffer = cipher.update(buffer, 0, noBytes);
				bos.write(cipherBuffer);
			}
		}
		cipherBuffer = cipher.doFinal();
		bos.write(cipherBuffer);
		
		fis.close();
		bos.close();	
	}
	
	public static void decryptCTS(
			String cipherFile, String key, String algorithm, String plainFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		File input = new File(cipherFile);
		if(!input.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(input);
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		File output = new File(plainFile);
		if(!output.exists()) {
			output.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(output);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		
		
		Cipher cipher = Cipher.getInstance(algorithm + "/CTS/NoPadding");
		
		//you need an IV because CTS is implemented in CBC mode
		IvParameterSpec iv = new IvParameterSpec(new byte[cipher.getBlockSize()]);
		
		cipher.init(
				Cipher.DECRYPT_MODE, 
				new SecretKeySpec(key.getBytes(), algorithm), 
				iv);
		
		byte[] inputBuffer = new byte[cipher.getBlockSize()];
		byte[] outputBuffer;
		int noBytes = 0;
		
		while(noBytes != -1) {
			noBytes = bis.read(inputBuffer);
			if(noBytes != -1) {
				outputBuffer = cipher.update(inputBuffer,0,noBytes);
				bos.write(outputBuffer);
			}
		}
		
		outputBuffer = cipher.doFinal();
		bos.write(outputBuffer);
		
		bos.close();
		fis.close();		
	}
	
}
