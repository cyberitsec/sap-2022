package ro.ase.ism.sap.day1;

import java.util.ArrayList;
import java.util.List;

public class LFSR32bit {

	// 32 bit LFSR with 1 register
	// tap sequence(from right to left): x^31 + x^11 + x^5 + x^2
	// it needs an intial key/seed of 32 bits

	byte[] register = new byte[4];

	public LFSR32bit(byte[] seed) {

		if (seed.length != 4) {
			throw new UnsupportedOperationException("Seed size not ok");
		}

		// don't do shallow copy
		// this.register = seed

		for (int i = 0; i < register.length; i++) {
			this.register[i] = seed[i];
		}

		// alternative
		// this.register = seed.clone();
	}

	public byte get31Bit() {
		byte maskMostSignificatBit = (byte) 0b1000_0000;
		byte result = (byte) (register[0] & maskMostSignificatBit);
		return (byte) (result == 0 ? 0 : 1);
	}

	public byte get11Bit() {
		byte mask11thbit = (byte) 0b0000_1000;
		byte result = (byte) (register[2] & mask11thbit);
		return (byte) (result == 0 ? 0 : 1);
	}

	public byte get5Bit() {
		byte mask5thbit = (byte) 0b0010_0000;
		byte result = (byte) (register[3] & mask5thbit);
		return (byte) (result == 0 ? 0 : 1);
	}

	public byte get2Bit() {
		byte mask2ndbit = (byte) 0b0000_0100;
		byte result = (byte) (register[3] & mask2ndbit);
		return (byte) (result == 0 ? 0 : 1);
	}

	public byte getLastBit(byte value) {
		return (byte) (value & 1);
	}

	public byte shiftAndAddMostSignificantBit(byte value, byte bit) {

//		byte initialBitOfValue = (byte) (value & 0x7f);
//		byte remaining7bitsValue = (byte) (value & 0x7f);
//		Integer someValue = (byte) remaining7bitsValue >>> 1;

		value = (byte) ((value & 0xff) >>> 1);

		return (byte) (value | (bit << 7));
	}

	public byte shiftRegisterAndAddBit(byte bit) {
		byte firstByteLastBit = getLastBit(register[0]);
		register[0] = shiftAndAddMostSignificantBit(register[0], bit);

		byte secondByteLastBit = getLastBit(register[1]);
		register[1] = shiftAndAddMostSignificantBit(register[1], firstByteLastBit);

		byte thirdByteLastBit = getLastBit(register[2]);
		register[2] = shiftAndAddMostSignificantBit(register[2], secondByteLastBit);

		byte pseudoRandomBit = getLastBit(register[3]);
		register[3] = shiftAndAddMostSignificantBit(register[3], thirdByteLastBit);

		return pseudoRandomBit;
	}

	public byte processTapSequence() {
		return (byte) (get31Bit() ^ get11Bit() ^ get5Bit() ^ get2Bit());
	}

	public List<Byte> getRandomBits(int noBits) {

		// we need to run 32 iterations to remove the initial seed
		
		for(int i = 0 ; i < 32; i++) { byte nextBit = processTapSequence();
		shiftRegisterAndAddBit(nextBit); }
		
		List<Byte> randomBits = new ArrayList<>();
		for (int i = 0; i < noBits; i++) {
			byte nextBit = processTapSequence();
			byte randomBit = shiftRegisterAndAddBit(nextBit);
			randomBits.add(randomBit);
		}

		return randomBits;
	}

}
