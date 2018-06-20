const mongoose = require("mongoose");

const Schema = mongoose.Schema;

//Wird zurzeit nicht verwedent. Ist direkt in receipt.js eingebaut
const articleSchema = new Schema ({
    participants: [{type: Schema.Types.ObjectId, ref: "User" }],
    receipt: {type: Schema.Types.ObjectId, ref: "Receipt" }
    name: {type: String, required: true},
    price: {type: Number, required: true},
    category: [{type: Schema.Types.ObjectID, ref: "Category"}],
    amount: Number,
    total: Number,
    edited: {type: Boolean, default: false}
});


module.exports = mongoose.model("Article", articleSchema);
