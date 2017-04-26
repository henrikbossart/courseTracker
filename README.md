# Coursetracker

Welcome to Coursetracker!  
Coursetrakcer is an app that allows you to track your courses at NTNU, and informs you about your missed lectures.

## Running the server
In order to run the application, you first have to create the database. Make sure you have installed MySQL on your computer or server. Then, run the script called ****

In order to run the server, you have to have Node.js installed on your computer/server. To do this in a bash console, type:

`curl "https://nodejs.org/dist/latest/node-${VERSION:-$(wget -qO- https://nodejs.org/dist/latest/ | sed -nE 's|.*>node-(.*)\.pkg</a>.*|\1|p')}.pkg" > "$HOME/Downloads/node-latest.pkg" && sudo installer -store -pkg "$HOME/Downloads/node-latest.pkg" -target "/"`

This will download Node.js on your computer.
After this, type `npm install` inside the server folder. then, you can type `npm start` to run the server.
Now your server is running, and you can navigate to `http://localhost:3000/` to view the website.

#### Note!
It is recomended that you run the server and the database on remote servers. You will have to change the IP address inside the Android application! To do this, open the file "HttpGetRequest.java" in your preferred editor. Inside the `generateURL()` method, change the IP in
```java
String stringURL = "http://138.197.33.171/php/" + filename.trim();
```

To the location where you have put the php folder inside the server folder.

## Running the application
In order to run the Android application, you can either download Android Studio from `https://developer.android.com/studio/index.html` and flash it to your device, or transfer the file `coursetracker.apk` to your phone.


#### Note!
The application is optimized for a device with a 5" display! Smaller or larger displays may cause problems.
