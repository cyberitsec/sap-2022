package ro.ase.ism.day1;

public class Certificate implements Cloneable{
	String name;
	int version;
	String[] authorities = {"Google","Amazon", "Cloudflare"};
	
	public Certificate(String name, int version) {
		super();
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
	public String[] getAuthorities() {
		//is not ok
		//return authorities;
		return authorities.clone();
	}

	
	public void setAuthorities(String[] authorities) {
		//is not ok
		//this.authorities = authorities;
		this.authorities = authorities.clone();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nCertificate name = " + name);
		sb.append("\nCertificate version = " + version);
		sb.append("\nCertificate authorities = ");
		for(String ca : authorities) {
			sb.append(ca + " ");
		}
		return sb.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		//return super.clone();
		Certificate copy = new Certificate(name, version);
		copy.authorities = this.authorities.clone();
		return copy;
	}
	
	public void changeAuthority() {
		this.authorities[0] = "Home";
	}
	
	

	
}
