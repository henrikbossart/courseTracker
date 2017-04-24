package gruppe087.coursetracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by malfridhendenaaraas on 20.03.2017.
 */
public class SignupActivityTest {

    SignupActivity signupTest = new SignupActivity();

    /*
    to String
    String usrname = signupTest.editTextUserName.getText().toString();
    String password = signupTest.editTextPassword.getText().toString();
    String confirmPassword = signupTest.editTextConfirmPassword.getText().toString();
    */

    @Test
    public void onCreate() throws Exception {
        //dummy input
        String username = "Kari";
        String password = "Hemmelig";
        String confirmPasswordCorrect = "Hemmelig";
        String confirmPasswordIncorrect = "Hemelig";

        SignupActivity signupTest = new SignupActivity();

        assertEquals(password, confirmPasswordCorrect);
        assertNotEquals(password, confirmPasswordIncorrect);
        assertTrue(username != null);
        assertTrue(password != null);
        assertTrue(confirmPasswordCorrect != null);
    }

}