const express = require("express");

let router = express.Router();

let Settlement = require("../models/settlement");

router.get("/", function (req, res) {
    Settlement.find({}, function (err, result) {
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

router.get("/:settlementId/", function (req, res) {
    let id = req.params.settlementId;
    Settlement.findById(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    })
});

router.post("/", function (req, res) {
    console.log(req.body);

    let settlement = new Settlement({
        creditor: req.body.creditor,
        debtor: req.body.debtor,
        receipts: req.body.receipts,
    });
    settlement.save(function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

router.patch("/:settlementId", function (req, res) {
    let id = req.params.settlementId;
    let method = req.query.m;

    switch (method) {
        case "push":
            Settlement.findByIdAndUpdate(id, {$addToSet: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        case "pull":
            /*re.body muss folgendes Format haben: [ "arrayname": "id"].
              Mehr als 1 id gleichzeitig zu entfernen ist zurzeit nicht möglich */
            Settlement.findByIdAndUpdate(id, {$pull: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        default:
            Settlement.findByIdAndUpdate(id, req.body, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
    }
});

router.delete("/:settlementId", function (req, res) {
    let id = req.params.settlementId;

    Settlement.findByIdAndRemove(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

module.exports = router;