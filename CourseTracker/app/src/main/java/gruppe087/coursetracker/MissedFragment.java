package gruppe087.coursetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;


public class MissedFragment extends Fragment {

    ListView missedListView;
    ArrayList<String> listItems;
    HttpGetRequest getRequest;
    UserCourseAdapter userCourseAdapter;
    SharedPreferences settings;
    public static final String PREFS_NAME = "CTPrefs";
    MissedListAdapter<String> arrayAdapter;
    View rootView;
    LectureAdapter lectureAdapter;
    UserLectureAdapter userLectureAdapter;
    private Boolean active;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_missed, container, false);
        missedListView = (ListView) rootView.findViewById(R.id.missed_lv);
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        initList();
        active = true;
        missedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), MissedLectureCurriculum.class);
                //Optional parameters: myIntent.putExtra("key", value);
                getActivity().startActivity(myIntent);
            }
        });
        return rootView;
    }

    private ArrayList<String> createMissedList(){
        userCourseAdapter = new UserCourseAdapter(getContext());
        userCourseAdapter.open();
        ArrayList<String> returnList = new ArrayList<String>();
        ArrayList<String> courses = userCourseAdapter.getCoursesForUser(settings.getString("username", "default"));
        TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();
        lectureAdapter = new LectureAdapter(getContext());
        userLectureAdapter = new UserLectureAdapter(getContext(), settings.getString("username", "default"));
        lectureAdapter.open();
        userLectureAdapter.open();
        Date dNow = new Date();
        DateFormat dateValueFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String thisTime = timeFormat.format(dNow);
        int timeValueNow = Toolbox.timeToInt(thisTime);
        thisTime = thisTime + ":00";

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
                    final String courseID2 = courseID;
                    final String courseName = jsonObject.getString("courseName");
                    final String time = jsonObject.getString("time");
                    final String room = jsonObject.getString("room");
                    final String date = jsonObject.getString("date");
                    String row = courseID + "\n" + courseName + "\nTid:\t" + time + "\nRom:\t" + room;
                    Integer hour = Integer.parseInt(time.split(":")[0]);
                    int timeValueLecture = Toolbox.timeToInt(time);
                    String time2 = time + ":00";
                    final int lectureID = lectureAdapter.getLectureID(courseID, date, time2, room);

                    //timeValueLecture = timeValueLecture + 90;
                    if (timeValueNow > timeValueLecture && !userLectureAdapter.isAsked(lectureID)){
                        //lectureAdapter.setMissed(courseID, time, date,  0);

                        // Create notification if window is not active
                        if (this.active = false) {
                            NotificationBuilder notification = new NotificationBuilder(getActivity());
                            notification.Build(getActivity(), "Attendance", "Did you attend the class in " + courseID + "?");
                        }

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Attendance")
                                .setMessage("Did you attend the class in " + courseID + "\nat" + time + "?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // set the lecture to attended
                                        userLectureAdapter.setMissed(lectureID, 0);
                                        userLectureAdapter.setAsked(lectureID, 1);
                                        updateList();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // set the lecture to be missed
                                        userLectureAdapter.setMissed(lectureID, 1);
                                        userLectureAdapter.setAsked(lectureID, 1);
                                        updateList();
                                    }
                                })
                                .show();
                    }
                    boolean missed = userLectureAdapter.isMissed(lectureID);

                    if (missed){
                        sortMap.put(hour,row);
                    }
                }
            } catch (JSONException e) {
            }

        }

        for (Map.Entry<Integer, String> entry : sortMap.entrySet()){
            returnList.add(entry.getValue());
        }


        return returnList;
    }

    public boolean lectureMissed(String courseID, String time, String date, String room){
        lectureAdapter = new LectureAdapter(getActivity().getApplicationContext());
        userLectureAdapter = new UserLectureAdapter(getActivity().getApplicationContext(), settings.getString("username", "default"));
        lectureAdapter.open();
        userLectureAdapter.open();
        time = time + ":00";
        return userLectureAdapter.isMissed(lectureAdapter.getLectureID(courseID, date, time, room));
    }

    private void updateList(){
        Date dNow = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(dNow);

        for (int i = 0; i < listItems.size(); i++){
            String courseId = listItems.get(i).split("\n|\t")[0];
            String time = listItems.get(i).split("\n|\t")[3];
            String room = listItems.get(i).split("\n|\t")[5];
            Boolean missed = lectureMissed(courseId, time, date, room);
            if (!missed){
                listItems.remove(i);
            }

        }

        arrayAdapter.notifyDataSetChanged();
    }

    private void initList(){

        listItems = createMissedList();

        // Initializing getRequest class
        Date dNow = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(dNow);
        for (int i = 0; i < listItems.size(); i++){
            String courseId = listItems.get(i).split("\n|\t")[0];
            String time = listItems.get(i).split("\n|\t")[3];
            String room = listItems.get(i).split("\n|\t")[5];
            Boolean missed = lectureMissed(courseId, time, date, room);
            if (!missed){
                listItems.remove(i);
            }

        }

        // Create a List from String Array elements
        arrayAdapter = new MissedListAdapter<String>(getActivity().getApplicationContext(), R.layout.listitem, R.id.textview, listItems);
        // DataBind ListView with items from SelectListAdapter
        missedListView.setAdapter(arrayAdapter);
        updateList();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

        active = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        active = false;
    }

    @Override
    public void onStop() {
        super.onStop();

        active = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayAdapter.notifyDataSetChanged();

        active = true;
    }
}
