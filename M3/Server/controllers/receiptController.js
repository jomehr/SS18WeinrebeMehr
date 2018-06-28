let Receipt = require("../models/receipt");
let Group = require("../models/group");
let User = require("../models/user");
let Adress = require("../models/address");
let Article = require("../models/article");
//let Category = require("../models/category");

//let users = require("./userController")

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

        receiptID = result._id;
        res.status(202).end();

        req.body.articles.forEach(function (item) {
            let article = new Article({
                receipt: receiptID,
                name: item.name,
                price: item.price
            });

            article.save(function (err, result) {
                if (err) console.log(err);
                Receipt.findByIdAndUpdate(
                    receiptID, {$addToSet: {articles: result._id}}, {new:true},
                    function(err, result) {
                        if (err) console.log(err);
                    })
            })
        })
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

    // Article.find({})
    // .exec( function(err, result){
    // if (err) console.log(err);
    // console.log(result);
    // res.send(result);
    // });

//Kann leider nur über eine ID querien: receiptId. ArticleDetails sind in einer eigenen Entity
    Receipt.findById(id)
        .select("articles")
        //.populate("articles", "name")  //nur ein populate möglich
        // .populate("articles", "total")
        // .populate("articles", "price")
        .exec(function (err, receipt) {
            if (err) return console.log(err);

            console.log(receipt.article);
            res.send(receipt);
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
