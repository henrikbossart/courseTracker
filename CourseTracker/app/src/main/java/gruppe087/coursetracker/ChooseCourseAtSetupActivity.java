package gruppe087.coursetracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;


public class ChooseCourseAtSetupActivity extends AppCompatActivity {

    CustomAdapter<String> customAdapter;
    ArrayList<String> listItems;
    ListView lv;
    EditText et;
    HttpGetRequest getRequest;
    ArrayList<String> overview_list;
    HashSet<Integer> selected = new HashSet<Integer>();
    LoginDataBaseAdapter loginDataBaseAdapter;
    AddCoursesToDataBaseAdapter dataBaseAdapter;
    ArrayList<String> infoList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get Instance  of Database Adapter
        dataBaseAdapter = new AddCoursesToDataBaseAdapter(this);
        dataBaseAdapter = dataBaseAdapter.open();

        setContentView(R.layout.activity_choose_course_at_setup);

        lv = (ListView)findViewById(R.id.initlv);
        customAdapter = new CustomAdapter<String>(this, listItems, selected);

        //START LISTVIEW

        lv = (ListView) findViewById(R.id.initlv);
        et = (EditText) findViewById(R.id.searchtxt);

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setItemsCanFocus(false);

        initList();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String selectedFromList = (String)(customAdapter.getItem(position));
                int pos = overview_list.indexOf(selectedFromList);

                if (!isSelected(pos)){
                    view.setBackgroundColor(getResources().getColor(R.color.grey));
                    select(pos);
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                    deselect(pos);
                }
                customAdapter.updateSelected(selected);
                customAdapter.notifyDataSetChanged();

            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    //reset listview
                    initList();
                    colourSelectedItems();
                }
                else {
                    //perform search
                    searchItem(s.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //END LISTVIEW




        final Button button = (Button) findViewById(R.id.dummy_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Define action on click
                ArrayList<String> courseCodes = new ArrayList<String>();
                for (int i : selected){
                    String courseCode = overview_list.get(i).split(" ")[0];
                    getRequest = new HttpGetRequest("getCourse.php ");
                    String result;
                    try {
                        result = getRequest.execute("courseID",courseCode).get();
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                        result=null;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        result=null;
                    }
                    // Parsing the result and turning it into an JSONArray, so that it is simpler to pick
                    // out the fields that are wanted.
                    try {
                        System.out.println(result);
                        JSONArray jsonArray = new JSONArray(result);

                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String courseID = jsonObject.getString("courseID");
                        String courseName = jsonObject.getString("courseName");
                        String location = jsonObject.getString("location");
                        String examDate = jsonObject.getString("examDate");
                        System.out.println(courseID + " " + courseName + " " + location + " " + examDate);

                        dataBaseAdapter.insertEntry(courseID, courseName, location, examDate);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                //Intent myIntent = new Intent(RegisterNameActivity.this, TodayOverviewActivity.class);
                Intent myIntent = new Intent(ChooseCourseAtSetupActivity.this, MainActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                ChooseCourseAtSetupActivity.this.startActivity(myIntent);
            }
        });

    }

    private void colourSelectedItems(){
        for (int i = 0; i < customAdapter.getCount(); i++){
            View v = getViewByPosition(i,lv);
            String s = (String) customAdapter.getItem(i);
            int index = overview_list.indexOf(s);
            if (selected.contains(index)) {
                v.setBackgroundColor(getResources().getColor(R.color.grey));
            } else {
                v.setBackgroundColor(Color.WHITE);
            }

        }
    }

    public void searchItem(String textToSearch){

        if(overview_list.size() != 0) {
            for (int i = 0; i < overview_list.size(); i++) {

                String item = overview_list.get(i);

                if (!item.toLowerCase().contains(textToSearch.toLowerCase())) {
                    customAdapter.addHiddenPosition(i);
                } else {
                    customAdapter.removeHiddenPosition(i);
                }
            }
        }
        colourSelectedItems();

        ArrayList sortedList = new ArrayList(selected);
        Collections.sort(sortedList);
        System.out.println(sortedList);
        customAdapter.notifyDataSetChanged();

    }

    public void initList(){

        listItems = new ArrayList<String>();
        // Initializing getRequest class
        getRequest = new HttpGetRequest("addCoursesSetup.php");
        String result;
        try {
            result = getRequest.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            result=null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            result=null;
        }
        System.out.println(result);


        String[] overview = new String[]{};
        overview_list = new ArrayList<String>(Arrays.asList(overview));
        infoList = new ArrayList<String>();

        // Parsing the result and turning it into an JSONArray, so that it is simpler to pick
        // out the fields that are wanted.
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String courseID = jsonObject.getString("courseID");
                String courseName = jsonObject.getString("courseName");
                //String location = jsonObject.getString("location");
                //String examDate = jsonObject.getString("examDate");

                overview_list.add(courseID + " " + courseName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Create a List from String Array elements
        customAdapter = new CustomAdapter<String>
                (this, overview_list, selected);


        // DataBind ListView with items from CustomAdapter
        lv.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void select(int pos){
        selected.add(pos);
    }

    public void deselect (int pos){
        selected.remove(pos);
    }

    public boolean isSelected(int pos){
        if(selected.contains(pos)){
            return true;
        } else {
            return false;
        }
    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}

