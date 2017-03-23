package gruppe087.coursetracker;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by henrikbossart on 20.03.2017.
 */
public class HttpConnectorTest {

    HttpConnector testClass = new HttpConnector("getCourses");


    @Test
    public  void commandChangerTest() {
        String testURL  = testClass.getUrl();
        String checkURL = "http://138.197.33.171/php/getCourses.php";
        assertEquals(checkURL, testURL);

        testClass.commandChanger("getLectures");
        testURL         = testClass.getUrl();
        checkURL        = "http://138.197.33.171/php/getLectures.php";
        assertEquals(checkURL, testURL);

        testClass.commandChanger("getCourse", "TDT4100");

        checkURL        = "http://138.197.33.171/php/getCourse.php?courseID=TDT4100";
        assertEquals(checkURL, testURL);
    }

}