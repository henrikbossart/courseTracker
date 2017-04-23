exports.list = function (req, res, connection) {


    var query = connection.query('SELECT * FROM courses', function (err, rows) {
        if (err){
            console.log('Error selecting : %s', err);
        }
        res.render('index.ejs', {page_title:"Index", data: rows});
    })
}