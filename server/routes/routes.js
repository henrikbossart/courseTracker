// routes/routes.js
module.exports = function(app,passport, connection){

    /////////////////////////////////////////////////////
    // HOME PAGE
    /////////////////////////////////////////////////////

    app.get('/', function (req, res) {
        res.redirect('/login');
    });



    ////////////////////////////////////////////////////////////
    // Login
    ////////////////////////////////////////////////////////////

    app.get('/login', function(req, res) {
        // render the page and pass in any flash data if it exists
        res.render('login.ejs', {message : req.flash('loginMessage')});
    });

    app.post('/login', passport.authenticate('local-login', {
        successRedirect : '/home',
        failureRedirect : '/login',
        failureFlash : true
    }), function (req, res) {
        console.log('hello');

        if (req.body.remember){
            req.session.cookie.maxAge = 1000 * 60 * 3;
        } else {
            req.session.cookie.expires = false;
        }
        res.redirect('/');
    });


    ////////////////////////////////////////////////////////////
    // Logout
    ////////////////////////////////////////////////////////////
    app.get('/logout', function(req, res) {
        req.logout();
        res.redirect('/');
    });

    app.get('/home', isLoggedIn, function (req, res) {

        var courses = 'SELECT * FROM courses';
        var times = 'SELECT time, date FROM `lecture` INNER JOIN `courses` WHERE lecture.courseID = courses.courseID'

        connection.query(courses, function (err, rows) {
            if (err){
                console.log('Error selecting : %s', err);
            }
            console.log(rows);
            res.render('index.ejs', {page_title:"Index", data: rows, time: times});
        })
    });
    app.post('/send', function (req, res) {
        var coursecode = req.body.coursecode;
        var date = req.body.date;
        var time = req.body.time;
        var curriculum = req.body.textarea1;

        var query =
            "UPDATE lecture SET lecture.curriculum = '" + curriculum +
            "' WHERE lecture.courseID = '" + coursecode +
            "' AND lecture.time = '" + time +
            "' AND lecture.date = '" + date + "'";
        put();

        function put(err) {
            connection.query(query);
            if (err){
                res.json({'ERROR: ': err})
            } else {
                res.redirect('/home');
            }
        }
    });



    function isLoggedIn(req, res, next) {
        if (req.isAuthenticated()){
            return next();
        }
        res.redirect('/');
    }

}
