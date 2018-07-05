let Receipt = require("../models/receipt");
let Group = require("../models/group");
let User = require("../models/user");
let Adress = require("../models/address");
let Article = require("../models/article");
let Participation = require("../models/participation");
let Settlement = require("../models/settlement");
//let Category = require("../models/category");
logic = require("./logic");

let admin = require("firebase-admin");

exports.receipts_get_all = function (req, res) {

    Receipt.find({type: "GROUP"})
        .populate("owner", "name")
        .exec(function (err, result) {
            if (err) console.log(err);
            res.send(result);
        });
};

exports.receipts_create_receipt = function (req, res) {
    let receiptID;
    let receiptData;
    let articleData;
    let participationData;

    let receipt = new Receipt({
        type: req.body.type,
        owner: req.body.owner,
        group: req.body.group,
        location: req.body.location,
        store: req.body.store,
        date: req.body.date,
        total: req.body.total,
        paid: req.body.paid,
        change: req.body.change,
        currency: req.body.currency,
    });



    console.log(receipt);
    receipt.save(function (err, result) {
        if (err) console.log(err);
        receiptData = result;

        receiptID = result._id;
        res.status(202).end();

        req.body.articles.forEach(function (item, index) {
            let article = new Article({
                receipt: receiptID,
                name: item.name,
                priceTotal: item.priceTotal,
                priceSingle: item.priceSingle,
                amount: item.amount
            });

            article.save(function (err, result) {
                if (err) console.log(err);
                 articleData = result;
                let articleId = result._id;

                Receipt.findByIdAndUpdate(
                    receiptID, {$addToSet: {articles: articleId}}, {new:true},
                    function(err, result) {
                        if (err) console.log(err);
                        receiptData = result;
                        console.log(receiptData);
                     });
                req.body.articles[index].participation.forEach(function (item) {
                    let participation = new Participation({

                      receipt: receiptID,
                      article: articleId,
                      participant: item.participant,
                      percentage: item.percentage

                    });

                    participation.save(function (err, result) {
                        if (err) console.log(err);
                        participationData = result;

                        Article.findByIdAndUpdate(
                            articleId, {$addToSet: {participation: result._id}}, {new: true},
                            function (err, result) {
                                if (err) console.log(err);
                                articleData = result;
                            }
                        );
                        Receipt.findByIdAndUpdate(
                            receiptID, {$addToSet: {participants: result.participant}}, {new: true},
                            function (err, result) {
                                if (err) console.log(err);
                                receiptData = result;
                                console.log(receiptData);

                                let message = {
                                    //data payload, used to trigger changes in app
                                    data: {
                                        activity: "3",
                                        dataId: receiptID.toString()
                                    },
                                    //notification data for statusbar
                                    notification: {
                                      title: "Neuer Kassenzettel",
                                      body: "Ein neuer Kassenzzettel wurde in der Gruppe verlinkt"
                                    },
                                    topic: "group_articles" //test statt "group_receipts"
                                };

                                admin.messaging().send(message)
                                    .then((response) => {
                                        // Response is a message ID string.
                                        console.log('Successfully sent message:', response);
                                    })
                                    .catch((error) => {
                                        console.log('Error sending message:', error);
                                    });
                            }
                        );
                        logic.distribute_debts("post", receiptData, articleData, participationData);
                    });

                });
            });
        });
    });
};

exports.receipts_get_single = function (req, res) {
    let id = req.params.receiptId;

    Receipt.findById(id, function (err, result) {
        if (err) console.log(err);

        console.log(result);
        res.send(result);
    });
};

exports.receipts_update_receipt = function (req, res) {
    let id = req.params.receiptId;

    Receipt.findByIdAndUpdate(id, {

        $set: {
            type: req.body.type,
            owner: req.body.owner,
            group: req.body.group,
            location: req.body.location,
            store: req.body.store,
            date: req.body.date,
            total: req.body.total,
            paid: req.body.paid,
            change: req.body.change,
            currency: req.body.currency,
            edited: req.body.edited
        }
    }, function (err, result) {
        if (err) console.log(err);

        console.log(result);
        res.send(result);
    });
};

exports.receipts_delete_receipt = function (req, res) {
    let id = req.params.receiptId;

    Receipt.findByIdAndRemove(id, function (err, result) {
        if (err) console.log(err);

        console.log(result);
        res.send(result);
    });
};

exports.receipts_get_all_article = function (req, res) {
    let id = req.params.receiptId;
    console.log(id);
    // Article.find({})
    // .exec( function(err, result){
    // if (err) console.log(err);
    // console.log(result);
    // res.send(result);
    // });

    Receipt.findById(id)
        .select("articles")
        .populate("articles")

        .exec(function (err, receipt) {
            if (err) return console.log(err);

            console.log(receipt);
            console.log(receipt === JSON);
            res.send(receipt.articles);
        });
};

exports.receipts_create_article = function (req, res) {

    let article = new Article({

        name: req.body.name,
        participation: req.body.participation,
        receipt: req.body.receipt,
        price: req.body.price,
        category: req.body.category,
        amount: req.body.amount,
        total: req.body.total,
        edited: req.body.edited
    });

    console.log(article);
    receipt.save(function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
};


exports.receipts_get_single_article = function (req, res) {
    let id = req.params.articleId;

    Article.findById(id, function (err, result) {
        if (err) console.log(err);

        console.log(result);
        res.send(result);
    });
};

exports.receipt_update_article = function (req, res) {
    let id = req.params.articleId;
    Article.findByIdAndUpdate(id, {

        $set: {
            name: req.body.name,
            participation: req.body.participation,
            receipt: req.body.receipt,
            price: req.body.price,
            category: req.body.category,
            amount: req.body.amount,
            total: req.body.total,
            edited: req.body.edited
        }
    }, function (err, result) {
        if (err) console.log(err);

        console.log(result);
        res.send(result);
    });
};

exports.receipts_delete_article = function (req, res) {

    let id = req.params.articleId;

    Article.findByIdAndRemove(id, function (err, result) {
        if (err) console.log(err);

        console.log(result);
        res.send(result);
    });

};
