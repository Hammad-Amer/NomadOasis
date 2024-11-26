package backend;

import java.sql.Connection;

public class SharedState {
    private static SharedState instance;

    private Connection connection;
    private User User;

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

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

   
}
