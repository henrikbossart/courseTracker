// Middleware to make sure user is logged in

module.exports = function isLoggedIn(req, res, next) {
    // If user is authenticated in the session, carry on
    if (req.isAuthenticated()){
        return next();
    } else {
        // if they are not, redirect to login
        res.redirect('/login');
    }
}