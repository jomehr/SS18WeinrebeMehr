const mongoose = require("mongoose");

const Schema = mongoose.Schema;

//Wird zurzeit nicht verwedent. Ist direkt in receipt.js eingebaut
const articleSchema = new Schema ({

    name: {type: String, required: true},
    participation: [{type: Schema.Types.ObjectId, ref: "Participation" }],
    receipt: {type: Schema.Types.ObjectId, ref: "Receipt" },
    price: {type: Number, required: true},
    //category: [{type: Schema.Types.ObjectID, ref: "Category"}],
    amount: Number,
    total: Number,
    edited: {type: Boolean, default: false}
});


module.exports = mongoose.model("Article", articleSchema);
