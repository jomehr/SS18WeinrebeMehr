let Settlement = require("../models/settlement");
let TotalDebt = require("../models/totaldebt");
let ReceiptDebt = require("../models/receiptdebt");


exports.distribute_debts = function (method, receiptData, articleData, participationData) {


    switch (method) {
        case "post":

            let group = receiptData.group;
            let settlement;
            let debt = 0;
            //let article = articleData;
            let participation = participationData;


            Settlement.findOne({group: group})
                .exec(function (err, result) {
                        if (err) console.log(err);
                        settlement = result;
                        // let settlementId = result._id;
                        receiptData.participants.forEach(function (participantID) {
                            //  console.log("TEST 0 :" + participantID);
                            if (settlement.totalDebts.length !== 0) {
                                settlement.totalDebts.forEach(function (totalDebt) {
                                    if (totalDebt.debtor === participantID) {
                                        console.log("1. Erstelle Kassenzettelschuld");
                                        let receiptDebt = new ReceiptDebt({

                                            creditor: receiptData.owner,
                                            receipt: receiptData._id,
                                            articles: receiptData.articles,
                                            totalDebt: totalDebt._id

                                        });

                                        receiptDebt.save(function (err, result) {
                                            if (err) console.log(err);
                                            console.log(result);


                                        });
                                        receiptData.articles.forEach(function (articleData) {
                                            articleData.participation.forEach(function (participationData) {
                                                if (articleData.participation === totalDebt.receiptDebt.debtor) {


                                                    debt = articleData.price * participationData.percentage;
                                                    console.log(debt);

                                                }
                                            });
                                        });
                                    } else {

                                        console.log("2. Erstelle Totale Schuld");

                                        let totalDebt = new TotalDebt({

                                            debtor: receiptData.participants._id,
                                            settlement: result._id

                                        });
                                        totalDebt.save(function (err, result) {
                                            if (err) console.log(err);
                                            totalDebt = result;
                                        });


                                        console.log("2. Erstelle Kassenzettelschuld");

                                        let articleID = articleData._id;
                                        let participationID = participationData._id;

                                        receiptData.articles.forEach(function (articleID) {
                                            articleData.participation.forEach(function (participationID) {

                                                 // if (participation.participant._id === participantID) {

                                                      console.log("TEST - priceTotal: " + articleData.priceTotal);
                                                      console.log("test2 - percentage: " + participation.percentage);
                                                      debt = articleData.priceTotal * participation.percentage;
                                                      console.log("TEST3 - debt: " + debt);

                                                  }

                                                  }
                                            });
                                        });

                                                // }
                                        debt += debt;


                                        let receiptDebt = new ReceiptDebt({

                                            creditor: receiptData.owner,
                                            debtor: participantID,
                                            receipt: receiptData._id,
                                            articles: receiptData.articles,
                                            totalDebt: result,
                                            debt: debt

                                        });


                                        receiptDebt.save(function (err, result) {
                                            if (err) console.log(err);
                                            console.log(result);
                                            receiptDebt = result;

                                        });


                                      //  });
                                    }
                                });
                            }
                            else {
                                console.log("3. Erstelle Totale Schuld");

                                let totalDebt = new TotalDebt({

                                    debtor: receiptData.participants,
                                    settlement: group.settlement

                                });
                                totalDebt.save(function (err, result) {
                                    if (err) console.log(err);
                                    totalDebt = result;
                                });


                                console.log("3. Erstelle Kassenzettelschuld");
                                let receiptDebt = new ReceiptDebt({

                                    creditor: receiptData.owner,
                                    receipt: receiptData,
                                    debtor: participantID

                                });

                                receiptDebt.save(function (err, result) {
                                    if (err) console.log(err);
                                    console.log(result);
                                    receiptDebt = result;

                                    receiptData.articles.forEach(function (articleData) {
                                        articleData.participation.forEach(function (participationData) {
                                            if (articleData.participation === participantID) {

                                                receiptDebt.debt += articleData.price * participationData.percentage;
                                                console.log(receiptDebt.debt);

                                            }
                                        });
                                    });

                                });

                            }
                        });
                    }
                )
            ;

            break;
        case
        "patch"
        :

            Settlement.findOne({group: group})
                .exec(function (err, result) {
                    if (err) console.log(err);
                    settlement = result;

                    settlement.totalDebts.forEach(function (totalDebt) {
                        if (totalDebt.debtor === receiptData.participants) {
                            totalDebt.receiptDebt.forEach(function (totalDebt) {
                                receiptData.articles.forEach(function (articleData) {
                                    articleData.participation.forEach(function (participationData) {
                                        if (articleData.participation === totalDebt.receiptDebt.debtor) {

                                            totalDebt.receiptDebt.debt += articleData.price * participationData.percentage;
                                            console.log(totalDebt.receiptDebt.debt);

                                        }
                                        else {
                                            console.log("Erstelle neue Totale Schuld");

                                            let totalDebt = new TotalDebt({

                                                debtor: receiptData.participants._id,
                                                settlement: group.settlement

                                            });
                                            totalDebt.save(function (err, result) {
                                                if (err) console.log(err);
                                                let totalDebtId = result._id;

                                                Settlement.findByIdAndUpdate(
                                                    settlement._id, {$addToSet: {totalDebts: totalDebtId}}, {new: true},
                                                    function (err) {
                                                        if (err) console.log(err);
                                                    });
                                            });
                                        }
                                    });

                                });
                            });

                        } else {
                            console.log("Erstelle neue Totale Schuld");

                            let totalDebt = new TotalDebt({

                                debtor: receiptData.participants._id,
                                settlement: group.settlement

                            });
                            totalDebt.save(function (err, result) {
                                if (err) console.log(err);
                                let totalDebtId = result._id;

                                Settlement.findByIdAndUpdate(
                                    settlement._id, {$addToSet: {totalDebts: totalDebtId}}, {new: true},
                                    function (err) {
                                        if (err) console.log(err);
                                    });
                            });
                        }

                    });
                });
            break;

        case
        "delete"
        :

            Settlement.findOne({group: group})
                .exec(function (err, result) {
                    if (err) console.log(err);
                    settlement = result;

                    settlement.totalDebts.forEach(function (totalDebt) {
                        totalDebt.receiptDebt.forEach(function (receiptDebt) {
                            if (receiptDebt.receipt === receiptData) {
                                totalDebt.totalDebt -= receiptDebt.debt;

                                receiptDebt.findByIdAndRemove(receiptDebt._id, function (err) {
                                    if (err) console.log(err);
                                });
                            }
                        });
                    });
                });
            break;
        default:
        /*            Settlement.findOne({group: group})
                        .exec(function (err, result) {
                            if (err) console.log(err);
                            console.log("Erstelle totale Schuld");

                            let totalDebt = new TotalDebt({

                                debtor: receiptData.participants,
                                settlement: group.settlement

                            });
                            totalDebt.save(function (err) {
                                if (err) console.log(err);
                            });
                        });*/
    }
}
;


