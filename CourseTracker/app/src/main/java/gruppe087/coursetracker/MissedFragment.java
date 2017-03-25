package gruppe087.coursetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_missed, container, false);
        missedListView = (ListView) rootView.findViewById(R.id.missed_lv);
        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        initList();
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
        lectureAdapter.open();
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
                    //timeValueLecture = timeValueLecture + 90;
                    if (timeValueNow > timeValueLecture){
                        //lectureAdapter.setMissed(courseID, time, date,  0);


                        // Create notification if the
                        NotificationBuilder notification = new NotificationBuilder(getActivity());
                        notification.Build(getActivity(), "Attendance", "Did you attend the class in " + courseName + "?");

                        AlertDialog alert = new AlertDialog.Builder(getActivity())
                                .setTitle("Attendance")
                                .setMessage("Did you attend the class in " + courseName + "?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // set the lecture to attended
                                        lectureAdapter.setMissed(courseID2, time, date, 0);
                                        System.out.println(lectureAdapter.getSingleEntry(courseID2, time, date));
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // set the lecture to be missed
                                        lectureAdapter.setMissed(courseID2, time,date, 1);
                                        System.out.println(lectureAdapter.getSingleEntry(courseID2, time, date));
                                    }
                                })
                                .show();
                    } //else {
                        //lectureAdapter.setMissed(courseID, time, date, 1);
                    //}

                    if (lectureMissed(courseID, time, date)){
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

    public boolean lectureMissed(String courseID, String time, String date){
        lectureAdapter = new LectureAdapter(getActivity().getApplicationContext());
        lectureAdapter.open();
        time = time + ":00";
        ArrayList<String> lecture = lectureAdapter.getSingleEntry(courseID, time, date);
        int missed = Integer.parseInt(lecture.get(4));
        Boolean lectureMissed;
        if (missed < 1){
            lectureMissed = false;
        } else {
            lectureMissed = true;
        }
        return lectureMissed;
    }

    private void initList(){

        listItems = createMissedList();
        // Initializing getRequest class

        // Create a List from String Array elements
        arrayAdapter = new MissedListAdapter<String>(getActivity().getApplicationContext(), R.layout.listitem, R.id.textview, listItems);
        // DataBind ListView with items from SelectListAdapter
        missedListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

}
