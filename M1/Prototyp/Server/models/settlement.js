const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const settlementSchema = new Schema ({
    creditor: {type: Schema.Types.ObjectId, ref: "User", required: true},
    debtor: [{id:{type: Schema.Types.ObjectId, ref: "User"}, dept: Number}],
    receipts: [{type: Schema.Types.ObjectId, ref: "Receipt"}],
    total: Number,
    finished: {type: Boolean, default: false}
});

module.exports = mongoose.model("Settlement", settlementSchema);