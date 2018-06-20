const mongoose = require("mongoose");

const Schema = mongoose.Schema;
const TYPE = ["SOLO", "GROUP"];
const CURRENCY = ["DOLLAR", "EURO"];

const receiptSchema = new Schema ({

    type:  {type: String, enum: TYPE}
    owner: {type: Schema.Types.ObjectId, ref: "User", required: true},
    participation: {type: Schema.Types.ObjectId, ref: "User" },
    articles: {type: Schema.Types.ObjectId, ref: "Article", required: true},
    location: {type: Schema.Types.ObjectId, ref: "Address"},
    store: {type: String, required: true},
    date:  {type: Date, default: Date.now()},
    total: {type: Number, required: true},
    payment: {type: Number, required: true},
    change: {type: Number, required: true},
    currency: {type: String, enum: CURRENCY, required:true}, 
    edited: {type: Boolean, required: true}
});

module.exports = mongoose.model("Receipt", receiptSchema);
