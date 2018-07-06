const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const participationSchema = new Schema ({

    participant: {type: Schema.Types.ObjectId, ref: "User", required: true},
    receipt: {type: Schema.Types.ObjectId, ref: "Receipt", required: true},
    article: {type: Schema.Types.ObjectId, ref: "Article", required: true},
    suggestion: {type: Schema.Types.ObjectId, ref: "Suggestion"},
    percentage: Number
});


module.exports = mongoose.model("Participation", participationSchema);
