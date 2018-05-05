const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const settlementSchema = new Schema ({
    creditor: {type: Schema.Types.ObjectID, ref: "User", required: true},
    debtor: [{type: Schema.Types.ObjectID, ref: "User"}],
    receipts: [{type: Schema.Types.ObjectID, ref: "Receipt"}],
    total: Number,
    finished: {type: Boolean, default: false}
});

module.exports = mongoose.model("Settlement", settlementSchema);