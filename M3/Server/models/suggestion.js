const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const suggestionSchema = new Schema ({


    receipt: {type: Schema.Types.ObjectID, ref: "Receipt"},
    percentage: Number,
    accepted: {type:Boolean, required:true}
});

module.exports = mongoose.model("Suggestion", suggestionSchema);
