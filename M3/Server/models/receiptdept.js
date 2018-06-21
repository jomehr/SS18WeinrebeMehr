const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const receiptdeptSchema = new Schema({

  creditor: {type: Schema.Types.ObjectId, ref: "User", required: true},
  receipt: {type: Schema.Types.ObjectId, ref: "Receipt", required:true},
  articles: [{type: Schema.Types.ObjectId, ref: "Article"}],
  totaldept: {type: Schema.Types.ObjectId, ref: "Totaldept"},
  dept: Number,
  paid: {type: Boolean, default: false}
});

module.exports = mongoose.model("Receiptdept", receiptSchema);
