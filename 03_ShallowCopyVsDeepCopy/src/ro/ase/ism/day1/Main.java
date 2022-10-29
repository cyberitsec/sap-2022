package ro.ase.ism.day1;

public class Main {

	public static void main(String[] args) throws CloneNotSupportedException {
		
		Certificate certificate = new Certificate("ISM", 1);
		System.out.println(certificate);
		
		String[] cas = certificate.getAuthorities();
		cas[0] = "Home";
		
		System.out.println(certificate);
		
		String[] newAuthorities = {"VeriSign"};
		certificate.setAuthorities(newAuthorities);
		
		newAuthorities[0] = "Home";
		
		System.out.println(certificate);
		
		//clone the certificate
		Certificate copy = (Certificate) certificate.clone();
		
		System.out.println(copy);
		
		copy.changeAuthority();
		
		System.out.println(copy);
		System.out.println(certificate);
	}

}


