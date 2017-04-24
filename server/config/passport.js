var LocalStrategy   = require('passport-local').Strategy;
var mysql           = require('mysql');
var dbconfig        = require('./database');
var connection      = mysql.createConnection(dbconfig.connection);
var bcrypt          = require('bcrypt-nodejs');

connection.query('USE ' + dbconfig.database);

module.exports = function (passport) {

    ///////////////////////////////////////
    // PASSPORT SESSION SETUP
    // required for persistent login sessions
    // passport needs ability to serialize and unserialize users out of session
    ///////////////////////////////////////

    passport.serializeUser(function (user, done) {
        done(null, user.id);
    });

    passport.deserializeUser(function (id, done) {
        connection.query("SELECT * FROM users WHERE username = ? ", [id], function (err, rows) {
            done(err, rows[0]);
        });
    });

    ///////////////////////////////////////
    // LOCAL SIGNUP
    // we are using named strategies since we have one for login and one for signup
    // by default, if there was no name, it would just be called 'local'
    //
    // Signup is not needed, and therefore commented out. Users will be added directly
    // into the database by sysadmin
    ///////////////////////////////////////
    /*
    passport.use('local-signup', new LocalStrategy({
        usernameField       : 'username',
        passwordField       : 'password',
        passReqToCallback   : true
    },
    function (req, username, password, done) {
        connection.query("SELECT * FROM users WHERE username = ? ",[username], function (err, rows) {

            if (err) {
                return done(err);
            }
            if (rows.length) {
                return done(null, false, req.flash('signupMessage', "That email is already taken!"));
            } else {
                var newUserMysql = {
                    username : username,
                    password : bcrypt.hashSync(password, null, null)
                };

                var insertQuery = "INSERT INTO users (username, password) VALUES (?,?)";
                connection.query(insertQuery, [newUserMysql.username, newUserMysql.password], function (err, rows) {
                    newUserMysql.id = rows.insertId;
                });

                return done(null, newUserMysql);
            }
        });
    }));
    */
    ///////////////////////////////////////
    // LOCAL LOGIN
    // we are using named strategies since we have one for login and one for signup
    // by default, if there was no name, it would just be called 'local'
    ///////////////////////////////////////

    passport.use('local-login', new LocalStrategy({
        usernameField    : 'username',
        passwordField    : 'password',
        passReqToCallback:  true
    },
    function (req, username, password, done) {
        connection.query("SELECT * FROM users WHERE username = ? ",[username], function (err, rows) {
            if (err){
                return done(err);
            }
            if (!rows.length) {
                return done(null, false, req.flash('loginMessage', 'No user found.'));
            }

            if (!((password == rows[0].password))) {
                return done(null, false, req.flash('loginMessage', 'Oops! Wrong password.'));
            }

            return done(null, rows[0]);
        });
    }));
}