package backend;
import java.sql.Connection;

import backend.DBHandler;

public class Traveler extends User{

	

	public Traveler(String email, String username, String password, String cnic, String gender, String dob) {
		super(email, username, password, cnic, gender, dob);
		
	}

	public Traveler() {
		// TODO Auto-generated constructor stub
	}

	public boolean addtravelertoDB(Connection conn)
	{
	
		boolean flag;
		DBHandler dbhandler=new DBHandler(conn);

		flag=dbhandler.InsertingTravelerTODB(getEmail(), getUsername(), getPassword(), getCnic(), getGender(), getDob());
	
		return flag;
		
	}


}
