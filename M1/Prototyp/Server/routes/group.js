const express = require("express");

let router = express.Router();

let Group = require("../models/group");

router.get("/", function (req, res) {
    Group.find({}, function (err, result) {
        if (err) console.log(err);
        res.send(result);

        //DEV CONSOLE OUTPUT
        let copy = [];
        console.log(result.length + " Gruppen in DB gefunden")
        result.forEach(function (group, index) {
            copy.push(group._id);
            console.log("Gruppe " + (index + 1) + " mit ID: " + group._id.toString());
        });
        console.log(copy)
    });
});

router.get("/:groupId/", function (req, res) {
    let id = req.params.groupId;
    Group.findById(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    })
});

router.post("/", function (req, res) {
    console.log(req.body);

    let group = new Group({
        name: req.body.name,
        creator: req.body.creator,
        startDate: req.body.startDate,
        endDate: req.body.endDate,
        participants: req.body.participants,
        startLocation: req.body.startLocation,
        endLocation: req.body.endLocation
    });
    group.save(function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

router.patch("/:groupId", function (req, res) {
    let id = req.params.groupId;
    let method = req.query.m;

    switch (method) {
        case "push":
            Group.findByIdAndUpdate(id, {$addToSet: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        case "pull":
            /*re.body muss folgendes Format haben: [ "arrayname": "id"].
              Mehr als 1 id gleichzeitig zu entfernen ist zurzeit nicht möglich */
            Group.findByIdAndUpdate(id, {$pull: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        default:
            Group.findByIdAndUpdate(id, req.body, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
    }
});

router.delete("/:groupId", function (req, res) {
    let id = req.params.groupId;

    Group.findByIdAndRemove(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

module.exports = router;