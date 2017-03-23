package gruppe087.coursetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText editTextUserNameToLogin,editTextPasswordToLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }


}
