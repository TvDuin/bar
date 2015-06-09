package entitylayer;



public class Medewerker {
	private String id;
    private String password;
	
	 public Medewerker(String id, String password) {
	        this.id = id;
	        this.password  = password;
	    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}
	 
}
