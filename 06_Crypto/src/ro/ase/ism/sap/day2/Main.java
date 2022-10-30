package ro.ase.ism.sap.day2;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Main {
	
	public static final String BouncyCastleProvider = "BC";
	
	

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		// TODO Auto-generated method stub
		
		CryptoUtil.loadBouncyCastleProvider();

		if(CryptoUtil.isProviderAvailable(BouncyCastleProvider))
			System.out.println("Bouncy Castle is available");
		else
			System.out.println("Bouncy Castle is not available");
		
		if(CryptoUtil.isProviderAvailable("SUN"))
			System.out.println("SUN is available");
		else
			System.out.println("SUN is not available");
		
		if(CryptoUtil.isProviderAvailable("SunJCE"))
			System.out.println("SunJCE is available");
		else
			System.out.println("SunJCE is not available");
		
		//test random bytes
		byte[] randomBytes = RandomGenerator.GetRandomBytes(16);
		System.out.println("Random bytes");
		System.out.println(CryptoUtil.getHex(randomBytes));
		
//		randomBytes = RandomGenerator.GetRandomBytesWithBC(16);
//		System.out.println("Random bytes");
//		System.out.println(CryptoUtil.getHex(randomBytes));
		
		byte[] seed = "password".getBytes();
		randomBytes = RandomGenerator.GetRandomBytes(16, seed);
		System.out.println("Random bytes");
		System.out.println(CryptoUtil.getHex(randomBytes));
		
		//testing OTP encryption
		OTP.encrypt("message.txt", "message.enc", "random.key");
		OTP.decrypt("message.enc", "random.key", "message2.txt");
		
		String password = "password";
		byte[] passHash = Hash.getHashValue(password, "MD5", "BC");
		System.out.println("MD5 value of password is");
		System.out.println(CryptoUtil.getHex(passHash));
		
		passHash = Hash.getHashValue(password, "SHA-1");
		System.out.println("SHA1 value of password is");
		System.out.println(CryptoUtil.getHex(passHash));
		
		byte[] fileHash = Hash.getFileHashValue("message.txt", "SHA-256");
		System.out.println("SHA2 value of the file is");
		System.out.println(CryptoUtil.getHex(fileHash));
		
		
		System.out.println("Done");
	}

}
