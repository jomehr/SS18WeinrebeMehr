const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const receiptdebtSchema = new Schema({


  creditor: {type: Schema.Types.ObjectId, ref: "User", required: true},
  receipt:  {type: Schema.Types.ObjectId, ref: "Receipt", required:true},
  debtor: {type: Schema.Types.ObjectId, ref: "User"},
  articles: [{type: Schema.Types.ObjectId, ref: "Article"}],
  totaldebt: {type: Schema.Types.ObjectId, ref: "Totaldebt"},
  debt: Number,
  paid: {type: Boolean, default: false}
});

module.exports = mongoose.model("Receiptdebt", receiptdebtSchema);
