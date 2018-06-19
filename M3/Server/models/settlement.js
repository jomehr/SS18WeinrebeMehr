const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const articleDebtSchema = new Schema ({
    articleId: {type: Schema.Types.ObjectId, ref: "Article"},
    articleName: String,
    debt: Number
});

const debtSchema = new Schema ({
    userId:{type: Schema.Types.ObjectId, ref: "User"},
    articles: [{type: articleDebtSchema}],
    deptTotal: Number
});

const settlementSchema = new Schema ({
    creditor: {type: Schema.Types.ObjectId, ref: "User", required: true},
    debtor: [{type: debtSchema}],
    receipts: [{type: Schema.Types.ObjectId, ref: "Receipt"}],
    total: Number,
    finished: {type: Boolean, default: false}
});

module.exports = mongoose.model("Settlement", settlementSchema);