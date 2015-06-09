package entitylayer;



public class Medewerker {
	private int id;
    private String wachtWoord;
	
	 public Medewerker(int id, String wachtWoord) {
	        this.id = id;
	        this.wachtWoord  = wachtWoord;
	    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWachtWoord() {
		return wachtWoord;
	}

	public void setWachtWoord(String wachtWoord) {
		this.wachtWoord = wachtWoord;
	}
	 
}
