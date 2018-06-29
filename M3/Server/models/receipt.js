const mongoose = require("mongoose");
let Receiptdebt = require("./receiptdebt");

const Schema = mongoose.Schema;
const TYPE = ["SOLO", "GROUP"];
const CURRENCY = ["$", "€"];

const receiptSchema = new Schema ({
    type:  {type: String, enum: TYPE, required:true},
    owner: {type: Schema.Types.ObjectId, ref: "User", required: true},
    group: {type: Schema.Types.ObjectId, ref: "Group"},
    participants: [{type: Schema.Types.ObjectId, ref: "User" }],
    articles: [{type: Schema.Types.ObjectId, ref: "Article", required: true}],
    location: {type: Schema.Types.ObjectId, ref: "Address"},
    store: {type: String, required: true},
    date:  {type: Date, default: Date.now()},
    total: {type: Number, required: true},
    paid: {type: Number, required: true},
    change: {type: Number, required: true},
    currency: {type: String, enum: CURRENCY, required:true},
    edited: {type: Boolean, default: false}
});




module.exports = mongoose.model("Receipt", receiptSchema);
