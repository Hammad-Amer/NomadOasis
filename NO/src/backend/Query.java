package backend;

public class Query {
    private int queryID;
    private int consultantID;
    private String travelerID;
    private String queryContent;
    private String response; 

    public Query(int queryID, int consultantID, String travelerID2, String queryContent)
    {
        this.queryID = queryID;
        this.consultantID = consultantID;
        this.travelerID = travelerID2;
        this.queryContent = queryContent;
        this.response = null; 
    }

    public int getQueryID() 
    {
        return queryID;
    }

    public void setQueryID(int queryID)
    {
        this.queryID = queryID;
    }

    public int getConsultantID()
    {
        return consultantID;
    }

    public void setConsultantID(int consultantID) 
    {
        this.consultantID = consultantID;
    }

    public String getTravelerID() 
    {
        return travelerID;
    }

    public void setTravelerID(String travelerID) 
    {
        this.travelerID = travelerID;
    }

    public String getQueryContent()
    {
        return queryContent;
    }

    public void setQueryContent(String queryContent)
    {
        this.queryContent = queryContent;
    }

    public String getResponse() 
    {
        return response;
    }

    public void setResponse(String response) 
    {
        this.response = response;
    }

    public String viewQueryContent() 
    {
        String q = "Query Content: " + queryContent;
        return q;
    }

    public String viewQueryOwner() 
    {
        return "Query Traveler with ID: " + travelerID;
    }
    
    public String respondToQuery(String response)
    {
        if (this.response == null) 
        {
            this.response = response;
            return "Response has been saved.";
        } 
        else 
        {
            return "Query already responded to.";
        }
    }
    
}
