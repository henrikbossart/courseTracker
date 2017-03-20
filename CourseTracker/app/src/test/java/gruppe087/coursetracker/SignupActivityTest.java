package gruppe087.coursetracker;

import android.os.Bundle;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by malfridhendenaaraas on 20.03.2017.
 */
public class SignupActivityTest {

    SignupActivity signupTest = new SignupActivity();

    //to String
    String usrname = signupTest.editTextUserName.getText().toString();
    String password = signupTest.editTextPassword.getText().toString();
    String confirmPassword = signupTest.editTextConfirmPassword.getText().toString();

    @Test
    public void onCreate() throws Exception {
        //sjekker at passordene matcher og feltene ikke er tomme
        //TODO: Implementere "liksom-input" for at try-blokka skal funke
        try {
            assertEquals(password, confirmPassword);
            assertTrue(usrname != null);
            assertTrue(password != null);
            assertTrue(confirmPassword != null);
        }
        catch (Exception e){
            System.err.println("Woops");
        }
    }

}