package gruppe087.coursetracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class SetupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup);

        final Button button = (Button) findViewById(R.id.next_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // Define action on click
                Intent myIntent = new Intent(SetupActivity.this, RegisterNameActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                SetupActivity.this.startActivity(myIntent);
            }
        });
    }


}
