package ro.ase.ism.sap.day2;

import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptoUtil {
	
	public static boolean isProviderAvailable(String providerName) {
		Provider provider = Security.getProvider(providerName);
		return provider == null ? false : true;
	}
	
	public static void loadBouncyCastleProvider() {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static String getHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for(byte value : bytes) {
			sb.append(String.format("%02x ", value).toUpperCase());
		}
		return sb.toString();
	}
	
}
