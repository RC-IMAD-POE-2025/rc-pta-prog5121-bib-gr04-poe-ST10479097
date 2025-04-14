package quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    private Registration registration;

    // Expected messages
    private static final String SUCCESS_REGISTRATION_MESSAGE = "Your Registration was successful, you can login now";
    private static final String FAILED_REGISTRATION_MESSAGE = "Your Registration was denied, please try again";
    private static final String USERNAME_SUCCESS_MESSAGE = "Username successfully captured";
    private static final String PASSWORD_SUCCESS_MESSAGE = "Password successfully captured";
    private static final String CELL_PHONE_SUCCESS_MESSAGE = "Cellphone number successfully captured";
    private static final String FIRST_NAME_SUCCESS_MESSAGE = "First name successfully captured";
    private static final String LAST_NAME_SUCCESS_MESSAGE = "Last name successfully captured";
    private static final String USERNAME_ERROR_MESSAGE = "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
    private static final String PASSWORD_ERROR_MESSAGE = "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    private static final String CELL_PHONE_ERROR_MESSAGE = "Cellphone number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
    private static final String FIRST_NAME_ERROR_MESSAGE = "First name is invalid.";
    private static final String LAST_NAME_ERROR_MESSAGE = "Last name is invalid.";

    // Test data
    private static final String VALID_USERNAME = "kyl_1";
    private static final String VALID_PASSWORD = "Ch&&sec@ke99!";
    private static final String VALID_CELL_PHONE_NUMBER = "+27838968976";
    private static final String VALID_FIRST_NAME = "Kyle";
    private static final String VALID_LAST_NAME = "Smith";

    // Invalid test data
    private static final String INVALID_USERNAME = "kyle!!!!!!!";
    private static final String USERNAME_WITHOUT_UNDERSCORE = "kyl1";
    private static final String INVALID_PASSWORD = "password";
    private static final String INVALID_CELL_PHONE_NUMBER = "123456789";
    private static final String INVALID_FIRST_NAME = "";
    private static final String INVALID_LAST_NAME = "";

    @BeforeEach
    void setUp() {
        registration = new Registration();
    }

    @Test
    void testSuccessfulRegistration() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(USERNAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(PASSWORD_SUCCESS_MESSAGE));
        assertTrue(result.contains(CELL_PHONE_SUCCESS_MESSAGE));
        assertTrue(result.contains(FIRST_NAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(LAST_NAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(SUCCESS_REGISTRATION_MESSAGE));
        
        assertEquals(VALID_USERNAME, registration.getUsername());
        assertEquals(VALID_PASSWORD, registration.getPassword());
        assertEquals(VALID_CELL_PHONE_NUMBER, registration.getCellphone());
        assertEquals(VALID_FIRST_NAME, registration.getFirstName());
        assertEquals(VALID_LAST_NAME, registration.getLastName());
    }

    @Test
    void testInvalidUsername() {
        String result = registration.registerUser(INVALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(USERNAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getUsername());
    }

    @Test
    void testUsernameWithoutUnderscore() {
        String result = registration.registerUser(USERNAME_WITHOUT_UNDERSCORE, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(USERNAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getUsername());
    }

    @Test
    void testInvalidPassword() {
        String result = registration.registerUser(VALID_USERNAME, INVALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(PASSWORD_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getPassword());
    }

    @Test
    void testInvalidCellPhoneNumber() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, INVALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(CELL_PHONE_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getCellphone());
    }

    @Test
    void testInvalidFirstName() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, INVALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(FIRST_NAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getFirstName());
    }

    @Test
    void testInvalidLastName() {
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, INVALID_LAST_NAME);
        
        assertTrue(result.contains(LAST_NAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getLastName());
    }

    @Test
    void testNullInputs() {
        String result = registration.registerUser(null, null, null, null, null);
        
        assertTrue(result.contains(USERNAME_ERROR_MESSAGE));
        assertTrue(result.contains(PASSWORD_ERROR_MESSAGE));
        assertTrue(result.contains(CELL_PHONE_ERROR_MESSAGE));
        assertTrue(result.contains(FIRST_NAME_ERROR_MESSAGE));
        assertTrue(result.contains(LAST_NAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        
        assertNull(registration.getUsername());
        assertNull(registration.getPassword());
        assertNull(registration.getCellphone());
        assertNull(registration.getFirstName());
        assertNull(registration.getLastName());
    }

    @Test
    void testPartialInvalidInputs() {
        String result = registration.registerUser(INVALID_USERNAME, INVALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(USERNAME_ERROR_MESSAGE));
        assertTrue(result.contains(PASSWORD_ERROR_MESSAGE));
        assertTrue(result.contains(CELL_PHONE_SUCCESS_MESSAGE));
        assertTrue(result.contains(FIRST_NAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(LAST_NAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        
        assertNull(registration.getUsername());
        assertNull(registration.getPassword());
    }

    // Edge case tests
    @Test
    void testMinimumLengthUsername() {
        String username = "a_1"; // 3 chars, contains underscore
        String result = registration.registerUser(username, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(USERNAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(SUCCESS_REGISTRATION_MESSAGE));
        assertEquals(username, registration.getUsername());
    }

    @Test
    void testMaximumLengthUsername() {
        String username = "abc_d"; // Exactly 5 chars
        String result = registration.registerUser(username, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(USERNAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(SUCCESS_REGISTRATION_MESSAGE));
        assertEquals(username, registration.getUsername());
    }

    @Test
    void testUsernameJustOverMaxLength() {
        String username = "abc_de"; // 6 chars
        String result = registration.registerUser(username, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(USERNAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getUsername());
    }

    @Test
    void testMinimumPasswordLength() {
        String password = "A1@aabbcc"; // Exactly 8 chars, meets all criteria
        String result = registration.registerUser(VALID_USERNAME, password, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(PASSWORD_SUCCESS_MESSAGE));
        assertTrue(result.contains(SUCCESS_REGISTRATION_MESSAGE));
        assertEquals(password, registration.getPassword());
    }

    @Test
    void testPasswordMissingOneCriteria() {
        String password = "A1aabbcc"; // 8 chars, missing special char
        String result = registration.registerUser(VALID_USERNAME, password, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(PASSWORD_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getPassword());
    }

    @Test
    void testCellPhoneNumberWithDifferentValidFormat() {
        String cellPhone = "+27000000000"; // Valid +27 with all zeros
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, cellPhone, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(CELL_PHONE_SUCCESS_MESSAGE));
        assertTrue(result.contains(SUCCESS_REGISTRATION_MESSAGE));
        assertEquals(cellPhone, registration.getCellphone());
    }

    @Test
    void testCellPhoneNumberJustInvalid() {
        String cellPhone = "+2783896897"; // Only 10 digits after +27
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, cellPhone, VALID_FIRST_NAME, VALID_LAST_NAME);
        
        assertTrue(result.contains(CELL_PHONE_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getCellphone());
    }

    @Test
    void testSingleLetterFirstName() {
        String firstName = "A"; // Single letter
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, firstName, VALID_LAST_NAME);
        
        assertTrue(result.contains(FIRST_NAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(SUCCESS_REGISTRATION_MESSAGE));
        assertEquals(firstName, registration.getFirstName());
    }

    @Test
    void testFirstNameWithWhitespace() {
        String firstName = " Kyle "; // Whitespace around
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, firstName, VALID_LAST_NAME);
        
        assertTrue(result.contains(FIRST_NAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getFirstName());
    }

    @Test
    void testFirstNameWithNumbers() {
        String firstName = "Kyle123"; // Contains numbers
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, firstName, VALID_LAST_NAME);
        
        assertTrue(result.contains(FIRST_NAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getFirstName());
    }

    @Test
    void testSingleLetterLastName() {
        String lastName = "B"; // Single letter
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, lastName);
        
        assertTrue(result.contains(LAST_NAME_SUCCESS_MESSAGE));
        assertTrue(result.contains(SUCCESS_REGISTRATION_MESSAGE));
        assertEquals(lastName, registration.getLastName());
    }

    @Test
    void testLastNameWithSpecialCharacters() {
        String lastName = "Smith!"; // Contains special char
        String result = registration.registerUser(VALID_USERNAME, VALID_PASSWORD, VALID_CELL_PHONE_NUMBER, VALID_FIRST_NAME, lastName);
        
        assertTrue(result.contains(LAST_NAME_ERROR_MESSAGE));
        assertTrue(result.contains(FAILED_REGISTRATION_MESSAGE));
        assertNull(registration.getLastName());
    }
}