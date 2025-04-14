package quickchat;

/**
 * @author Takalani Mudau
 * @see  ST10479097@rcconnect.edu.za 
 * @version part 1 of 3
 * @since 7 April 2025
 */
public class Login 
{
    // Reference to the registration details for login validation
    private final Registration registration;

    // Tracks whether the user is currently logged in
    private boolean loggedIn;

    // Constructor connects Login to a specific Registration instance
    public Login(Registration registration) 
    {
        this.registration = registration;
        this.loggedIn = false; // User starts off as not logged in
    }

    // Attempts to log in using provided username and password
    public boolean loginUser(String username, String password) 
    {
        // Validates the credentials against the registered details
        loggedIn = username != null && password != null &&
                     username.equals(registration.getUsername()) &&
                     password.equals(registration.getPassword());
        return loggedIn;
    }

    // Returns a message based on whether the user is logged in
    public String returnLoginStatus() 
    {
        if (loggedIn) {
            return "Welcome back " + registration.getFirstName() + " " + 
                   registration.getLastName() + "!\nit is great to see you.";
        } else {
            return "Username or password incorrect, please try again!";
        }
    }

}