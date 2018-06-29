const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const totaldebtSchema = new Schema({

  //_id: new mongoose.Types.ObjectId,
  debtor: [{type: Schema.Types.ObjectId, ref: "User", required: true}],
  settlement: {type: Schema.Types.ObjectId, ref: "Settlement", required:true},
  receiptdebt: {type: Schema.Types.ObjectId, ref: "Receiptdebt", required:true},
  totaldebt: Number,
  paid: {type: Boolean, default: false}
});

module.exports = mongoose.model("Totaldebt", totaldebtSchema);
