const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const articleSchema = new Schema ({

    name: {type: String, required: true},
    participation: [{type: Schema.Types.ObjectId, ref: "Participation" }],
    receipt: {type: Schema.Types.ObjectId, ref: "Receipt" },
    priceTotal: {type: Number, required: true},
    priceSingle: {type: Number, required: true},
    amount: {type: Number, required: true},
    category: [{type: Schema.Types.ObjectId, ref: "Category"}],
    edited: {type: Boolean, default: false}
});


module.exports = mongoose.model("Article", articleSchema);
