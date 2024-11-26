package backend;

import java.sql.Connection;

public class SharedState {
    private static SharedState instance;

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


	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

   
}
