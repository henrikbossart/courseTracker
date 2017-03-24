package gruppe087.coursetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MissedLectureCurriculum extends AppCompatActivity {
    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed_lecture_curriculum);
        back_btn = (Button) findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MissedLectureCurriculum.this, MainActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                MissedLectureCurriculum.this.startActivity(myIntent);
            }
        });




    }
}
