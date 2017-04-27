# Coursetracker

Welcome to Coursetracker!  
Coursetrakcer is an app that allows you to track your courses at NTNU, and informs you with information regarding the lecture if you did not attend the lecture.

## Running the server
In order to run the application, you first have to create the database. Make sure you have installed MySQL on your computer or server.

Then, install all the modules stated at the top of all the python scripts in the folder server_scripts. Make sure you have a csv file with all the courses you want to scrape lectures from which have the correct file path stated in manipulateCSV.py inside the folder server_scripts/scraper and then run the script called MainLectureScraper.py to start scraping lectures. All the lectures will be written in a new csv file descending regarding course code and lecture date ready to be imported to a database.

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

!!!Until July 2017, you just have to install the Android app on your phone, and not change anything, server will be up until then!!!

To the location where you have put the php folder inside the server folder.

## Running the application
In order to run the Android application, you can either download Android Studio from `https://developer.android.com/studio/index.html` and flash it to your device, or transfer the file `coursetracker.apk` to your phone.


#### Note!
The application is optimized for a device with a 5" display! Smaller or larger displays may cause problems.
