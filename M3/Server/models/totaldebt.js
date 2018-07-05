const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const totalDebtSchema = new Schema({

  debtor: {type: Schema.Types.ObjectId, ref: "User"/*, required: true*/},
  settlement: {type: Schema.Types.ObjectId, ref: "Settlement"/*, required:true*/},
  receiptDebt: [{type: Schema.Types.ObjectId, ref: "ReceiptDebt"}],
  totalDebt: Number,
  paid: {type: Boolean, default: false}
});


module.exports = mongoose.model("TotalDebt", totalDebtSchema);
