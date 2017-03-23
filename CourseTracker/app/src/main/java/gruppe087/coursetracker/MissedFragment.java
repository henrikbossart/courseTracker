package gruppe087.coursetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                    String courseName = jsonObject.getString("courseName");
                    String time = jsonObject.getString("time");
                    String room = jsonObject.getString("room");
                    String date = jsonObject.getString("date");
                    String row = courseID + "\n" + courseName + "\nTid:\t" + time + "\nRom:\t" + room;
                    Integer hour = Integer.parseInt(time.split(":")[0]);
                    int timeValueLecture = Toolbox.timeToInt(time);
                    timeValueLecture = timeValueLecture + 90;
                    if (timeValueNow < timeValueLecture){
                        lectureAdapter.setMissed(courseID, time, date,  0);
                    } else {
                        lectureAdapter.setMissed(courseID, time, date, 1);
                    }

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

    /*    // Tatt fra gamle Activity'en før vi gikk over til fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed_lectures);


    // Get reference of widgets from XML layout
    final ListView lv = (ListView) findViewById(R.id.lv);

    // Initializing a new String Array
    String[] missed
            = new String[] {
            "MMI, 12.15-14.00 (3/2/17)",
            "KTN, 09.15-11.00 (3/2/17)",
            "KTN, 12.15-14.00 (2/2/17)"
    };

    // Create a List from String Array elements
    final List<String> missed_list = new ArrayList<String>(Arrays.asList(missed));

    // Create an ArrayAdapter from List
    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
            (this, android.R.layout.simple_list_item_1, missed_list);

    // DataBind ListView with items from ArrayAdapter
    lv.setAdapter(arrayAdapter);
    arrayAdapter.notifyDataSetChanged();
    }*/

}
