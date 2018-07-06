const mongoose = require("mongoose")
const Settlement = require("../models/settlement");

const Schema = mongoose.Schema;

//enums
const INTERVAL = ["DAY", "WEEK", "MONTH", "RECEIPT"];
const METHOD   = ["PREPAID","ONLINE", "CASH"];

const groupSchema = new Schema ({

    name: {type: String, required: true},
    creator: {type: Schema.Types.ObjectId, ref: "User", required: true},
    startDate: {type: Date, default: Date.now()},
    endDate: Date,
    settlements: [{type: Schema.Types.ObjectId, ref: "Settlement"}],
    participants: [{type: Schema.Types.ObjectId, ref: "User"}],
    receipts: [{type: Schema.Types.ObjectId, ref: "Receipt"}],
    finished: {type: Boolean, default: false},
    startLocation: {type:Schema.Types.ObjectId, ref:"Address"},
    endLocation: {type: Schema.Types.ObjectId, ref:"Address"},
    interval: {type: String, enum: INTERVAL, default: "WEEK"},
    method: {type: String, enum: METHOD, default: "CASH"}
});

groupSchema.post("save", function (next) {
   const group = this;
   console.log("Creating Settlement...");

   const settlement = new Settlement ({
       startDate: group.startDate,
       endDate: calculateSettlementEndDate(group.startDate, group.interval),
       group: group._id
   });

   settlement.save(function (err, result) {
       if (err) console.log(err);
       console.log(result);
   })
});

calculateSettlementEndDate  = function (startDate, interval) {

    //Milliseconds of interval
    const MONTH = 30 * 7 * 24 * 60 * 60 * 1000;
    const WEEK  = 7 * 24 * 60 * 60 * 1000;
    const DAY = 24 * 60 * 60 * 1000;

    switch (interval) {
        case interval = "MOTNH":
            return new Date(startDate.getTime() + MONTH);
        case interval = "WEEK":
            return new Date(startDate.getTime() + WEEK);
        case interval = "DAY":
            return new Date(startDate.getTime() + DAY);
        case interval = "RECEIPT":
            return null;
        default:
            return new Date(startDate.getTime() + WEEK);
    }
};

module.exports = mongoose.model("Group", groupSchema);
