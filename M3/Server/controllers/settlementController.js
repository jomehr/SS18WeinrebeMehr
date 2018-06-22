let Settlement = require("../models/settlement");
let Receipt = require("../models/receipt");
let Group = require("../models/group");
let User = require("../models/user");
// let Participation = require("../models/participation");
// let Adress = require("../models/address");
//let Article = require("../models/article");
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

    Settlement.findByIdAndUpdate(id, {

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



      Receiptdept.find({})
  //    .select("dept")
      .distinct("dept", function(err, result){
        if (err) console.log(err);

        var total=0;
        for(var i in result){

          total += result[i];

        }
          
          console.log(total);


        // console.log(result);


        res.send(result);
      });
      // let x = Receiptdept.find({/*creditor: { $eq:userId}*/}, function(err, result){
      //   if(err) console.log(err);
      //
      //   let copy = Number;
      //   let query1 = x.select().where("dept");
      //   let query2 = x.count();
      //
      //   result.forEach(function(result, query2){
      //
      //   console.log(query1);
      //   console.log(query2);
      //   // parseInt(query1);
      //   //   copy  += query1;
      //   //   console.log(copy);
      //   });
      //   res.send(result);
      // });



};

exports.settlements_get_dept = function (req, res){

};

exports.settlements_update_dept = function (req, res){


};


exports.settlements_delete_dept = function (req, res){

};

//----------------------------------------------------------------

exports.settlements_get_receipts = function (req, res){

};

exports.settlement_create_receipt = function (req, res){

};

exports.settlements_delete_receipt = function (req, res){

};
