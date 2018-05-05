/*
Diese Datei ist für das Routing, Debugging, Error-Handling und allgemeine Anwendungslogik
 */

const express = require("express"),
    bodyParser = require("body-parser"),
    methodOverride = require("method-override");

let app = express();

let test = require("./routes/test");

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.use("/test", test);

app.use(function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST', 'PUT',
        'DELETE');
    res.setHeader('Access-Control-Allow-Headers',
        'X-Requested-With, content-type, Authorization');
    next();
});

// handling 404 errors
app.use(function(req, res, next) {
    var err = new Error("Sorry, nothing was found here");
    err.status = 404;
    next(err);
});

//error handler with time and route
app.use(function(err, req, res, next) {
    let time = new Date();
    console.log("Time: " + time);
    console.log("Request-Pfad: " + req.path + ":");
    console.error(err.message)

    res.status(err.status || 500);
    res.send(err.message);
    next();
});

module.exports = app;