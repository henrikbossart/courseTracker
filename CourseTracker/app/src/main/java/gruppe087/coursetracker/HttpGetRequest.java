package gruppe087.coursetracker;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by henrikbossart on 28.02.2017.
 */

public class HttpGetRequest extends AsyncTask<String, Void, String> {

    public static final String REQUEST_METHOD   = "GET";
    public static final int READ_TIMEOUT        = 15000;
    public static final int CONNECTION_TIMEOUT  = 15000;
    private final String filename;

    public HttpGetRequest(String filename){
        this.filename = filename;

    }

    @Override
    protected String doInBackground(String... params) {

        // Taking in the params and generating the URL.
        // Varargs format: [varName,varValue, varName, varValue, ...]
        String stringURL = this.generateURL(params);
        String result;
        String inputLine;

        try {

            // Connecting to the given URL
            URL url = new URL(stringURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Setting connection methods
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();

            // Creating an InputStreamReader to read what the server is sending back
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }

            reader.close();
            streamReader.close();

            result = stringBuilder.toString();


        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    private String generateURL(String[] params){
        String stringURL = "http://138.197.33.171/php/" + filename.trim();

        if(params.length == 0){
            return stringURL;
        }
        stringURL += "?";
        for (int i = 0; i < Array.getLength(params); i++ ){
            if (i%2 == 0){
                stringURL += params[i];
                stringURL += "=";
            } else {
                stringURL += params[i];
            }
        }
        System.out.println(stringURL);
        return stringURL;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
