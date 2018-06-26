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


exports.logic = function (req, res){
  let id = req.params.settlementId;
  let method = req.method;

  switch (method) {
      case "post":
      // Settlement.findById(id, function (err, settlement) {
      //     if (err) return console.log(err);
           // settlement.receipts.forEach(function (receiptId) {
           //     Receipt.findById(receiptId, "participants", function (err, receipt) {
           //         if (err) return console.log(err);
                     receipts.participants.forEach(function(receiptId){
                      if(settlement.totaldebts != null){
                       settlement.totaldebts.forEach(function(totaldebt){
                         if(totaldebt.debtor === receipt.participants){
                           console.log("Neue Kassenzettelschuld");

                            //Erstelle Kassenzettelschuld

                         }else{
                           console.log("Neue Totale Schuld");
                             //Erstelle Totale Schuld

                         }
                       });
                     }else{
                       console.log("Neue Totale Schuld");
                        //Erstelle Totale Schuld

                     }

                    // });
                   //res.status(202).send(settlement);
                  });

            // });
          // });
          break;
      case "patch":
          settlement.totaldebts.forEach(function(totaldebt){
            if(totaldebt.debtor === receipt.participants){
                totaldebt.receiptdept.forEach(function(totaldeptId){
                  if(receiptdebt.receibt === receipt){
                    console.log("Berechne neue Kassenzettelschuld");
                  }else{
                    console.log("Berechne neue Totale Schuld");
                  }
                });
            }else{
              console.log("Berechne neue Totale Schuld");
            }
          });
          break;
          case "delete":
            settlement.totaldebts.forEach(function(totaldebt){
              totaldebt.receiptdebt.forEach(function(receiptdebt){
                if(receiptdebt.receipt === receipt){
                  totaldebt.totaldebt -= receiptdebt.debt;
                };
                let id = req.params.receiptdebtId;

              Receiptdebt.findByIdAndRemove(id, function (err, result) {
                    if (err) console.log(err);

                    console.log(result);
                    res.send(result);
                });
              });
            });

              break;
      default:




  // let totaldebt = new Totaldebt({
  //
  //   deptor: receipt.participants.userId,
  //   settlement: settlement.settlementId,
  //   receiptdebt:receiptdebt.receiptdebtId,
  //   totaldept: totaldebt.totaldebt
  //
  // });
  };
};
