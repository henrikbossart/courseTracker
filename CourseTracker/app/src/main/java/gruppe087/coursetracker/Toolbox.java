package gruppe087.coursetracker;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by petercbu on 23.03.2017.
 */

public abstract class Toolbox {


    public Long SQLdateAndTimeToTimestamp(String date, String time){
        Timestamp timestamp = Timestamp.valueOf(date + " " + time + ":00.000000000");
        return timestamp.getTime();
    }

    public static int timeToInt(String time){
        return Integer.parseInt(time.split(":")[0])*60 + Integer.parseInt(time.split(":")[1]);
    }

}
