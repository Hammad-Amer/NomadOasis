package backend;

import java.sql.Connection;

public class SharedState {
    private static SharedState instance;

    private Connection connection;
    private String travelerID;

    // Private constructor to enforce singleton pattern
    private SharedState() {}

    // Get the single instance of the class
    public static SharedState getInstance() {
        if (instance == null) {
            instance = new SharedState();
        }
        return instance;
    }

    // Getters and setters for shared data
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getTravelerID() {
        return travelerID;
    }

    public void setTravelerID(String travelerID) {
        this.travelerID = travelerID;
    }
}
