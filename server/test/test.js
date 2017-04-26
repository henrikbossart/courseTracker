var chai     = require('chai');
var chaiHttp = require('chai-http');
var server   = require('../index.js');
var should   = chai.should();

chai.use(chaiHttp);

// Testing login method
describe('login', function () {
    it('should log in the user and redirect to /home', function () {
        chai.request(server)
            .post('/login')
            .set('Token', 'text/plain')
            .set('content-type', 'application/x-www-form-urlencoded')
            .type('form')
            .send('grant_type=password')
            .send('username=admin@admin')
            .send('password=admin')
            .end(function (err, done) {
                res.should.have.status(200);
                expect(res).to.redirectTo('http://138.171.33.197/home');
                done();
            });
    });
});

describe('login-fail', function () {
    it('should return that no user was found', function () {
        chai.request(server)
            .post('/login')
            .set('Token', 'text/plain')
            .set('content-type', 'application/x-www-form-urlencoded')
            .type('form')
            .send('grant_type=password')
            .send('username=admin@test')
            .send('password=admin')
            .end(function (err, done) {
                res.should.have.status(200);
                res.flash.should.be('No user found')
                expect(res).to.redirectTo('http://138.171.33.197/');
                done();
            });
    });
});


// Testing ability to get courses from database
describe('home', function () {
    it('should GET all courses in the database', function (done) {
        chai.request(server)
            .get('/home')
            .end(function (err, res) {
                res.should.have.status(200);
                done();
            });
    });
});

// Testing ability to PUT to DB
describe('send', function () {
    it('should PUT something into the database', function (done) {
        chai.request(server)
            .post('/send')
            .send({'cousrsecode':'TDT4100', 'date':'2017-04-24', 'time':'06:15', 'curriculum':'TSTING 123'})
            .end(function (err, res) {
                res.should.have.status(200);
                res.should.be.html;
                res.body.should.be.a('object');
                done();
            });
    });
});

//Testing the logout method
describe('logout', function () {
    it('should redirect us to /login', function () {
        chai.request(server)
            .post('/login')
            .end(function (err, done) {
                res.should.have.status(200);
                expect(res).to.redirectTo('http://138.171.33.197/login');
                done();
            });
    });
});
