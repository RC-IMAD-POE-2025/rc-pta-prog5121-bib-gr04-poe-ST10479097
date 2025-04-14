package quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    /** The Registration instance used to store test user data. */
    private Registration registration;

    /** The Login instance under test, linked to the Registration object. */
    private Login login;

    // Test data
    private static final String VALID_USERNAME = "kyl_1";
    private static final String VALID_PASSWORD = "Ch&&sec@ke99!";
    private static final String VALID_CELL_PHONE_NUMBER = "+27838968976";
    private static final String VALID_FIRST_NAME = "Kyle";
    private static final String VALID_LAST_NAME = "Smith";
    private static final String INVALID_USERNAME = "kyle!!!!!!!";
    private static final String INVALID_PASSWORD = "password";

    // Expected messages
    private static final String SUCCESS_LOGIN_MESSAGE = "Welcome back " + VALID_FIRST_NAME + " " + VALID_LAST_NAME + "!\nit is great to see you.";
    private static final String FAILED_LOGIN_MESSAGE = "Username or password incorrect, please try again!";

    @BeforeEach
    public void setUp() {
        registration = new Registration();
        registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        login = new Login(registration);
    }

    @Test
    void testSuccessfulLogin() {
        boolean result = login.loginUser(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(result);
        assertEquals(SUCCESS_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testInvalidUsername() {
        boolean result = login.loginUser(INVALID_USERNAME, VALID_PASSWORD);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testInvalidPassword() {
        boolean result = login.loginUser(VALID_USERNAME, INVALID_PASSWORD);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testBothInvalidCredentials() {
        boolean result = login.loginUser(INVALID_USERNAME, INVALID_PASSWORD);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testNullUsername() {
        boolean result = login.loginUser(null, VALID_PASSWORD);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testNullPassword() {
        boolean result = login.loginUser(VALID_USERNAME, null);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testBothNullCredentials() {
        boolean result = login.loginUser(null, null);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testEmptyUsername() {
        boolean result = login.loginUser("", VALID_PASSWORD);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testEmptyPassword() {
        boolean result = login.loginUser(VALID_USERNAME, "");
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testCaseSensitiveUsername() {
        boolean result = login.loginUser(VALID_USERNAME.toUpperCase(), VALID_PASSWORD);
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testCaseSensitivePassword() {
        boolean result = login.loginUser(VALID_USERNAME, VALID_PASSWORD.toLowerCase());
        assertFalse(result);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());
    }

    @Test
    void testMultipleLoginAttempts() {
        // First attempt: invalid
        boolean result1 = login.loginUser(INVALID_USERNAME, INVALID_PASSWORD);
        assertFalse(result1);
        assertEquals(FAILED_LOGIN_MESSAGE, login.returnLoginStatus());

        // Second attempt: valid
        boolean result2 = login.loginUser(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(result2);
        assertEquals(SUCCESS_LOGIN_MESSAGE, login.returnLoginStatus());
    }
}