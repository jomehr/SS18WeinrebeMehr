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



exports.distribute_debts = function (receiptData, articleData, participationData ) {



    switch (method) {
        case "post":

            let group = receiptData.group;
            let settlement;

            Settlement.find({group: group})
                .exec(function (err, result) {
                if (err) console.log(err);
                    settlement = result;
                });


            receiptData.participants.forEach(function (participantID){
               if(settlement.totaldebts != null){
                   settlement.totaldebts.forEach(function (totalDebt) {
                       if(totalDebt.debtor === participantID) {
                           console.log("Erstelle Kassenzettelschuld");
                           let receiptdebt = new Receiptdebt({

                               creditor: req.body.owner,
                               receipt: req.body.receiptId,
                               debt: req.body.debt
                           });
                           receiptdebt.save(function (err, result) {
                               if (err) console.log(err);
                               res.send(result);
                           });
                       }else{
                           console.log("Erstelle Totale Schuld");
                       }

                   })
               }else{
                   console.log("Erstelle Totale Schuld");
               }
            });




            //     if (err) return console.log(err);
            // settlement.receipts.forEach(function (receiptId) {
            /*Receipt.findById(rec_id,
                req.body.participants.forEach(function (rec_id) {
                    if (settlement.totaldebts != null) {
                        settlement.totaldebts.forEach(function (totaldebt) {
                            if (totaldebt.debtor === receipt.participants) {
                                console.log("Erstelle Kassenzettelschuld");

                                //Erstelle Kassenzettelschuld
                                let receiptdebt = new Receiptdebt({

                                    creditor: req.body.owner,
                                    receipt: req.body.receiptId,
                                    debt: req.body.debt
                                });
                                receiptdebt.save(function (err, result) {
                                    if (err) console.log(err);
                                    res.send(result);
                                });

                            } else {
                                console.log("Erstelle Totale Schuld");
                                //Erstelle Totale Schuld
                                let id = req.params.receiptdebtId;

                                Receiptdebt.findById(id,


                                    totaldebt = new Totaldebt({

                                        debtor: req.body.debtor,
                                        settlement: req.body.settlement,
                                        receiptdebt: req.body.receiptdebt
                                    })
                                )

                                totaldebt.save(function (err, result) {
                                    if (err) console.log(err);

                                    res.send(result);
                                });
                            }
                        });*/
                        /*  }else{
                               console.log("Erstelle Totale Schuld");
                                //Erstelle Totale Schuld
                              let id = req.params.receiptdebtId;

                              Receiptdebt.findById(id,

                                  let totaldebt =  new Totaldebt({

                                  debtor: req.body.debtor,
                                  settlement:req.body.settlement,
                                  receiptdebt:req.body.receiptdebt
                              })
                          )

                              totaldebt.save(function (err,result){
                                  if(err) console.log(err);

                                  res.send(result);
                              });

                          };*/

                        // });
                        //res.status(202).send(settlement);


            //         }
            //         ;
            //     })
            // );
            break;
        case "patch":
            settlement.totaldebts.forEach(function (totaldebt) {
                if (totaldebt.debtor === receipt.participants) {
                    totaldebt.receiptdept.forEach(function (totaldeptId) {
                        if (receiptdebt.receibt === receipt) {
                            console.log("Berechne neue Kassenzettelschuld");
                        } else {
                            console.log("Berechne neue Totale Schuld");
                        }
                    });
                } else {
                    console.log("Berechne neue Totale Schuld");
                }
            });
            break;
        case "delete":
            settlement.totaldebts.forEach(function (totaldebt) {
                totaldebt.receiptdebt.forEach(function (receiptdebt) {
                    if (receiptdebt.receipt === receipt) {
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
        console.log("Erstelle Schuld");

    };
};

/*exports.logic_create_receiptdebt = function (req, res) {
    let id = req.params.receiptId;

    Receipt.findById(id, function (err) {
        if (err) console.log(err);


        let receiptdebt = new Receiptdebt({

            creditor: req.body.owner,
            receipt: req.body.receipt,
            debt: req.body.debt
        });
        receiptdebt.save(function (err, result) {
            if (err) console.log(err);
            res.send(result);
        });

    });


};

exports.logic_create_totaldebt = function (req, res) {
    let id = req.params.receiptdebtId;

    Receiptdebt.findById(id,

        totaldebt = new Totaldebt({

            debtor: req.body.debtor,
            settlement: req.body.settlement,
            receiptdebt: req.body.receiptdebt
        })
    )

    totaldebt.save(function (err, result) {
        if (err) console.log(err);

        res.send(result);
    })

};

exports.logic_calc_receiptdebt = function (req, res) {
    let id = req.params.receiptId;

    receipt.findById(id,
        function (err) {
            if (err) console.log(err)

            receiptdebt.findByIdAndUpdate(req.params.receiptdebtId,
                function (err) {
                    if (err) console.log(err)

                    req.body.articles.forEach(
                        req.body.article.participation.forEach(participationId,


                    if (req.body.article.participant === receiptdebt.debtor) {

                        let receiptdebt
                    .
                        debt += article.price * article.participation.percentage;

                    }
                });

            receiptdebt.save(function (err, result) {
                debt, {$addToSet: {debt: result}}, {new: true},
                    function (err) {
                        if (err) console.log(err)
                    };
            };
        });
}
)
;
})
;
})
;
}
;*/
