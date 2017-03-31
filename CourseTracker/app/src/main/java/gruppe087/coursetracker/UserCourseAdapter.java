package gruppe087.coursetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by petercbu on 20.03.2017.
 */

public class UserCourseAdapter extends DataBaseAdapter {

    static final String ADD_USER_COURSE_TABLE =
            "CREATE TABLE usercourse("+
                    "username 	TEXT NOT NULL,"+
                    "courseid 	TEXT NOT NULL," +
                    "PRIMARY KEY(username, courseid)" +
                    "); ";

    public UserCourseAdapter(Context _context) {
        super(_context);
    }

    public  UserCourseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertEntry(String username, String courseID){
        ContentValues newValues = new ContentValues();

        newValues.put("username", username);
        newValues.put("courseid", courseID);

        db.insert("usercourse", null, newValues);
    }

    public int deleteEntry(String username, String courseID){
        String where = "username=? AND courseid=?";
        return db.delete("usercourse", where, new String[]{username,courseID});

    }

    public ArrayList<String> getSingleEntry(String username, String courseID) {
        Cursor cursor = db.query("usercourse", null, "username=? AND courseid=?", new String[]{username, courseID}, null, null, null);
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            //Toast.makeText(context, "There is no course with this key pair.", Toast.LENGTH_LONG).show();
            return null;
        }


        cursor.moveToFirst();
        ArrayList<String> row = new ArrayList<String>();
        row.add(username);
        row.add(courseID);
        return row;
    }

    public ArrayList<String> getCoursesForUser(String username){
        ArrayList<String> courses = new ArrayList<String>();
        Cursor cursor = db.query("usercourse", null, "username=?", new String[]{username}, null, null, null);
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            //Toast.makeText(context, "There is no course with this key pair.", Toast.LENGTH_LONG).show();
            return null;
        }
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            courses.add(cursor.getString(cursor.getColumnIndex("courseid")));
            cursor.moveToNext();
        }
        return courses;
    }

    public void updateEntry(String username, String courseID){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put("username", username);
        updatedValues.put("courseid", courseID);

        String where="username=? AND courseid=?";
        db.update("usercourse", updatedValues, where, new String[]{username, courseID});

    }


}
