const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const totaldeptSchema = new Schema({

  //_id: new mongoose.Types.ObjectId,
  deptor: [{type: Schema.Types.ObjectId, ref: "User", required: true}],
  settlement: {type: Schema.Types.ObjectId, ref: "Settlement", required:true},
  receiptdebt: {type: Schema.Types.ObjectId, ref: "Receiptdept", required:true},
  totaldept: Number,
  paid: {type: Boolean, default: false}
});

module.exports = mongoose.model("Totaldept", totaldeptSchema);
