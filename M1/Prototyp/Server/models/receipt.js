const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const articleSchema = new Schema ({
    id: {type: Schema.Types.ObjectID, required: true},
    name: {type: String, required: true},
    price: {type: Number, required: true},
    participants: [{type: Schema.Types.ObjectID, ref: "User"}],
    category: [{type: Schema.Types.ObjectID, ref: "Category"}],
    amount: Number,
    priceAll: Number
});

const addressSchema = new Schema ({
    street: {streetName: String, streetNumber: Number},
    location: {zipCode: Number, city: String, state: String},
    country: String
});

const receiptSchema = new Schema ({
    owner: {type: Schema.Types.ObjectID, ref: "User", required: true},
    participants: [{type: Schema.Types.ObjectID, ref: "User"}],
    store: {type: String, required: true},
    date: {type: Date, default: Date.now()},
    imagePath: {type: String, required: true},
    address: {type: addressSchema},
    article: [{type: articleSchema, required: true}],
    total: {type: Number, required: true},
    payment: {type: Number, required: true},
    change: {type: Number, required: true},
});

module.exports = mongoose.model("Receipt", receiptSchema);