/*
Diese Datei ist für das Routing, Debugging, Error-Handling und allgemeine Anwendungslogik
 */

const express = require("express"),
    bodyParser = require("body-parser"),
    app = express();

let test = require("./routes/test");
let user = require("./routes/user");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.use(function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST', 'PUT',
        'DELETE');
    res.setHeader('Access-Control-Allow-Headers',
        'X-Requested-With, content-type, Authorization');
    next();
});

app.use("/test", test);
app.use("/user", user);

app.use(function(req, res, next) {
    let time = new Date();
    console.log("Time: " + time);
    console.log("Request-Pfad: " + req.path);
    next();
});

// handling 404 errors
app.use(function(req, res, next) {
    let err = new Error("Sorry, nothing was found here");
    err.status = 404;
    next(err);
});

//error handler with time and route
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.send(err.message);
    next(err);
});

module.exports = app;