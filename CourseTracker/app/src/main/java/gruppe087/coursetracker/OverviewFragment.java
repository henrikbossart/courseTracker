package gruppe087.coursetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;


public class OverviewFragment extends Fragment {
    ListView agendaListView;
    ArrayList<String> listItems;
    HttpGetRequest getRequest;
    UserCourseAdapter userCourseAdapter;
    SharedPreferences settings;
    public static final String PREFS_NAME = "CTPrefs";
    OverviewListAdapter<String> arrayAdapter;
    View rootView;
    LectureAdapter lectureAdapter;
    UserLectureAdapter userLectureAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        agendaListView = (ListView) rootView.findViewById(R.id.agenda_lv);
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        lectureAdapter = new LectureAdapter(getContext());
        userLectureAdapter =
                new UserLectureAdapter(getContext(), settings.getString("username", "default"));
        initList();
        return rootView;
    }


    private ArrayList<String> createAgendaList(){
        userCourseAdapter = new UserCourseAdapter(getContext());
        userCourseAdapter.open();
        ArrayList<String> returnList = new ArrayList<String>();
        ArrayList<String> courses = userCourseAdapter.getCoursesForUser(settings.getString("username", "default"));
        TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();

        for (String courseID : courses){
            String result;
            getRequest = new HttpGetRequest("getTodayLecturesForCourse.php");
            try {
                result = getRequest.execute("courseID", courseID).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                result=null;
            } catch (ExecutionException e) {
                e.printStackTrace();
                result=null;
            }

            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    courseID = jsonObject.getString("courseID");
                    String courseName = jsonObject.getString("courseName");
                    String time = jsonObject.getString("time");
                    String room = jsonObject.getString("room");
                    String row = courseID + "\n" + courseName + "\nTid:\t" + time + "\nRom:\t" + room;
                    Integer hour = Integer.parseInt(time.split(":")[0]);
                    sortMap.put(hour,row);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addLecturesToSQLite(courseID);




        }

        for (Map.Entry<Integer, String> entry : sortMap.entrySet()){
            returnList.add(entry.getValue());
        }

        return returnList;
    }

    private void addLecturesToSQLite(String courseID){
        String result;
        getRequest = new HttpGetRequest("getLecturesForCourse.php");
        try {
            result = getRequest.execute("courseID", courseID).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            result=null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            result=null;
        }
        lectureAdapter.open();
        userLectureAdapter.open();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                courseID = jsonObject.getString("courseID");
                String date = jsonObject.getString("date");
                String time = jsonObject.getString("time");
                String room = jsonObject.getString("room");
                lectureAdapter.insertEntry(courseID, date, time, room);
                int lectureID = lectureAdapter.getLectureID(courseID, date, time, room);
                userLectureAdapter.insertEntry(lectureID, 0, 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        lectureAdapter.close();
        userLectureAdapter.close();
    }


    private void initList(){

        listItems = createAgendaList();
        // Initializing getRequest class

        // Create a List from String Array elements
        arrayAdapter = new OverviewListAdapter<String>(getActivity().getApplicationContext(), R.layout.listitem, R.id.textview, listItems);
        // DataBind ListView with items from SelectListAdapter
        agendaListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }


}
