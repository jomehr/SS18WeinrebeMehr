const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const categorySchema = new Schema ({


      name: {type: String, required: true},
      article: [{type: Schema.Types.ObjectId, ref: "Article"}]
});

module.exports = mongoose.model("Category", categorySchema);
