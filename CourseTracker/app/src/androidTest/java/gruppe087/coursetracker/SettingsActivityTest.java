package gruppe087.coursetracker;

import android.os.SystemClock;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by petercbu on 26.04.2017.
 */

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {


    @Rule
    public ActivityTestRule<SettingsActivity> mActivityActivityTestRule =
            new ActivityTestRule<SettingsActivity>(SettingsActivity.class);

    @Test
    public void GeneralOpens(){
        onView(withText("General"))
        .perform(click());
        SystemClock.sleep(200);
    }

    @Test
    public void NotificationsOpens(){
        onView(withText("Notifications"))
                .perform(click());
        SystemClock.sleep(200);
    }

    @Test
    public void DataAndSyncOpens(){
        onView(withText("Data & sync"))
                .perform(click());
        SystemClock.sleep(200);
    }

    @Test
    public void UserOpens(){
        onView(withText("User"))
                .perform(click());
        SystemClock.sleep(200);
    }

}
