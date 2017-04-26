package gruppe087.coursetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/*
This file is required to handle all Database operations like (create DB, Insert record,
update record, Delete record, Close DB, and Cursor related stuffs.
 */

public class LoginDataBaseAdapter extends DataBaseAdapter {

    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE =   "create table LOGIN(" +
                                            "USERNAME   text    primary key not null," +
                                            "PASSWORD   text" +
                                            "); ";
    // Variable to hold the database instance
    // Context of the application using the database.
    // Database open/upgrade helper
    public LoginDataBaseAdapter(Context _context)
    {
        super(_context);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userName,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String userName)
    {
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{userName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }

   /* public ArrayList<String> getSingleEntry(String courseID){
        Cursor cursor = db.query("COURSE", null, " COURSEID=?", new String[]{courseID}, null, null, null);
        if(cursor.getCount()<1){ // CourseID does not exist
            cursor.close();
            Toast.makeText(context, "There is no course with this course code.",Toast.LENGTH_LONG).show();
            return null;

        }*/

    public boolean userExists(String username){
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{username}, null, null, null);
        if(cursor.getCount()<1) {
            cursor.close();
            return false;
        }
        return true;
    }
}
