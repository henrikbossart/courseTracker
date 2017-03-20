package gruppe087.coursetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class ChooseCourseAtSetupActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> listItems;
    ListView lv;
    EditText et;
    HttpConnector httpConnector;
    ArrayList<String> overview_list;
    ArrayList<String> hidden_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course_at_setup);

        lv = (ListView)findViewById(R.id.initlv);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_text_view, listItems);

        //START LISTVIEW

        lv = (ListView) findViewById(R.id.initlv);
        et = (EditText) findViewById(R.id.searchtxt);

        initList();
        System.out.println("First");
        System.out.println(listItems);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    //reset listview
                    initList();
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
                //Intent myIntent = new Intent(RegisterNameActivity.this, TodayOverviewActivity.class);
                Intent myIntent = new Intent(ChooseCourseAtSetupActivity.this, MainActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                ChooseCourseAtSetupActivity.this.startActivity(myIntent);
            }
        });

    }

    public void searchItem(String textToSearch){

        if(overview_list.size() != 0) {
            for (int i = 0; i < overview_list.size(); i++) {

                String item = overview_list.get(i);

                if (!item.toLowerCase().contains(textToSearch.toLowerCase())) {
                    hidden_list.add(item);
                    overview_list.remove(item);
                }
            }
        }
        if(hidden_list.size() != 0){
            for (int i = 0; i < hidden_list.size(); i++) {
                String item = hidden_list.get(i);

                if(item.toLowerCase().contains(textToSearch.toLowerCase())){
                    overview_list.add(item);
                    hidden_list.remove(item);
                }
            }
        }
        arrayAdapter.notifyDataSetChanged();

    }

    public void initList(){

         listItems = new ArrayList<String>();
        // Initializing httpConnector class
        httpConnector = new HttpConnector("getCourses");
        JSONArray result;
        try {
            result = httpConnector.execute().get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
            result=null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            result=null;
        }


        String[] overview = new String[]{};
        overview_list = new ArrayList<String>(Arrays.asList(overview));
        hidden_list = new ArrayList<String>();

        // Parsing the result and turning it into an JSONArray, so that it is simpler to pick
        // out the fields that are wanted.

        try {

            for (int i = 0; i<result.length(); i++) {
                JSONObject jsonObject = result.getJSONObject(i);
                String courseID = jsonObject.getString("courseID");
                String courseName = jsonObject.getString("courseName");

                overview_list.add(courseID + "\t" + courseName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a List from String Array elements
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, overview_list);


        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);
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

}
