let Settlement = require("../models/settlement");
let TotalDebt = require("../models/totaldebt");
let ReceiptDebt = require("../models/receiptdebt");



exports.distribute_debts = function (method, receiptData, articleData, participationData ) {


    switch (method) {
        case "post":

            let group = receiptData.group;
            let settlement;

            Settlement.findOne({group: group})
                .exec(function (err, result) {
                    if (err) console.log(err);
                    settlement = result;
                    let settlementId = result._id;
                    receiptData.participants.forEach(function (participantID) {
                        if (settlement.totalDebts.length !== 0) {
                            settlement.totalDebts.forEach(function (totalDebt) {
                                if (totalDebt.debtor === participantID) {
                                    console.log("Erstelle Kassenzettelschuld");
                                    let receiptDebt = new ReceiptDebt({

                                        creditor: receiptData.owner,
                                        receipt: receiptData

                                    });
                                    receiptDebt.save(function (err, result) {
                                        if (err) console.log(err);
                                        console.log(result);
                                    });
                                } else {
                                    console.log("Erstelle Totale Schuld");

                                    let totalDebt = new TotalDebt({

                                        debtor: receiptData.participants,
                                        settlement: group.settlement

                                    });
                                    totalDebt.save(function (err) {
                                        if (err) console.log(err);
                                    });
                                }
                            });
                        } else {
                            console.log("Erstelle Totale Schuld");

                            let totalDebt = new TotalDebt({

                                debtor: participantID,
                                settlement: group.settlement

                            });
                            totalDebt.save(function (err, result) {
                                if (err) console.log(err);
                                let totalDebtId = result._id;

                                Settlement.findByIdAndUpdate(
                                    settlementId, {$addToSet: {totalDebts: totalDebtId}}, {new: true},
                                    function (err) {
                                        if (err) console.log(err);
                                    });
                            });
                        }
                    });

                });
            break;
        case "patch":

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

        case "delete":

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
            console.log("Erstelle totale Schuld");

            let totalDebt = new TotalDebt({

                debtor: receiptData.participants,
                settlement: group.settlement

            });
            totalDebt.save(function (err) {
                if (err) console.log(err);
            });
    }
};


