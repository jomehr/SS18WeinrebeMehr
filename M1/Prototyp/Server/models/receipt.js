const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const articleSchema = new Schema ({
    id: {type: Schema.Types.ObjectID, required: true},
    name: {type: String, required: true},
    price: {type: Number, required: true},
    category: [{type: Schema.Types.ObjectID, ref: "Category"}],
    amount: Number,
    priceAll: Number
});


const receiptSchema = new Schema ({
    owner: {type: Schema.Types.ObjectID, ref: "User", required: true},
    participants: [{type: Schema.Types.ObjectID, ref: "User"}],
    store: {type: String, required: true},
    date: {type: Date, default: Date.now()},
    address: {type: Schema.Types.ObjectId, ref: "Address"},
    article: [{type: articleSchema, required: true}],
    total: {type: Number, required: true},
    payment: {type: Number, required: true},
    change: {type: Number, required: true},
});

module.exports = mongoose.model("Receipt", receiptSchema);