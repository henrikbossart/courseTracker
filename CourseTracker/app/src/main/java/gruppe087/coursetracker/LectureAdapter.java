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
            "CREATE TABLE lecture(" +
                    "courseID 	TEXT NOT NULL, " +
                    "time  		TEXT NOT NULL, " +
                    "date		TEXT NOT NULL, " +
                    "room 		TEXT NOT NULL, " +
                    "PRIMARY KEY(courseid, time, date, room)" +
                    ");";

    public LectureAdapter(Context _context) {
        super(_context);
    }

    public  LectureAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void insertEntry(String courseID, String date, String time, String room){
        ContentValues newValues = new ContentValues();
        if (getSingleEntry(courseID, time, date) != null){
            return;
        }
        newValues.put("courseID", courseID);
        newValues.put("date", date); //YYYY-mm-dd
        newValues.put("time", time); //HH:mm:ss
        newValues.put("room", room);

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
        Cursor cursor = db.query("lecture", null, "courseID=? AND time=? AND date=?",
                new String[]{courseID, time, date}, null, null, null);
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        ArrayList<String> row = new ArrayList<String>();
        row.add(courseID);
        row.add(date);
        row.add(time);
        row.add(cursor.getString(cursor.getColumnIndex("room")));
        return row;
    }

    public int getLectureID(String courseID, String date, String time, String room){
        Cursor cursor = db.query("lecture", new String[]{"ROWID"}, "courseID=? AND time=? AND date=? AND room=?",
                new String[]{courseID, time, date, room}, null, null, null);
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            return -1;
        }
        cursor.moveToFirst();

        return cursor.getInt(0);

    }

    public void updateEntry(String courseID, String time, String date){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put("courseID", courseID);
        updatedValues.put("time", time);
        updatedValues.put("date", date);

        String where="courseID=? AND time=? AND date=?";
        db.update("lecture", updatedValues, where, new String[]{courseID, time, date});

    }







}
