const express = require("express");
//const bodyParser = require("body-parser");

let router = express.Router();

//router.use(bodyParser.json());

let User = require("../models/user");

router.get("/", function (req, res) {
    User.find({}, function (err, data) {
        if (err) console.log(err);
        res.send(data);
        console.log(data.length + " User in DB gefunden")
    })
});

router.post("/", function (req, res) {
    console.log(req.body);

    let user = new User ({
        name: req.body.name,
        address: req.body.address,
        email: req.body.email,
        password: req.body.password
    });
    user.save(function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

module.exports = router;
