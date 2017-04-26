var mysql = require('mysql');
var connection = mysql.createConnection({
  host      : '45.55.250.253',
  user      : 'testuser',
  password  : '',
  database  : 'courses'
})

connection.connect();

connection.query('SELECT courseID FROM courses.courses where courseID="tdt4140"', function(error, rows, fields){
  if (! error) {
    console.log('RESULT: ', rows);
  } else {
    console.log('Error while executing query: \n', error);
  }
});

connection.end();
