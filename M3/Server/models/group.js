const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const addressSchema = new Schema ({
    street: {streetName: String, streetNumber: Number},
    location: {zipCode: Number, city: String, state: String},
    country: String
});

//enums
const INTERVAL = ["DAY", "WEEK", "MONTH", "RECEIPT"]
const METHOD   = ["PREPAID","ONLINE", "CASH"]

const groupSchema = new Schema ({
    name: String,
    creator: {type: Schema.Types.ObjectId, ref: "User", required: true},
    startDate: {type: Date, default: Date.now()},
    endDate: Date,
    finished: {type: Boolean, default: false},
    participants: [{type: Schema.Types.ObjectId, ref: "User"}],
    startLocation: {type: addressSchema},
    endLocation: {type: addressSchema},
    settlement: [{type: Schema.Types.ObjectId, ref: "Settlement"}],
    finished: Boolean,
    interval: {type: String, enum: INTERVAL},
    method: {type: String, enum: METHOD}
});

module.exports = mongoose.model("Group", groupSchema);
