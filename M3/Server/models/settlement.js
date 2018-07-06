const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const settlementSchema = new Schema ({

    startDate: {type: Date, valid: Boolean, required: true},
    endDate:   {type: Date, valid: Boolean},
    group: {type: Schema.Types.ObjectId, ref: "Group"},
    totalDebts:[{type: Schema.Types.ObjectId, ref:"TotalDebt"}],
    receipts: [{type: Schema.Types.ObjectId, ref: "Receipt"}],
    total: Number,
    finished: {type: Boolean, default: false}
});

module.exports = mongoose.model("Settlement", settlementSchema);
