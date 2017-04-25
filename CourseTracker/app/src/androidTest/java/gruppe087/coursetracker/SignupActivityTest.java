package gruppe087.coursetracker;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by henrikbossart on 24.04.2017.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class SignupActivityTest extends AndroidTestCase{

    @Rule
    public ActivityTestRule<SignupActivity> activityTestRule =
        new ActivityTestRule<SignupActivity>(SignupActivity.class);


    @Test
    public void testTest() throws Exception{
        SignupActivity activity = activityTestRule.getActivity();
        EditText username       = (EditText) activity.findViewById(R.id.editTextUserName);
        assertThat(username, instanceOf(EditText.class));
        assertThat(username, notNullValue());
    }
    @Test
    public void clickSignUpButton() {
        String username = "test2";
        String password = "test2";

        onView(withId(R.id.editTextUserName)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.editTextConfirmPassword)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.buttonCreateAccount)).perform(click());
        onView(withId(R.id.header)).check(matches(allOf(isDescendantOfA(withId(R.id.layout_chooseCourses)))));

    }
}
