const express = require("express");
const router = express.Router();

const User = require("../models/user");

router.get("/", function (req, res) {
    User.find({}, function (err, data) {
        if (err) console.log(err);
        res.send(data);

        //DEV CONSOLE OUTPUT
        console.log(data.length + " User in DB gefunden");
        data.forEach(function (user, index) {
            console.log("User "+ (index + 1) +" mit ID: " + user._id.toString());
        });
    })
});

router.get("/:userId/", function (req, res) {
    let id = req.params.userId;
    User.findById(id, function (err, data) {
        if (err) console.log(err);
        res.send(data);
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

//TODO: Möglichkeit zum Ändern/Löschen von einzelnen Elementen in einem Array hinzufügen (Query?)
router.patch("/:userId", function (req, res) {
    let id = req.params.userId;
    let method = req.query.m;

    switch (method) {
        case "push":
            User.findByIdAndUpdate(id, {$addToSet: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        case "pull":
            /*re.body muss folgendes Format haben: [ "arrayname": "id"].
              Mehr als 1 id gleichzeitig zu entfernen ist zurzeit nicht möglich */
            User.findByIdAndUpdate(id, {$pull: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        default:
            User.findByIdAndUpdate(id, req.body, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
    }
});

router.delete("/:userId", function (req, res) {
    let id = req.params.userId;

    User.findByIdAndRemove(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

module.exports = router;
