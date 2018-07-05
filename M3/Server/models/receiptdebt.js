const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const receiptDebtSchema = new Schema({


  creditor: {type: Schema.Types.ObjectId, ref: "User", required: true},
  receipt:  {type: Schema.Types.ObjectId, ref: "Receipt", required:true},
  debtor: {type: Schema.Types.ObjectId, ref: "User"},
  articles: [{type: Schema.Types.ObjectId, ref: "Article"}],
  totalDebt: {type: Schema.Types.ObjectId, ref: "TotalDebt"},
  debt: Number,
  paid: {type: Boolean, default: false}
});


// receiptDebtSchema.pre("save", function (next) {
//     let receiptDebt = this;
//
//     if(!receiptDebt.isModified("debt")) return next();
//
//    receiptDebt.update("debt", function (err, result) {
//         if (err) return next(err);
//
//         this.update({}, {$set:{ updateAt: new receiptDebt("debt")}});
//
//             next();
//         });
//     });


// mongoose.model('receiptDebt', receiptDebtSchema);
// let ReceiptDebt = mongoose.model('receiptDebt');
//
//
// receiptDebtSchema.pre('validate', true, function(next) {
//     ReceiptDebt.findByIdAndUpdate(this._id, { $set: { debt: this.debt } }, function (err) {
//         if (err) { console.log(err) };
//         next();
//     });
// });

module.exports = mongoose.model("ReceiptDebt", receiptDebtSchema);
