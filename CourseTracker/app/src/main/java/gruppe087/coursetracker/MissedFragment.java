package gruppe087.coursetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
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
    private Boolean missed;
    Date dNow;
    DateFormat timeFormat;
    DateFormat dateFormat;
    Boolean elementRemoved = false;

    public void setMissed(Boolean missed){
        this.missed = missed;
    }

    public boolean getMissed(){
        return this.missed;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_missed, container, false);
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dNow = new Date();
        missedListView = (ListView) rootView.findViewById(R.id.missed_lv);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        active = true;
        missedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), MissedLectureCurriculum.class);
                String text = missedListView.getItemAtPosition(position).toString();
                String courseId = text.split("\n|\t")[0];

                String time = text.split("\n|\t")[3];
                time = time + ":00";
                time = time.substring(0,8);
                String date = dateFormat.format(dNow);
                updateCurriculum(courseId, date);


                String curriculum = lectureAdapter.getCurriculum(courseId, date, time);
                myIntent.putExtra("curriculum", curriculum);
                getActivity().startActivity(myIntent);
            }
        });
        listItems = createMissedList();
        arrayAdapter = new MissedListAdapter<String>(getActivity().getApplicationContext(), R.layout.listitem, R.id.textview, listItems);
        // DataBind ListView with items from SelectListAdapter
        missedListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        initListPrompt(0);
        //initList();
        return rootView;
    }


    private void updateCurriculum(String courseID, String date){
        String result;
        getRequest = new HttpGetRequest("getLecture.php");
        try {
            result = getRequest.execute("courseID", courseID, "date", date).get();
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
                String time = jsonObject.getString("time");
                String room = jsonObject.getString("room");
                String curriculum = jsonObject.getString("curriculum");
                time = time + ":00";
                lectureAdapter.addCurriculum(courseID, date, time, room, curriculum);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> createMissedList(){


        ArrayList<String> returnList = new ArrayList<String>();
        userCourseAdapter = new UserCourseAdapter(getContext());
        userCourseAdapter.open();
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
            boolean missed;
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

                    //timeValueLecture = timeValueLecture + 90;


                    sortMap.put(hour,row);


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
        time = time.substring(0,8);
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

    private void missedPrompt(int timeValueLecture, final int lectureID, String courseID, String time, final int index){
        String timeNow = timeFormat.format(dNow);
        int timeValueNow = Toolbox.timeToInt(timeNow);


        if (timeValueNow > timeValueLecture && !userLectureAdapter.isAsked(lectureID)){
            //lectureAdapter.setMissed(courseID, time, date,  0);

            new AlertDialog.Builder(getActivity())
                    .setTitle("Attendance")
                    .setMessage("Did you attend the class in " + courseID + "\nat " + time.substring(0,5) + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // set the lecture to attended
                            userLectureAdapter.setMissed(lectureID, 0);
                            userLectureAdapter.setAsked(lectureID, 1);
                            listItems.remove(index);
                            elementRemoved = true;
                            arrayAdapter.notifyDataSetChanged();
                            arrayAdapter.notifyDataSetInvalidated();
                            if (index < listItems.size()){
                                initListPrompt(index);
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // set the lecture to be missed
                            userLectureAdapter.setMissed(lectureID, 1);
                            userLectureAdapter.setAsked(lectureID, 1);
                            elementRemoved = false;
                            arrayAdapter.notifyDataSetChanged();
                            arrayAdapter.notifyDataSetInvalidated();
                            if (index + 1 < listItems.size()){
                                initListPrompt(index + 1);
                            }

                        }
                    })
                    .show();
        }
    }

    private void initListPrompt(int i){
        int timeValueNow;
        int timeValueLecture;
        int lectureId;
        String courseId;
        String time;
        Date dNow = new Date();
        DateFormat dateValueFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String thisTime = timeFormat.format(dNow);
        timeValueNow = Toolbox.timeToInt(thisTime);
        String thisDate = dateValueFormat.format(dNow);
        if (listItems.size() == 0){
            return;
        }

        courseId = listItems.get(i).split("\n|\t")[0];
        time = listItems.get(i).split("\n|\t")[3];
        String room = listItems.get(i).split("\n|\t")[5];
        timeValueLecture = Toolbox.timeToInt(time);
        time = time + ":00";
        lectureId = lectureAdapter.getLectureID(courseId, thisDate, time, room);
        if (lectureId == -1){
            ;
        }
        if (timeValueNow < timeValueLecture){
            listItems.remove(i);
            if (i < listItems.size()){
                initListPrompt(i);
            }
        }
        if(userLectureAdapter.isAsked(lectureId)){
            if (!userLectureAdapter.isMissed(lectureId)){
                listItems.remove(i);
                if (i < listItems.size()){
                    initListPrompt(i);
                }
            } else {
                i++;
                if (i < listItems.size()) {
                    initListPrompt(i);
                }
            }

        }
        missedPrompt(timeValueLecture, lectureId, courseId, time, i);
    }


    private void initList(){

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
                i--;
            }

        }

        // Create a List from String Array elements
        arrayAdapter = new MissedListAdapter<String>(getActivity().getApplicationContext(), R.layout.listitem, R.id.textview, listItems);
        // DataBind ListView with items from SelectListAdapter
        missedListView.setAdapter(arrayAdapter);
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
