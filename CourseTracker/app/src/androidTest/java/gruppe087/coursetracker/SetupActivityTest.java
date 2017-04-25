package gruppe087.coursetracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

/**
 * Created by malfridhendenaaraas on 20.03.2017.
 */
public class SetupActivityTest {

    @Rule
    public ActivityTestRule<SetupActivity> activityTestRule =
            new ActivityTestRule<SetupActivity>(SetupActivity.class);

    @Test
    public void clickSignUpButton_openSignUpScreen() {
        //Locate and click the sign-up button
        onView(withId(R.id.buttonSignUP)).perform(click());

        // Check if the screen popped up
        onView(withId(R.id.editTextUserName)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_signup)), isDisplayed())));
    }

    @Test
    public void clickLoginButton_openLoginScreen() {
        // Locate and press the sign-in button
        onView(withId(R.id.buttonSignIN)).perform(click());

        // Check that the sign-in page shows up
        onView(withId(R.id.editTextUserNameToLogin)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_login)), isDisplayed())));
    }
}