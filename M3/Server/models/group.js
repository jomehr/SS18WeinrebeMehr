const mongoose = require("mongoose");

const Schema = mongoose.Schema;

//enums
const INTERVAL = ["DAY", "WEEK", "MONTH", "RECEIPT"];
const METHOD   = ["PREPAID","ONLINE", "CASH"];

const groupSchema = new Schema ({

    name: {type: String, required: true},
    creator: {type: Schema.Types.ObjectId, ref: "User", required: true},
    startDate: {type: Date, default: Date.now()},
    endDate: Date,
  //  settlement: [{type: Schema.Types.ObjectId, ref: "Settlement"}],
    participants: [{type: Schema.Types.ObjectId, ref: "User"}]
  /*  finished: {type: Boolean, default: false},
    startLocation: {type:Schema.Types.ObjectId, ref:"Address"},
    endLocation: {type: Schema.Types.ObjectId, ref:"Address"},
    finished: Boolean,
    interval: {type: String, enum: INTERVAL},
    method: {type: String, enum: METHOD}*/
});

module.exports = mongoose.model("Group", groupSchema);
