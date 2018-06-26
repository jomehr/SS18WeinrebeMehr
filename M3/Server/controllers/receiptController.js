let Receipt = require("../models/receipt");
let Group = require("../models/group");
let User = require("../models/user");
let Adress = require("../models/address");
let logic = require("./logic");
//let Suggestion = require("../models/suggestion");
let Article = require("../models/article");
let Participation = require("../models/participation");

//let Category = require("../models/category");

//let users = require("./userController")

exports.receipts_get_all = function (req, res) {

    let query = Receipt.find({});
    query.select("owner store date paid articles currency").populate("owner", "name");
    query.exec(function (err, result) {
        if (err) console.log(err);

        res.send(result);
    });
/*    Receipt.find({/!*owner: userId*!/})
    .exec(function (err, result) {
        if (err) console.log(err);

        res.send(result);
    });*/
};

exports.receipts_create_receipt = function (req, res) {
    //  let method = req.method;

    let receipt = new Receipt({
        type: req.body.type,
        owner: req.body.owner,
        participants: req.body.participants,
        store: req.body.store,
        date: req.body.date,
        location: req.body.location,
        address: req.body.address,
        articles: req.body.articles,
        total: req.body.total,
        paid: req.body.paid,
        change: req.body.change,
        currency: req.body.currency,
        edited: req.body.edited

        //logic.(receipt.params.receiptId);

    });
    console.log(receipt);
    receipt.save(function (err, result) {
        if (err) console.log(err);
        res.send(result);
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
            participants: req.body.participants,
            store: req.body.store,
            date: req.body.date,
            location: req.body.location,
            address: req.body.address,
            articles: req.body.articles,
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
        .populate("articles", "name total price")
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

exports.receipts_get_all_participation = function(req, res){




  Participation.find({})
  .populate("participant")
  .exec(function (err, result) {
      if (err) console.log(err);

      console.log(result);
      res.send(result);
  });


  //   , function (err, result) {
  //     if (err) console.log(err);
  //
  //     console.log(result.participation);
  //     res.send(result.participation);
  // });

};

exports.receipts_create_participation = function(req, res){
let id = req.params.articleId;

console.log(req.body.participation);

Article.findByIdAndUpdate(
    id, {$addToSet: req.body}, {new: true}, function (err, result) {
        if (err) console.log(err);

        console.log(result.participation);
        res.send(result.participation);
    });

};

exports.receipts_get_single_participation = function(req, res){
  let id = req.params.participationId;

  Participation.findById(id, function (err, result) {
      if (err) console.log(err);

      console.log(result);
      res.send(result);
  });
};

exports.receipts_update_participation = function(req, res){

  let id = req.params.groupId;

  Participation.findByIdAndUpdate(id,
      {
          $set:
              {
                participant: req.body.participant ,
                receipt: req.body.receipt,
                article: req.body.article,
                suggestion: req.body.suggestion,
                percentage: req.body.percentage
              }
      },
      {new: true},
      function (err, result) {
          if (err) console.log(err);

          console.log(result);
          res.send(result);
      });

};

//-------------------------------------------------------------------------

exports.receipts_get_suggestion = function(req, res){

  let id = req.params.participationId;

  Participation.findById(id, function (err, result) {
      if (err) console.log(err);

      console.log(result.suggestion);
      res.send(result.suggestion);
  });


};

exports.receipts_create_suggestion = function(req, res){

  let id = req.params.participationId;

  console.log(req.body.suggestion);

  Participation.findByIdAndUpdate(
      id, {$addToSet: req.body}, {new: true}, function (err, result) {
          if (err) console.log(err);

          console.log(result.suggestion);
          res.send(result.suggestion);
      });
};

exports.receipts_update_suggestion = function(req, res){
  let id = req.params.suggestionId;

  Suggestion.findByIdAndUpdate(id,
      {
          $set:
              {
                receipt: req.body.receipt,
                percentage: req.body.percentage
              }
      },
      {new: true},
      function (err, result) {
          if (err) console.log(err);

          console.log(result);
          res.send(result);
      });

};

exports.receipts_delete_suggestion = function(req,res){

  let id = req.params.suggestionId;

  Suggestion.findByIdAndRemove(id, function (err, result) {
      if (err) console.log(err);

      console.log(result);
      res.send(result);
  });
};
