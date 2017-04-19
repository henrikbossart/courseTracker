/////////////////////////////////////////////////////
// IMPORTING MODULES AND DEPENDENCIES
/////////////////////////////////////////////////////

var express = require('express');
var mysql   = require('mysql');
var pool    = mysql.createPool({
  connectionLimit : 100,
  host            : '138.197.33.171',
  user            : 'coursetracker',
  password        : 'admin',
  database        : 'coursetracker',
  debug           : false
});

var app     = express();

app.use(express.static(__dirname + '/views'));
app.use(express.static(__dirname + '/public'));

/////////////////////////////////////////////////////
// CONNECTING TO DATABASE
/////////////////////////////////////////////////////

function handle_database(req, res){
  pool.getConnection(function(err, connection){
    if (err) {
      res.json({"code" : 100, "status" : "Error in conecting to database"});
      return;
    }

    console.log('connected as id ' + connection.threadId);

    connection.query('SELECT * from courses', function(err, rows){
      connection.release();
      if (!err) {
        res.json(rows);
      }
    });

    connection.on('error', function(err){
      res.json({"code" : 100, "status" : "Error while connecting"});
      return;
    });
  });
}

app.get('/', function(req, res) {-
  handle_database(req, res)
  res.sendFile('index.html');
});

app.get('/signin', function(req, res) {
  res.sendFile('login.html');
});

app.listen(3000);

console.log('server running on port 3000');
