package ro.ase.ism.sap.day2;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;

public class RandomGenerator {
	
	
	//don't use it; is not secure
	public static byte[] DontUseGenerator(int noBytes) {
		Random random = new Random();
		byte[] notRandomValues = new byte[noBytes];
		random.nextBytes(notRandomValues);
		return notRandomValues;
	}
	
	public static byte[] GetRandomBytes(int noBytes) throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] randomBytes = new byte[noBytes];
		secureRandom.nextBytes(randomBytes);
		return randomBytes;
	}
	
	public static byte[] GetRandomBytes(int noBytes, byte[] seed) throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(seed);
		byte[] randomBytes = new byte[noBytes];
		secureRandom.nextBytes(randomBytes);
		return randomBytes;
	}
	
	//BC does not have a SecureRandom implementation
//	public static byte[] GetRandomBytesWithBC(int noBytes) throws NoSuchAlgorithmException, NoSuchProviderException {
//		SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG","BC");
//		byte[] randomBytes = new byte[noBytes];
//		secureRandom.nextBytes(randomBytes);
//		return randomBytes;
//	}

}
