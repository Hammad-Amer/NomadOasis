package backend;

import java.util.List;

public class Consultant extends User{

	public Consultant(String email, String username, String password, String cnic, String gender, String dob) {
		super(email, username, password, cnic, gender, dob);
		// TODO Auto-generated constructor stub
	}

	public Consultant()
	{
		
	}
	public List<String> getQueries()
	{
		DBHandler dbHandler = DBHandler.getInstance();
		return dbHandler.getAssignedQueries(getUserid());
	}
	public String getQInfo(String qID)
	{
		DBHandler dbHandler = DBHandler.getInstance();
		return dbHandler.getQueryInfo(qID);
	}
	public boolean submitQueryResponse(String qID, String response)
	{
		DBHandler dbHandler = DBHandler.getInstance();
		return dbHandler.submitResponse(qID, response);		
	}
}
