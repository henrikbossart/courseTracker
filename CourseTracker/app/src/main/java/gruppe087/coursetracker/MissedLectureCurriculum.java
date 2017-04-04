package gruppe087.coursetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MissedLectureCurriculum extends AppCompatActivity {
    Button back_btn;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missed_lecture_curriculum);
        back_btn = (Button) findViewById(R.id.back_btn);
        text = (TextView) findViewById(R.id.body_tv);
        Bundle bundle = getIntent().getExtras();
        String curriculum = bundle.getString("curriculum");
        System.out.println(curriculum);
        text.setText(curriculum);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MissedLectureCurriculum.this, MainActivity.class);
                Toolbox.fragment = 1;
                //myIntent.putExtra("fragment", 1);
                //Optional parameters: myIntent.putExtra("key", value);
                MissedLectureCurriculum.this.startActivity(myIntent);

                //Transition
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });




    }
}
