package gruppe087.coursetracker;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;


/**
 * Created by petercbu on 24.04.2017.
 */

@RunWith(AndroidJUnit4.class)
public class SelectionChooseCoursesAtSetupTest {

    @Rule
    public ActivityTestRule<ChooseCourseAtSetupActivity> mChooseCourseAtSetupActivityActivityTestRule =
            new ActivityTestRule<ChooseCourseAtSetupActivity>(ChooseCourseAtSetupActivity.class);

    @Test
    public void clickListElement_togglesGreyAndWhite() throws Exception {
        onData(anything())
                .inAdapterView(withId(R.id.initlv))
                .atPosition(0)
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.initlv))
                .atPosition(0)
                .check(matches(withBackgroundColor(R.color.gray)));
        onData(anything())
                .inAdapterView(withId(R.id.initlv))
                .atPosition(0)
                .perform(click());
        onData(anything())
                .inAdapterView(withId(R.id.initlv))
                .atPosition(0)
                .check(matches(withBackgroundColor(R.color.white)));
    }

    public static Matcher<View> withBackgroundColor(final int color) {
        return new BoundedMatcher<View, LinearLayout>(LinearLayout.class) {
            @Override
            protected boolean matchesSafely(LinearLayout row) {
                ColorDrawable colorDrawable = new ColorDrawable(InstrumentationRegistry.getTargetContext().getResources().getColor(color));
                int assertColor = colorDrawable.getColor();
                return assertColor == ((ColorDrawable) row.getBackground()).getColor();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: ");
            }
        };
    }


}
