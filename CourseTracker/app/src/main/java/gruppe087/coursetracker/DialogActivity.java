package gruppe087.coursetracker;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DialogActivity extends AppCompatActivity {

    UserLectureAdapter userLectureAdapter;
    SharedPreferences settings;
    Date dNow;
    DateFormat timeFormat;
    DateFormat dateFormat;
    int timeValueLecture;
    int lectureID;
    String courseID;
    String time;
    ArrayList<String> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listItems = savedInstanceState.getStringArrayList("listItems");
        timeValueLecture = savedInstanceState.getInt("timeValueLecture");
        lectureID = savedInstanceState.getInt("lectureID");
        courseID = savedInstanceState.getString("courseID");
        time = savedInstanceState.getString("time");
        dNow = new Date();
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        setContentView(R.layout.activity_dialog);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        settings = getSharedPreferences(Toolbox.SETTINGS_FILE, 0);
        userLectureAdapter = new UserLectureAdapter(getApplicationContext(), settings.getString("username", "default"));

    }





}
