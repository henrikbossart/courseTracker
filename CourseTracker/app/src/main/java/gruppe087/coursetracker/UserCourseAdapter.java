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
        newValues.put("courseID", courseID);

        db.insert("usercourse", null, newValues);
    }

    public int deleteEntry(String username, String courseID){
        String where = "username=? AND courseID =?";
        int numberOfEntriesDeleted = db.delete("usercourse", where, new String[]{username,courseID});
        return numberOfEntriesDeleted;
    }

    public ArrayList<String> getSingleEntry(String username, String courseID) {
        Cursor cursor = db.query("usercourse", null, "username=? AND courseID=?", new String[]{username, courseID}, null, null, null);
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            Toast.makeText(context, "There is no course with this key pair.", Toast.LENGTH_LONG).show();
            return null;
        }


        cursor.moveToFirst();
        ArrayList<String> row = new ArrayList<String>();
        row.add(username);
        row.add(courseID);
        return row;
    }

    public void updateEntry(String username, String courseID){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put("username", username);
        updatedValues.put("courseID", courseID);

        String where="username=? AND courseID=?";
        db.update("usercourse", updatedValues, where, new String[]{username, courseID});

    }


}
