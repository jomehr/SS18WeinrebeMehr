/*
Diese Datei ist für das Routing, Debugging, Error-Handling und allgemeine Anwendungslogik
 */

const express = require("express"),
    bodyParser = require("body-parser"),
    app = express();

const user = require("./routes/user");
const group = require("./routes/group");
const receipt = require("./routes/receipt");
const settlement = require("./routes/settlement");

const admin = require("firebase-admin");
const serviceAccount = require("./serviceAccountKey.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

app.use(function(req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With, content-type, Authorization');
    next();
});

app.use(function(req, res, next) {
    let time = new Date();
    console.log("#####\n"
        + "Time: " + time + "\n"
        + req.method + " auf " + req.path);
    next();
});

app.use("/user", user);
app.use("/group", group);
app.use("/receipt", receipt);
app.use("/settlement", settlement);

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
