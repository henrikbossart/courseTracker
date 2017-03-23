package gruppe087.coursetracker;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by petercbu on 23.03.2017.
 */

public class Toolbox {


    public Long SQLdateAndTimeToTimestamp(String date, String time){
        Timestamp timestamp = Timestamp.valueOf(date + " " + time + ":00.000000000");
        return timestamp.getTime();
    }

}
