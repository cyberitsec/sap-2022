package ro.ase.ism.sap.day1;

import java.util.BitSet;
import java.util.List;

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
		
		int vb = 10;
		if(vb == 20) {
			System.out.println("It's 20");
		}
		else {
			System.out.println("Is not");
		}
		
		if(vb != 10 && vb != 20) {
			System.out.println("Is not 10 or 20");
		}
		
		if(vb != 20 && vb!= 30 && (++vb)!=31) {
			System.out.println("Is not 20, 3o or 31");
		}
		
		System.out.println(vb);
		
		//bitwise operators
		//vb is 11
		//check is vb has the 4th bit 1
		
		byte bitMask4thBit = 0b0001_0000;	//in binary
		
		System.out.println(bitMask4thBit);
		
		bitMask4thBit = 0x10; //in hex
		
		System.out.println(bitMask4thBit);
		
		bitMask4thBit = 1 << 4; //by shifting 1
		
		System.out.println(bitMask4thBit);
		
		bitMask4thBit = 16; //in decimal
		
		System.out.println(bitMask4thBit);
		
		//the wrong way
		//we check the 4th bit in the last byte - is the 28th one
		
		if((vb & bitMask4thBit) != 0) {
			System.out.println("The 4th bit in vb is 1");
		}
		else {
			System.out.println("The 4th bit in vb is 0");
		}
		
		
		//the right way
		int bitMask32Bits4thBit = 0x10000000;
		bitMask32Bits4thBit = 1 << 28;
		
		if((vb & bitMask32Bits4thBit) != 0) {
			System.out.println("The 4th bit in vb is 1");
		}
		else {
			System.out.println("The 4th bit in vb is 0");
		}
		
		
		byte bitMask2ndBit = 0b0100_0000;
		byte bitMask8thBit = 0x01;
		byte bitMask2and8thBits = (byte) (bitMask2ndBit | bitMask8thBit);
		System.out.println(bitMask2and8thBits);
		
		
		int value = 0x01;
		System.out.println(value);
		value = (byte) (value << 3);
		System.out.println(value);
		
		value = (byte) 0b10000001; //extending a byte to an int preserving the sign bit
		System.out.println(value);
		
		System.out.println("The int in hex is  " + 
					Integer.toHexString(value));
		
		byte byteValue = (byte)value;
		System.out.println("The byte value is " + byteValue);
		value = (value << 3);
		System.out.println(value);	
		
		value = 0x800000FF;
		System.out.println("Decimal: " + value);
		System.out.println("Hex: " + Integer.toHexString(value));
		value = value >> 1;
		System.out.println("Decimal: " + value);
		System.out.println("Hex: " + Integer.toHexString(value));
		
		value = value >>> 1;
		System.out.println("Decimal: " + value);
		System.out.println("Hex: " + Integer.toHexString(value));

		
		//test the LFSR
		byte[] seed = {(byte) 0x00, (byte) 0xFF, 0x08, (byte) 0xFF};
		LFSR32bit lfsr = new LFSR32bit(seed);
		List<Byte> randomBits = lfsr.getRandomBits(32);
		
		System.out.println(randomBits);
		
		//shallow-copy vs clone for arrays
//		byte[] values = {10,20,30,40};
//		System.out.println(values);
//		byte[] copy = values.clone();
//		System.out.println(copy);
//		byte[] anotherCopy = values;
//		System.out.println(anotherCopy);
		
		
		//alternative to byte array or boolean array
		BitSet register = new BitSet(32);
		register.set(0);
		System.out.println("The 1st bit is " + register.get(0));
		register.clear(0);
		System.out.println("The 1st bit is " + register.get(0));


	}

}
