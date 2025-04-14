package quickchat;

import javax.swing.JFrame;

/**
 * @author Takalani Mudau
 * @see  ST10479097@rcconnect.edu.za 
 * @version part 1 of 3
 * @since 7 April 2025
 */

public class QuickChat
{
    public static void main(String[] args)
    {
        // Create a new Registration object to hold user data
        Registration registration = new Registration();
        
        // Create a new Login object, passing in the registration data
        // This is the most important step because without this...
        // The Login instance won't access the Registration instance data
        Login login = new Login(registration);
        
        // Initialize the GUI panel for user registration and login
        RegistrationPanel registrationPanel = new RegistrationPanel(registration, login);
        
        // Close the application when the window is closed
        registrationPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Center the window on the screen
        registrationPanel.setLocationRelativeTo(null);
        
        // Make the window visible
        registrationPanel.setVisible(true);
    }
}
