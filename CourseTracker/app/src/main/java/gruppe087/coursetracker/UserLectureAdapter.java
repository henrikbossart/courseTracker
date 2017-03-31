package gruppe087.coursetracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by petercbu on 31.03.2017.
 */

public class UserLectureAdapter extends DataBaseAdapter {


    private String username;

    static final String ADD_USER_LECTURE_TABLE =
            "CREATE TABLE userlecture("+
                    "username 	TEXT    NOT NULL, " +
                    "lectureID  INT     NOT NULL, " +
                    "missed     INT     NOT NULL, " +
                    "asked      INT     NOT NULL, " +
                    "PRIMARY KEY(username, lectureID)" +
                    ");";

    public UserLectureAdapter(Context _context, String username) {
        super(_context);
        this.username = username;
    }

    public  UserLectureAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertEntry(int lectureID, Integer missed, Integer asked){
        ContentValues newValues = new ContentValues();
        if (entryExists(lectureID)){
            return;
        }
        newValues.put("username", username);
        newValues.put("lectureID", lectureID);
        newValues.put("missed", missed);
        newValues.put("asked", asked);

        try {
            db.insert("userlecture", null, newValues);
        } catch (SQLiteConstraintException e){

        }

    }

    public int deleteEntry(int lectureID){

        String where = "username=? AND lectureID=?";
        return db.delete("userlecture", where, new String[]{username, Integer.toString(lectureID)});
    }


    public boolean entryExists(int lectureID){
        Cursor cursor = db.query("userlecture", null, "username=? AND lectureID=?",
                new String[]{username, Integer.toString(lectureID)}, null, null, null);

        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            //Toast.makeText(context, "There is no course with this key pair.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public HashMap<String, String> getSingleEntry(int lectureID){
        System.out.println(username + " " + lectureID);
        Cursor cursor = db.query("userlecture", null, "username=? AND lectureID=?",
                new String[]{username, Integer.toString(lectureID)}, null, null, null);

        HashMap<String, String> row = new HashMap<String, String>();
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            //Toast.makeText(context, "There is no course with this key pair.", Toast.LENGTH_LONG).show();
            return null;
        }
        cursor.moveToFirst();
        row.put("username", username);
        row.put("lectureID", Integer.toString(lectureID));
        row.put("asked", cursor.getString(cursor.getColumnIndex("asked")));
        row.put("missed", cursor.getString(cursor.getColumnIndex("missed")));

        return row;
    }

    public boolean isAsked(int lectureID){

        HashMap<String, String> row = getSingleEntry(lectureID);
        String result = row.get("asked");
        if (Integer.parseInt(result) > 0) {
            return true;
        }
        return false;
    }

    public boolean isMissed(int lectureID){
        HashMap<String, String> row = getSingleEntry(lectureID);
        String result = row.get("missed");
        if (Integer.parseInt(result) > 0) {
            return true;
        }
        return false;
    }

    public void setMissed(int lectureID, Integer missed){

        ContentValues updatedValues = new ContentValues();

        updatedValues.put("missed", missed);

        String where = "username=? AND lectureID=?";
        db.update("userlecture", updatedValues, where,
                new String[]{username, Integer.toString(lectureID)});
    }

    public void setAsked(int lectureID, Integer asked){
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("asked", asked);
        String where = "username=? AND lectureID=?";
        db.update("userlecture", updatedValues, where, new String[]{username, Integer.toString(lectureID)});

    }

}
