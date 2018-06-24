let Settlement = require("../models/settlement");
let Receipt = require("../models/receipt");
let Group = require("../models/group");
let User = require("../models/user");
// let Participation = require("../models/participation");
// let Adress = require("../models/address");
let Article = require("../models/article");
//let Category = require("../models/category");
//let Suggestion = require("../models/suggestion");
let Totaldept = require("../models/totaldept");
let Receiptdept = require("../models/receiptdept");


exports.settlements_get_all = function (req, res) {
    Settlement.find({}, function (err, result) {
        if (err) console.log(err);
        res.send(result);

        //DEV CONSOLE OUTPUT
        let copy = [];
        console.log(result.length + " Abrechnungen in DB gefunden")
        result.forEach(function (settlement, index) {
            copy.push(settlement._id);
            console.log("Abrechnung " + (index + 1) + " mit ID: " + settlement._id.toString());
        });
        console.log(copy)
    });
};

exports.settlements_get_single = function (req, res) {
    let id = req.params.settlementId;
    Settlement.findById(id, function (err, result) {
        if (err) console.log(err);
        res.send(result);
    });
};

exports.settlements_update_settlement = function (req, res) {
  let id = req.params.settlementId;

  Settlement.findById(id, {

    $set: {
      startDate: req.body.startDate,
      endDate:   req.body.endDate,
      creditor:  req.body.creditor,
      totaldepts: req.body.totaldepts,
      receipts: req.body.receipts,
      total: req.body.total,
      finished: req.body.finished
    }
  }, function (err, result) {
    if (err) console.log(err);

    console.log(result);
    res.send(result);
  });
};



exports.settlements_get_totaldept = function (req,res){





};

exports.settlements_get_dept = function (req, res){




Receiptdept.find({})

  .distinct("dept",
    function(err, result){
      if (err) console.log(err);

      console.log(result);
      res.send(result);

    });

};

exports.settlements_update_dept = function (req, res){


};


exports.settlements_delete_dept = function (req, res){
  let id = req.params.receiptdId;

  Receiptdept.findByIdAndRemove(id, function (err, result) {
      if (err) console.log(err);

      console.log(result);
      res.send(result);
  });
};

//----------------------------------------------------------------

exports.settlement_update_post = function(req, result) {

  let id = req.params.settlementId;

  Settlement.findById(id, function (err, settlement) {
      if (err) return console.log(err);
      settlement.receipts.forEach(function (receiptId) {
           Receipt.findById(receiptId, "participants", function (err, receipt) {
               if (err) return console.log(err);
                 receipts.participants.forEach(function(receiptId){
                  if(settlement.totaldepts != null);
                   settlement.totaldepts.forEach(function(totaldept){
                     if(totaldept.deptor === receipt.participants){
                        console.log("Neue Kassenzettelschuld");

                        receiptdept.update({

                          $addToSet: {
                              "receipt":{
                                receiptId: receipt.receiptId
                              },
                              "totaldept":{
                                totaldept: totaldept.totaldeptId
                              }
                              }

                        });
                         // {upsert: true, new: true},
                        // function (err) {
                        //     if (err) return console.log(err);}

                     }
                    else{

                    }
          //     receipt.article.forEach(function (article) {
          //         article.participation.forEach(function (participation, index) {
          //             if (article.amount > 1) {
          //                 tmp = article.priceAll * participation.percentage;
          //             } else {
          //                 tmp = article.price * participation.percentage;
          //             }
                     });
                   });
                 });
                });
                res.status(202).send(settlement);
                });
};
//----------------------------------------------------------------

exports.settlements_get_receipts = function (req, res){

  Settlement.find({})
};

exports.settlement_create_receipt = function (req, res){

};

exports.settlements_delete_settlement = function (req, res){


};
