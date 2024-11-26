package backend;

public abstract class  User {
	private String userid;
	private String email;
	private String username;
	private String password;
	private String cnic;
	private String gender;
	private String dob;

	
	
	 public User(String email, String username, String password, String cnic, String gender, String dob) {
	        this.email = email;
	        this.username = username;
	        this.password = password;
	        this.cnic = cnic;
	        this.gender = gender;
	        this.dob = dob;
	    }

	 public User()
	 {
		 
	 }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCnic() {
		return cnic;
	}

	public void setCnic(String cnic) {
		this.cnic = cnic;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	

}
