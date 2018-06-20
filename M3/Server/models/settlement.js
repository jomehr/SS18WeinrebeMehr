const mongoose = require("mongoose");

const Schema = mongoose.Schema;

/*
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
*/

const settlementSchema = new Schema ({
    startDate: {type: Date, default: Date.now(), valid: Boolean},
    endDate:   {type: Date, valid: Boolean, required: true},//***
    creditor:  {type: Schema.Types.ObjectId, ref: "User", required: true},
    totaldepts:{type: Schema.Types.ObjectId, ref:"Totaldepts"}
    receipts: [{type: Schema.Types.ObjectId, ref: "Receipt"}],
    total: Number,
    finished: {type: Boolean, default: false}
});

module.exports = mongoose.model("Settlement", settlementSchema);
