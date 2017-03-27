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

        // Sign In button == True
        final Button button_signIn = (Button) findViewById(R.id.buttonSignIN);
        button_signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // Define action on click
                Intent myIntent = new Intent(SetupActivity.this, LoginActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                SetupActivity.this.startActivity(myIntent);

                // Transition
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        // Sign Up button == True
        final Button button_SignUp = (Button) findViewById(R.id.buttonSignUP);
        button_SignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // Define action on click
                Intent myIntent = new Intent(SetupActivity.this, SignupActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                SetupActivity.this.startActivity(myIntent);

                // Transition
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }


}
/*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                     // Create an intent that will start the main activity.
                Intent mainIntent = new Intent(SplashScreen.this,
                        ConnectedActivity.class);
                mainIntent.putExtra("id", "1");

                //SplashScreen.this.startActivity(mainIntent);
                startActivity(mainIntent);
                     // Finish splash activity so user cant go back to it.
                SplashScreen.this.finish();

                     /* Apply our splash exit (fade out) and main
                        entry (fade in) animation transitions.
                overridePendingTransition(R.anim.mainfadein,R.anim.splashfadeout);
            }
        }, SPLASH_DISPLAY_TIME);
    }
    */