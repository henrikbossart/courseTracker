package gruppe087.coursetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.filters.LargeTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;
import android.view.View;

import junit.framework.TestCase;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by petercbu on 25.04.2017.
 */

@RunWith(AndroidJUnit4.class)
public class MissedFragmentTest {

    final static Context mMockContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
    final static SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mMockContext);
    final static String username = "default";
    final static String COURSEID = "TDT4100";
    final static String DATE = "2017-04-26";
    final static String TIME = "08:15:00";
    final static String ROOM = "R7";
    final static String COURSENAME = "Object Oriented Programming";

    @BeforeClass
    public static void setUp() {

        SharedPreferences.Editor editor = settings.edit();

        editor.putString("username", "default");
        editor.commit();
        mMockContext.deleteDatabase("userdb_test.sqlite");
        System.out.println("Mock" + mMockContext.toString());

        LectureAdapter lectureAdapter = new LectureAdapter(mMockContext);
        CourseAdapter courseAdapter = new CourseAdapter(mMockContext);
        LoginDataBaseAdapter loginDataBaseAdapter = new LoginDataBaseAdapter(mMockContext);
        UserCourseAdapter userCourseAdapter = new UserCourseAdapter(mMockContext);
        UserLectureAdapter userLectureAdapter = new UserLectureAdapter(mMockContext, "default");

        courseAdapter.open();
        courseAdapter.insertEntry(COURSEID, COURSENAME, ROOM, "2017-06-07");
        courseAdapter.close();

        loginDataBaseAdapter.open();
        loginDataBaseAdapter.insertEntry("default", "default");
        loginDataBaseAdapter.close();

        lectureAdapter.open();
        lectureAdapter.insertEntry(COURSEID, DATE, TIME, ROOM);
        lectureAdapter.insertEntry("TDT4140", "2017-04-26", "10:15:00", "R1");
        int lectureID = lectureAdapter.getLectureID(COURSEID, DATE, TIME, ROOM);
        lectureAdapter.close();

        userCourseAdapter.open();
        userCourseAdapter.insertEntry("default", COURSEID);
        userCourseAdapter.close();

        userLectureAdapter.open();
        userLectureAdapter.insertEntry(lectureID, 0, 0);
        userLectureAdapter.close();
    }

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickLectureNotAttended() throws Exception {
        Thread.sleep(200);
        onView(withResourceName("message"))
                .perform(pressBack());
        onView(withId(R.id.agenda_lv))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.pager))
                .perform(swipeLeft());
        SystemClock.sleep(800);
        onView(withId(R.id.missed_lv))
                .check(matches(isCompletelyDisplayed()));
        onData(anything())
                .inAdapterView(withId(R.id.missed_lv))
                .atPosition(0)
                .check(matches(isCompletelyDisplayed()));
        onData(anything())
                .inAdapterView(withId(R.id.missed_lv))
                .atPosition(0)
                .perform(click());

    }


}
