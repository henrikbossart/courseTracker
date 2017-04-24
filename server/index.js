/////////////////////////////////////////////////////
// IMPORTING MODULES AND DEPENDENCIES
/////////////////////////////////////////////////////

var express     = require('express');
var session     = require('express-session');
var cookieParser= require('cookie-parser');
var bodyParser  = require('body-parser');
var morgan      = require('morgan');
var app         = express();
var port        = process.env.PORT || 3000;

var passport    = require('passport');
var flash       = require('connect-flash');
var mysql       = require('mysql');
var path        = require('path');
var http        = require('http');
var router      = express.Router();
//var data        = require('./routes/data');

/////////////////////////////////////////////////////
// SETTING UP ENVIRONMENT
/////////////////////////////////////////////////////

require('./config/passport.js')(passport);

// Morgan, CookieParser and BodyParser are used to log every request in detail
app.use(morgan('dev'));
app.use(cookieParser());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

// Setting the view engine and defining paths
app.set('view engine', 'ejs');
app.use(express.static(path.join(__dirname, 'public')));
app.set('views', path.join(__dirname, 'views'));


//Writing request data to console for easier debugging
router.use(function (req, res, next) {
    var request_text    = 'Request: ' + req.protocol + '://' + req.hostname + '@' + req.ip + req.path;
    console.log(request_text);
    next();
})

/////////////////////////////////////////////////////
// CONNECTING TO DATABASE
/////////////////////////////////////////////////////

var dbconfig   = require('./config/database.js');
var connection = mysql.createConnection(dbconfig.connection);
connection.query('USE ' + dbconfig.database);

/////////////////////////////////////////////////////
// STARTING PASSPORT
/////////////////////////////////////////////////////

app.use(session({
    secret            : 'unicornsareawesome',
    resave            : true,
    saveUninitialized : true}));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());

/////////////////////////////////////////////////////
// DEFINING ROUTES
/////////////////////////////////////////////////////

require('./routes/routes')(app, passport, connection);

/////////////////////////////////////////////////////
// LAUNCHING CLIENT
/////////////////////////////////////////////////////

app.listen(port, function () {
    console.log('server running on port ' + port);
});

module.exports = app;