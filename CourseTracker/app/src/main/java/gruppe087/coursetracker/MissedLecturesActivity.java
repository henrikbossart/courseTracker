package gruppe087.coursetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissedLecturesActivity extends AppCompatActivity {

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
    }

}
