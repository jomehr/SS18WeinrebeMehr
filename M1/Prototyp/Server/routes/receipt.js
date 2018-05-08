const express = require("express");

let router = express.Router();

let Receipt = require("../models/receipt");

router.get("/", function (req, res) {
    Receipt.find({}, function (err, result) {
        if (err) console.log(err);
        res.send(result);

        //DEV CONSOLE OUTPUT
        let copy = [];
        console.log(result.length + " Gruppen in DB gefunden")
        result.forEach(function (receipt, index) {
            copy.push(receipt._id);
            console.log("Gruppe " + (index + 1) + " mit ID: " + receipt._id.toString());
        });
        console.log(copy)
    });
});

router.get("/:receiptId/", function (req, res) {
    let id = req.params.receiptId;
    Receipt.findById(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    })
});

router.post("/", function (req, res) {
    console.log(req.body);

    let receipt = new Receipt({
        owner: req.body.owner,
        participants: req.body.participants,
        store: req.body.store,
        date: req.body.date,
        imagePath: req.body.imagePath,
        address: req.body.address,
        article: req.body.article,
        total: req.body.total,
        payment: req.body.payment,
        change: req.body.change
    });
    receipt.save(function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

router.patch("/:receiptId", function (req, res) {
    let id = req.params.receiptId;
    let method = req.query.m;

    switch (method) {
        case "push":
            Receipt.findByIdAndUpdate(id, {$addToSet: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        case "pull":
            /*re.body muss folgendes Format haben: [ "arrayname": "id"].
              Mehr als 1 id gleichzeitig zu entfernen ist zurzeit nicht möglich */
            Receipt.findByIdAndUpdate(id, {$pull: req.body}, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
            break;
        default:
            Receipt.findByIdAndUpdate(id, req.body, {new: true}, function (err, result) {
                if(err) console.log(err);
                res.send(result);
            });
    }
});

router.delete("/:receiptId", function (req, res) {
    let id = req.params.receiptId;

    Receipt.findByIdAndRemove(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

module.exports = router;