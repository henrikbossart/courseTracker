package gruppe087.coursetracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText editTextUserNameToLogin,editTextPasswordToLogin;
    public static final String PREFS_NAME = "CTPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserNameToLogin=(EditText)findViewById(R.id.editTextUserNameToLogin);
        editTextPasswordToLogin=(EditText)findViewById(R.id.editTextPasswordToLogin);

        // When Log in button pushed
        final Button button = (Button) findViewById(R.id.buttonSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                // get The User name and Password
                String userName=editTextUserNameToLogin.getText().toString();
                String password=editTextPasswordToLogin.getText().toString();
                // fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);
                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword))
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", userName);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    // Define action on click
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                    //Optional parameters: myIntent.putExtra("key", value);
                    LoginActivity.this.startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "User Name or Password is not correct or found in database", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void createNotification(View view) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.drawable.ic_checklist)
                .setContentIntent(pIntent)
                .addAction(R.drawable.icon, "Call", pIntent)
                .addAction(R.drawable.icon, "More", pIntent)
                .addAction(R.drawable.icon, "And more", pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }


}
