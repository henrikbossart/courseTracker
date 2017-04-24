package gruppe087.coursetracker;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by henrikbossart on 24.04.2017.
 */

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> activityTestRule =
        new ActivityTestRule<SignupActivity>(SignupActivity.class);

    @Test
    public void clickSignUpButton() {
        String username = "test";
        String password = "test";

        onView(withId(R.id.editTextUserName)).perform(typeText(username), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword)).perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.editTextConfirmPassword)).perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.buttonCreateAccount)).perform(click());

        //onView(withId(R.id.header)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_chooseCourses)))));

    }

}
