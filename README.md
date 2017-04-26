In order to run the application, you first have to create the database. Make sure you have installed MySQL on your computer or server. Then, run the script called ****
\\
\\
In order to run the server, you have to have Node.js installed on your computer/server. To do this in a bash console, type:
\\
\\
curl "https://nodejs.org/dist/latest/node-${VERSION:-$(wget -qO- https://nodejs.org/dist/latest/ | sed -nE 's|.*>node-(.*)\.pkg</a>.*|\1|p')}.pkg" > "$HOME/Downloads/node-latest.pkg" && sudo installer -store -pkg "$HOME/Downloads/node-latest.pkg" -target "/"
\\
\\

This will download Node.js on your computer. After this, type "npm install" inside the server folder. then, you can type npm start to run the server. Now your server is running, and you can navigate to http://localhost:3000/ to view the website.
\\
\\
To run the Application you will have to set up your application
