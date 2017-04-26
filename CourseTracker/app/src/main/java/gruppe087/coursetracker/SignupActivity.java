package gruppe087.coursetracker;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    EditText editTextUserName,editTextPassword,editTextConfirmPassword;
    LoginDataBaseAdapter loginDataBaseAdapter;
    public static final String PREFS_NAME = "CTPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // When Sign up button pushed
        final Button button = (Button) findViewById(R.id.buttonCreateAccount);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){

                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();

                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }
                //TODO: Implement Toast when user already exists!

                LoginDataBaseAdapter userDb = new LoginDataBaseAdapter(getApplicationContext());
                userDb.open();
                if(userDb.userExists(userName)){
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", userName);
                    editor.commit();
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(userName, password);


                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                }

                // Define action on click
                Intent myIntent = new Intent(SignupActivity.this, ChooseCourseAtSetupActivity.class);
                //Optional parameters: myIntent.putExtra("key", value);
                SignupActivity.this.startActivity(myIntent);

                // create fading animation
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }

}
