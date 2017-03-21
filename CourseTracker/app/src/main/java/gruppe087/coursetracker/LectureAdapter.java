package gruppe087.coursetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by petercbu on 21.03.2017.
 */

public class LectureAdapter extends DataBaseAdapter {
    public LectureAdapter(Context _context) {
        super(_context);
    }

    public  LectureAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void insertEntry(String courseID, String timestamp, String room, String missed){
        ContentValues newValues = new ContentValues();

        newValues.put("courseID", courseID);
        newValues.put("timestamp", timestamp);
        newValues.put("room", room);
        newValues.put("missed", missed);

        db.insert("lecture", null, newValues);
    }

    public int deleteEntry(String courseID, String timestamp){
        String where = "courseID=? AND timestamp=?";
        return db.delete("lecture", where, new String[]{courseID,timestamp});
    }

    public ArrayList<String> getSingleEntry(String courseID, String timestamp){
        Cursor cursor = db.query("lecture", null, "courseID=? AND timestamp=?", new String[]{courseID, timestamp}, null, null, null);
        if (cursor.getCount() < 1) { // Key does not exist
            cursor.close();
            Toast.makeText(context, "There is no course with this key pair.", Toast.LENGTH_LONG).show();
            return null;
        }


        cursor.moveToFirst();
        ArrayList<String> row = new ArrayList<String>();
        row.add(courseID);
        row.add(timestamp);
        row.add(cursor.getString(cursor.getColumnIndex("room")));
        row.add(cursor.getString(cursor.getColumnIndex("missed")));
        return row;
    }

    public void updateEntry(String courseID, String timestamp){
        ContentValues updatedValues = new ContentValues();

        updatedValues.put("courseID", courseID);
        updatedValues.put("timestamp", timestamp);

        String where="courseID=? AND timestamp=?";
        db.update("lecture", updatedValues, where, new String[]{courseID, timestamp});

    }

}
