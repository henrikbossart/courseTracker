package gruppe087.coursetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by petercbu on 15.03.2017.
 */

public abstract class DataBaseAdapter {

    static final String DATABASE_NAME = "userdb.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.

    // Variable to hold the database instance
    public SQLiteDatabase db;


    // Context of the application using the database.
    protected final Context context;
    // Database open/upgrade helper
    protected DataBaseHelper dbHelper;
    public DataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  DataBaseAdapter open() throws SQLException
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

}
