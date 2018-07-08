let Settlement = require("../models/settlement");
let TotalDebt = require("../models/totaldebt");
let ReceiptDebt = require("../models/receiptdebt");

let admin = require("firebase-admin");

exports.distribute_debts = function (method, receiptData, articleData, participationData) {
//TODO: implement async module to better control order of function calls and avoid "callback hell"

    switch (method) {
        case "POST":

            let group = receiptData.group;
            let settlement;

            Settlement
                .findOne({group: group})
                .exec(function (err, result) {
                    if (err) console.log(err);
                    settlement = result;
                    let settlementId = result._id;

                    if (settlement.totalDebts.length > 0) {
                        console.log("TotalDebts already exist in Settlement " + settlementId);
                        TotalDebt
                            .findOne({settlement: settlementId, debtor: participationData.participant})
                            .select("_id totalDebt receiptDebt")
                            .exec(function (err, result) {
                                if (err) console.log(err);
                                if (!result) {
                                    console.log("TotalDebt of Participant " + participationData.participant + " doesn't exist!");
                                    createTotalDebt(settlementId, receiptData, articleData, participationData);
                                } else {
                                    let totalDebtResult = result;
                                    console.log("TotalDebt of Participant " + participationData.participant + " already exists!");
                                    if (totalDebtResult.receiptDebt.length > 0) {
                                        console.log("ReceiptDebts already exists in TotalDebt");
                                        ReceiptDebt
                                            .findOne({totalDebt: totalDebtResult._id, receipt: receiptData._id})
                                            .select("_id articles debt")
                                            .exec(function (err, result) {
                                                if (err) console.log(err);
                                                if (!result) {
                                                    console.log("ReceiptDebt of current receipt doesn't exist");
                                                    createReceiptDebt(totalDebtResult, receiptData, articleData, participationData);
                                                } else {
                                                    console.log("ReceiptDebt of current receipt already exists");
                                                    updateReceiptDebt(result, articleData, participationData);
                                                }
                                            });
                                    } else {
                                        createReceiptDebt(totalDebtResult, receiptData, articleData, participationData);
                                    }
                                }
                            });
                    } else {
                        console.log("There are no TotalDebts in Settlement " + settlementId + " yet");
                        createTotalDebt(settlementId, receiptData, articleData, participationData);
                    }
                });
            break;
        case "PATCH":

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

        case "DELETE":

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
            console.log("NANI?? Das sollte nie erscheinen...");
    }
};

calculateDebt = function (articleData, participationData) {
    return articleData.priceTotal * (participationData.percentage / 100)
};

updateReceiptDebt = function (oldReceiptDebt, articleData, participationData) {
    console.log("Updating ReceiptDebt...");

    let newArticleArray = oldReceiptDebt.articles.push(articleData._id);
    let newDebt = oldReceiptDebt.debt += calculateDebt(articleData, participationData);

    let update = {
        debt: newDebt,
        articles: newArticleArray
    };

    ReceiptDebt
        .findByIdAndUpdate(oldReceiptDebt._id, update, {new: true})
        .exec(function (err, result) {
            if (err) console.log(err);
        });
};

createReceiptDebt = function (totalDebtId, receiptData, articleData, participationData) {
    console.log("Creating ReceiptDebt...");

    let curDebt;
    let fcmtmp = 0;
    let articleArray = [];
    articleArray.push(articleData._id);

    if (receiptData.owner.toString() === participationData.participant.toString()) {
        curDebt = receiptData.total - calculateDebt(articleData, participationData);
        console.log("CreditorDebt: "+curDebt);
    } else {
        curDebt = -calculateDebt(articleData, participationData);
        console.log("DebtorDebt: "+curDebt);
    }

    let receiptDebt = new ReceiptDebt({
        creditor: receiptData.owner,
        debtor: participationData.participant,
        receipt: receiptData._id,
        articles: articleArray,
        totalDebt: totalDebtId,
        debt: curDebt
    });

    receiptDebt.save(function (err, result) {
        if (err) console.log(err);

        if (fcmtmp === 0){
            fcmtmp = 1;
            let message = {
                //data payload, used to trigger changes in app
                data: {
                    activity: "4",
                    dataId: result._id.toString()
                },
                //notification data for statusbar
                notification: {
                    title: "Neue Abrechnung",
                    body: "Es wurde eine Abrechnung eines neuen Kassenzettels erstellt"
                },
                topic: "settlement"
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


        TotalDebt.findByIdAndUpdate(
            totalDebtId,
            {$addToSet: {receiptDebt: result._id}, $inc: {totalDebt: result.debt}},
            {new: true},
            function (err, result) {
                if (err) console.log(err);
            });
    });
};

createTotalDebt = function (settlementId, receiptData, articleData, participationData) {
    console.log("Creating TotalDebt...");

    let totalDebt = new TotalDebt({
        debtor: participationData.participant,
        settlement: settlementId,
    });

    totalDebt.save(function (err, result) {
        if (err) console.log(err);
        let totalDebt = result;

        createReceiptDebt(totalDebt._id, receiptData, articleData, participationData);

        Settlement.findByIdAndUpdate(
            settlementId, {$addToSet: {totalDebts: totalDebt._id}}, {new: true},
            function (err) {
                if (err) console.log(err);
            });
    });
};
