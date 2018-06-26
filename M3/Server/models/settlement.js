const mongoose = require("mongoose");

const Schema = mongoose.Schema;


const settlementSchema = new Schema ({


    startDate: {type: Date, default: Date.now(), valid: Boolean},
    endDate:   {type: Date, valid: Boolean/*, required: true*/},//***
    creditor:  {type: Schema.Types.ObjectId, ref: "User", /*required: true*/},
    totaldepts:[{type: Schema.Types.ObjectId, ref:"Totaldept"}],
    receipts: [{type: Schema.Types.ObjectId, ref: "Receipt"}],
    total: Number,
    finished: {type: Boolean, default: false}
});

module.exports = mongoose.model("Settlement", settlementSchema);
