const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const groupSchema = new Schema ({
    name: String,
    creator: {type: Schema.Types.ObjectID, ref: "User", required: true},
    startDate: {type: Date, default: Date.now()},
    endDate: Date,
    finished: {type: Boolean, default: false},
    participants: [{type: Schema.Types.ObjectID, ref: "User"}],
    startLocation: {type: Schema.Types.ObjectId, ref: "Address"},
    endLocation: {type: Schema.Types.ObjectId, ref: "Address"},
    settlement: [{type: Schema.Types.ObjectId, ref: "Settlement"}]
});

module.exports = mongoose.model("Group", groupSchema);