package gruppe087.coursetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by petercbu on 15.03.2017.
 */

public class CourseAdapter extends DataBaseAdapter {

    static final String ADD_COURSES_TABLE = "create table "+"COURSE"+
            "( " +"COURSEID"+" text primary key not null,"+ "COURSENAME  text,LOCATION text,EXAMDATE text); ";


    public CourseAdapter(Context _context) {
        super(_context);
    }

    public CourseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void insertEntry(String courseID, String courseName, String location, String examDate){


        ContentValues newValues = new ContentValues();
        // Assign values for each row
        newValues.put("COURSEID", courseID);
        newValues.put("COURSENAME", courseName);
        newValues.put("LOCATION", location);
        newValues.put("EXAMDATE", examDate);

        Cursor cursor = null;
        String sql ="SELECT courseid FROM COURSE WHERE courseid='"+ courseID+ "'";
        cursor= db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            //courseID found
            return;
        }else{
            //courseID not found
            // Insert the row into your table
            db.insert("COURSE", null, newValues);
        }
        cursor.close();

    }

    // Key as argument
    public int deleteEntry(String courseID) {
        String where = "COURSEID=?";
        int numberOFEntriesDeleted = db.delete("COURSE", where, new String[]{courseID});
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public ArrayList<String> getSingleEntry(String courseID){
        Cursor cursor = db.query("COURSE", null, " COURSEID=?", new String[]{courseID}, null, null, null);
        if(cursor.getCount()<1){ // CourseID does not exist
            cursor.close();
            Toast.makeText(context, "There is no course with this course code.",Toast.LENGTH_LONG).show();
            return null;

        }

        cursor.moveToFirst();
        ArrayList<String> row = new ArrayList<String>();
        row.add(courseID);
        row.add(cursor.getString(cursor.getColumnIndex("COURSENAME")));
        row.add(cursor.getString(cursor.getColumnIndex("LOCATION")));
        row.add(cursor.getString(cursor.getColumnIndex("EXAMDATE")));
        cursor.close();
        return row;
    }

    public void updateEntry(String courseID, String courseName, String location, String examDate){
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("COURSEID", courseID);
        updatedValues.put("COURSENAME", courseName);
        updatedValues.put("LOCATION", location);
        updatedValues.put("EXAMDATE", examDate);

        String where="COURSEID = ?";
        db.update("COURSE",updatedValues, where, new String[]{courseID});
    }
}
