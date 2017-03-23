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

public class HttpConnector extends AsyncTask<String, Void, JSONArray> {

    private String baseUrl                          = "http://138.197.33.171/php/";
    private String url;
    public static final String REQUEST_METHOD   = "GET";
    public static final int READ_TIMEOUT        = 15000;
    public static final int CONNECTION_TIMEOUT  = 15000;
    public HttpConnector(String command, String... argument){

        commandChanger(command, argument);

    }

    public void commandChanger(String command, String... argument){
        switch (command){
            case "getCourse":
                url = baseUrl + "getCourse.php?courseID=" + argument;
                break;

            case "getCourses":
                url = baseUrl + "getCourses.php";
                break;

            case "addCoursesSetup":
                url = baseUrl + "addCoursesSetup.php";
                break;

            case "getLectures":
                url = baseUrl + "getLectures.php";
                break;

            case "getLecture":
                //given that the arguments passed into the method are COURSEID, DATE, and DATE is on
                // format YYYY-MM-DD
                url = baseUrl + "getLecture.php?courseID=" + argument + "&" + argument;
                break;

        }
    }

    @Override
    protected JSONArray doInBackground(String... params) {

        // Taking in the params and generating the URL.
        // Varargs format: [varName,varValue, varName, varValue, ...]
        String stringURL = this.generateURL(params);
        String result;
        String inputLine;
        System.out.println("params: " + params);

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

        return JSONArrayConvert(result);

    }

<<<<<<< HEAD:CourseTracker/app/src/main/java/gruppe087/coursetracker/HttpConnector.java

    public String getUrl() {
        return url;
    }

    public String generateURL(String[] params){
        String stringURL = url;
=======
    private String generateURL(String[] params){
        String stringURL = "http://138.197.33.171/php/" + filename.trim();

>>>>>>> 27fa58faf11191c496c35f4a64273ae04d981c56:CourseTracker/app/src/main/java/gruppe087/coursetracker/HttpGetRequest.java
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
<<<<<<< HEAD:CourseTracker/app/src/main/java/gruppe087/coursetracker/HttpConnector.java
        url = stringURL;
=======
        System.out.println(stringURL);
>>>>>>> 27fa58faf11191c496c35f4a64273ae04d981c56:CourseTracker/app/src/main/java/gruppe087/coursetracker/HttpGetRequest.java
        return stringURL;
    }

    @Override
    protected void onPostExecute(JSONArray result) {

        super.onPostExecute(result);
        this.cancel(true);
    }

    public JSONArray JSONArrayConvert(String input) {

        try {
            JSONArray jsonArray = new JSONArray(input);
            return jsonArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
