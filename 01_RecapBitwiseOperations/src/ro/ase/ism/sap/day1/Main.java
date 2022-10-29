package ro.ase.ism.sap.day1;

import java.util.Base64;

public class Main {
	
	public static String getHex(byte[] value) {
		StringBuffer sb = new StringBuffer();
		for(byte input : value) {
			sb.append("0x" + String.format("%02x", input).toUpperCase());
			sb.append(" ");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		char character = 'a';		//2 byte variable
		byte smallNumber = 23;		//1 byte variable
		
		String password ="password";
		//converting a string to a byte array
		byte[] passwordBytes = password.getBytes();
		
		System.out.println("The length of the password byte array is " + 
				passwordBytes.length);
		
		String oldPassword = new String(passwordBytes);
		System.out.println("The initial password is " + oldPassword);
		
		
		byte passByte = 0b00000010;
		passByte = 0x02;
		
		byte[] binaryKey = {(byte) 0b1000_0001, 0b0000_0010, 0b0000_0100, 0b0000_1000};
		byte[] anotherBinaryKey = {(byte) 0x81, 0x02, 0x04, 0x08}; 
		
		
		//DON'T do this - you lose the value
		String binaryKeyAsString = new String(binaryKey);
		
		System.out.println("The binary key is " + binaryKeyAsString);
		System.out.println("The key size: " + binaryKeyAsString.length());
		
		byte[] initialKey = binaryKeyAsString.getBytes();
		if(initialKey[0] == binaryKey[0]) {
			System.out.println("They are the same");
		}
		else {
			System.out.println("They are different");
		}
		
		//encoding binary values to Base64
		String base64Key = Base64.getEncoder().encodeToString(binaryKey);
		System.out.println("The binary key as Base64 is " + base64Key);
		
		//decoding Base64 to binary
		initialKey = Base64.getDecoder().decode(base64Key);
		if(initialKey[0] == binaryKey[0]) {
			System.out.println("They are the same");
		}
		else {
			System.out.println("They are different");
		}
		
		//DON'T do this - you lose the value
		System.out.println("The initial key as String " + new String(initialKey));
		
		
		byte[] someValues = {(byte) 0b1111_1111, (byte) 0xA3, 0x03, 0b0000_1111};
		System.out.println("The hex value is " + 
		String.format("0x%02x", someValues[3]).toUpperCase());
		
		System.out.println(getHex(someValues));

	}

}
