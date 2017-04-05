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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        agendaListView = (ListView) rootView.findViewById(R.id.agenda_lv);
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        lectureAdapter = new LectureAdapter(getContext());
        userLectureAdapter =
                new UserLectureAdapter(getContext(), settings.getString("username", "default"));
        initList();
        lectureAdapter.close();
        userLectureAdapter.close();
        userCourseAdapter.close();
        return rootView;
    }


    private ArrayList<String> createAgendaList(){
        userCourseAdapter = new UserCourseAdapter(getContext());
        userLectureAdapter.open();
        userCourseAdapter.open();
        lectureAdapter.open();
        ArrayList<String> returnList = new ArrayList<String>();
        ArrayList<String> courses = userCourseAdapter.getCoursesForUser(settings.getString("username", "default"));
        TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();
        Date dNow = new Date();
        String date = dateFormat.format(dNow);

        for (String courseID : courses){

            ArrayList<String> todayLectures = lectureAdapter.getLecturesForToday(courseID);
            System.out.println();
            if (todayLectures != null){
                for (String row : todayLectures){
                    Integer hour = Integer.parseInt(row.split("\n|\t")[3].substring(0,2));
                    sortMap.put(hour, row);

                    String time = (row.split("\t|\n")[3] + ":00").substring(0,8);
                    String room = row.split("\t|\n")[5];
                    int lectureID = lectureAdapter.getLectureID(courseID, date, time, room);
                    if (!userLectureAdapter.entryExists(lectureID)){
                        userLectureAdapter.insertEntry(lectureID, 0, 0);
                    }
                }
            } else {
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
        }

        for (Map.Entry<Integer, String> entry : sortMap.entrySet()){
            returnList.add(entry.getValue());
        }
        MainActivity.setListItems(returnList);
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
                String text = "Dette er pensum for " + courseID + " " + date + " klokken " + time.substring(0,5) +
                        " i " + room;
                lectureAdapter.addCurriculum(courseID, date, time, room, text);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
