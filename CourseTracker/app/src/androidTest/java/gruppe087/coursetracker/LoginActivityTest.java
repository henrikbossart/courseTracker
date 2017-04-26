/*package gruppe087.coursetracker;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void clickLoginButton_LoginToOverview() {

        String username = "1";
        String password = "1";

        onView(withId(R.id.editTextUserNameToLogin)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.editTextPasswordToLogin)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.buttonSignIn)).perform(click());

        onView(withId(R.id.agenda_lv)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_overview)), isDisplayed())));

    }

}

*/