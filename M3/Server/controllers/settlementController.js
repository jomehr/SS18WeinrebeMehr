let Settlement = require("../models/settlement");
let Receipt = require("../models/receipt");
let Group = require("../models/group");
let User = require("../models/user");
// let Participation = require("../models/participation");
// let Adress = require("../models/address");
let Article = require("../models/article");
//let Category = require("../models/category");
//let Suggestion = require("../models/suggestion");
let Totaldebt = require("../models/totaldebt");
let Receiptdebt = require("../models/receiptdebt");


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

exports.settlements_create_settlement = function (req, res){
  let id = req.params.receiptId;

  let settlement = new Settlement({


  });


  console.log(settlement);
  settlement.save(function (err, result) {
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
      totaldebts: req.body.totaldebts,
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



exports.settlements_get_totaldebt = function (req,res){





};

exports.settlements_get_debt = function (req, res){




Receiptdebt.find({})

  .distinct("debt",
    function(err, result){
      if (err) console.log(err);

      console.log(result);
      res.send(result);

    });

};

exports.settlements_update_debt = function (req, res){


};


exports.settlements_delete_debt = function (req, res){
  let id = req.params.receiptdId;

  Receiptdebt.findByIdAndRemove(id, function (err, result) {
      if (err) console.log(err);

      console.log(result);
      res.send(result);
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
