package gruppe087.coursetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by petercbu on 21.03.2017.
 */

public class LectureAdapter extends DataBaseAdapter {

    static final String ADD_LECTURE_TABLE =
            "CREATE TABLE lecture("+
                    "courseid 	TEXT NOT NULL, " +
                    "time  		TEXT NOT NULL, " +
                    "date		TEXT NOT NULL, " +
                    "room 		TEXT NOT NULL, " +
                    "missed		INT  NOT NULL, " +
                    "asked      INT  NOT NULL, " +
                    "PRIMARY KEY(courseid, time, date, room)" +
                    ");";

    public LectureAdapter(Context _context) {
        super(_context);
    }

    public  LectureAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void insertEntry(String courseID, String date, String time, String room, String missed, String asked){
        ContentValues newValues = new ContentValues();
        if (getSingleEntry(courseID, time, date) != null){
            return;
        }

        newValues.put("courseID", courseID);
        newValues.put("date", date); //YYYY-mm-dd
        newValues.put("time", time); //HH:mm:ss
        newValues.put("room", room);
        newValues.put("missed", missed);
        newValues.put("asked", asked);

        try {
            db.insert("lecture", null, newValues);
        } catch (SQLiteConstraintException e){

        }

    }

    public int deleteEntry(String courseID, String time, String date){
        String where = "courseID=? AND time=? AND date=?";
        return db.delete("lecture", where, new String[]{courseID, time, date});
    }

    public ArrayList<String> getSingleEntry(String courseID, String time, String date){
        Cursor cursor = db.query("lecture", null, "courseID=? AND time=? AND date=?", new String[]{courseID, time, date}, null, null, null);
        System.out.println(cursor);
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            Toast.makeText(context, "There is no course with this key pair.", Toast.LENGTH_LONG).show();
            return null;
        }

        cursor.moveToFirst();
        ArrayList<String> row = new ArrayList<String>();
        row.add(courseID);
        row.add(date);
        row.add(time);
        row.add(cursor.getString(cursor.getColumnIndex("room")));
        row.add(cursor.getString(cursor.getColumnIndex("missed")));
        return row;
    }

    public void updateEntry(String courseID, String time, String date){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put("courseID", courseID);
        updatedValues.put("time", time);
        updatedValues.put("date", date);

        String where="courseID=? AND time=? AND date=?";
        db.update("lecture", updatedValues, where, new String[]{courseID, time, date});

    }

    public void setMissed(String courseID, String time, String date, Integer missed){

        ContentValues updatedValues = new ContentValues();

        updatedValues.put("missed", missed);
        time = time + ":00" ;

        String where = "courseID=? AND time=? AND date=?";
        db.update("lecture", updatedValues, where, new String[]{courseID, time,date});
        System.out.println(getSingleEntry(courseID, time, date));

    }

    public void setAsked(String courseID, String time, String date, Integer asked){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put("asked", asked);
        time = time + ":00";

        String where = "courseID=? AND time=? AND date=?";
        db.update("lecture", updatedValues, where, new String[]{courseID, time,date});

    }

}
