const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const articleSchema = new Schema ({
    name: {type: String, required: true},
    price: {type: Number, required: true},
    category: [{type: Schema.Types.ObjectID, ref: "Category"}],
    amount: Number,
    priceAll: Number
});

module.exports = mongoose.model("Article", articleSchema);