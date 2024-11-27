package backend;

import java.sql.Connection;

public class SharedState {
    private static SharedState instance;

    private User User;

    private SharedState() {}

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
