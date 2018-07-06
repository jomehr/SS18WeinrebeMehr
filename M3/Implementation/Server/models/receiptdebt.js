const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const receiptDebtSchema = new Schema({

    creditor: {type: Schema.Types.ObjectId, ref: "User", required: true},
    debtor: {type: Schema.Types.ObjectId, ref: "User", required: true},
    receipt:  {type: Schema.Types.ObjectId, ref: "Receipt", required:true},
    articles: [{type: Schema.Types.ObjectId, ref: "Article"}],
    totalDebt: {type: Schema.Types.ObjectId, ref: "TotalDebt"},
    debt: Number,
    paid: {type: Boolean, default: false}

});

module.exports = mongoose.model("ReceiptDebt", receiptDebtSchema);
