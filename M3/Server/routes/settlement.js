const express = require("express"),
    faye = require("faye");

let router = express.Router();

let Settlement = require("../models/settlement");
let Receipt = require("../models/receipt");

router.get("/", function (req, res) {
    Settlement.find({}, function (err, result) {
        if (err) console.log(err);
        res.send(result);

        //DEV CONSOLE OUTPUT
        let copy = [];
        console.log(result.length + " Gruppen in DB gefunden")
        result.forEach(function (group, index) {
            copy.push(group._id);
            console.log("Abrechnung " + (index + 1) + " mit ID: " + group._id.toString());
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
        case "push": //fügt Werte einem Array hinzu
            Settlement.findByIdAndUpdate(id, {$addToSet: req.body}, {new: true}, function (err, result) {
                if (err) console.log(err);
                res.send(result);
            });
            break;
        case "pull": //entfernt Wert aus einem Array
            /*re.body muss folgendes Format haben: [ "arrayname": "id"].
              Mehr als 1 id gleichzeitig zu entfernen ist zurzeit nicht möglich */
            Settlement.findByIdAndUpdate(id, {$pull: req.body}, {new: true}, function (err, result) {
                if (err) console.log(err);
                res.send(result);
            });
            break;
        default: //ändert einzelne Werte (keine Arrays)
            Settlement.findByIdAndUpdate(id, req.body, {new: true}, function (err, result) {
                if (err) console.log(err);
                res.send(result);
            });
    }
});

router.patch("/:settlementId/calc", function (req, res) {
    let id = req.params.settlementId;
    let tmp = 0;

    Settlement.findById(id, function (err, settlement) {
        if (err) return console.log(err);
        settlement.receipts.forEach(function (receiptId) {
            Receipt.findById(receiptId, "article", function (err, receipt) {
                if (err) return console.log(err);
                receipt.article.forEach(function (article) {
                    article.participation.forEach(function (participation, index) {
                        if (article.amount > 1) {
                            tmp = article.priceAll * participation.percentage;
                        } else {
                            tmp = article.price * participation.percentage;
                        }

                        client.publish("/settlement/" +id,
                            {
                                user: participation.participant,
                                message: participation.participant + " schuldet " + tmp +
                                " (" + article.priceAll + "*" + participation.percentage + ")" +
                                " für " + article.name
                            });

                        if (settlement.debtor.length === 0) {
                            console.log("debtor ist leer");
                            settlement.update(
                                {
                                    $addToSet: {
                                        "debtor": {
                                            "userId": participation.participant,
                                            "articles": [{
                                                "articleId": article._id,
                                                "articleName": article.name,
                                                "debt": tmp
                                            }],
                                        }
                                    }
                                },
                                {upsert: true, new: true},
                                function (err) {
                                    if (err) return console.log(err);
                                });
                        } else {
                            // settlement.debtor.forEach(function (debtor, index) {
                            //     console.log("X" + debtor.userId);
                            //     console.log("Y" + participation.participant);
                            //
                            //     if (debtor.userId = participation.participant) {
                            //         console.log("Test" + index);
                            //     }
                            // })
                        }

                    });
                })
            })
        });
        res.status(202).send(settlement)
    })
    ;
})
;

router.delete("/:settlementId", function (req, res) {
    let id = req.params.settlementId;

    Settlement.findByIdAndRemove(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
});

let client = new faye.Client("http://localhost:8081/test");
client.subscribe("/settlement/*", function (message) {
    console.log(message);
});
client.subscribe("/user/*", function (message) {
    console.log(message);
});

module.exports = router;