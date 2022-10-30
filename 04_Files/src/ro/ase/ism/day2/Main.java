package ro.ase.ism.day2;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Main {

	public static void main(String[] args) throws IOException {

		//managing files
		
		//the file is relative to the project folder root
		File message = new File("message.txt");
		if(!message.exists()) {
			message.createNewFile();
		}
		
		//write into the text file
		//for append mode use the ctor with 2nd argument - by default is trunc mode
		FileWriter fileWriter = new FileWriter(message,false);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println("This is a secret message.");
		printWriter.println("Don't tell anyone");
		printWriter.close();
		
		//read from the text file
		FileReader fileReader = new FileReader(message);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String line = bufferedReader.readLine();
		while(line != null) {
			System.out.println("Text file line: " + line);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		
		//play with the file system
		File folderRoot = new File("D:\\2022-2023\\sap-2022");
		if(folderRoot.exists() && folderRoot.isDirectory()) {
			File[] files = folderRoot.listFiles();
			for(File file : files) {
				String description = file.isFile() ? "is a file" : "is a folder";
				System.out.println(file.getName() + " - " + description);
			}
		}
		
		File tempFolder = new File("D:\\2022-2023\\sap-2022\\temp");
		if(!tempFolder.exists()) {
			tempFolder.mkdir();
		}
		
		//using binary files
		File myData = new File("mysecret.data");
		if(!myData.exists()) {
			myData.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(myData);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		DataOutputStream dos = new DataOutputStream(bos);
		
		int value = 23;
		float floatValue = 23.5f;
		boolean boolValue = true;
		String name = "John";
		
		dos.writeInt(value);
		dos.writeBoolean(boolValue);
		dos.writeFloat(floatValue);
		dos.writeUTF(name);
		
		dos.close();
		
		//reading from binary files
		FileInputStream fis = new FileInputStream(myData);
		DataInputStream dis = new DataInputStream(fis);
		
		value = dis.readInt();
		boolValue = dis.readBoolean();
		floatValue= dis.readFloat();
		name = dis.readUTF();
		
		dis.close();
		
		System.out.println("Name  = " + name);
		System.out.println("Float value = " + floatValue);
		
		//using RandomAccessFiles
		File values = new File("values.bin");
		if(!values.exists()) {
			values.createNewFile();
		}
		RandomAccessFile raf = new RandomAccessFile(values, "rw");
		byte[] numbers = {10,20,30,40};
		for(byte number : numbers) {
			raf.writeByte(number);
		}
		raf.close();
		
		raf = new RandomAccessFile(values, "r");
		//reading the 3rd value
		//if the values were int you need to jump 8 bytes
		raf.seek(2);
		byte thirdValue = raf.readByte();
		System.out.println("3rd value is " + thirdValue);
		raf.close();
		
		raf = new RandomAccessFile(values, "r");
		long fileSize = raf.length();
		System.out.println("File size is " + fileSize);
		
		raf.close();
		
		System.out.println("Done");
		
		
		
	}

}
