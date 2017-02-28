package gruppe087.coursetracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TodayOverviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_overview);

        // Get reference of widgets from XML layout
        final ListView lv = (ListView) findViewById(R.id.lv);

        HttpGetRequest getRequest = new HttpGetRequest();
        String url = "http://138.197.33.171/php/test.php";
        String result;
        try {
            result = getRequest.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            result=null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            result=null;
        }
        // Initializing a new String Array
        String[] courses
                = new String[]{
                "KTN @ R1, 09.15-11.00",
                "MMI @ S3, 12.15-14.00",
                "PU @ R1, 14.15-16.00",
                result

        };

        // Create a List from String Array elements
        final List<String> course_list = new ArrayList<String>(Arrays.asList(courses));
        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, course_list);

        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        final Button button = (Button) findViewById(R.id.today_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Define action on click
                Intent myIntent = new Intent(TodayOverviewActivity.this, MissedLecturesActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                TodayOverviewActivity.this.startActivity(myIntent);
            }
        });
    }
}

