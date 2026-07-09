package backend;

public class UserFactory 
{
    private static UserFactory instance;

    private UserFactory() {}

    public static UserFactory getInstance() {
        if (instance == null) {
            instance = new UserFactory();
        }
        return instance;
    }

    public static User createUser(String userType) 
    {
        switch (userType) {
            case "Traveler":
                return new Traveler();
            case "Admin":
                return new Admin();
            case "Consultant":
                return new Consultant();
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }
}