const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const addressSchema = new Schema ({
    street: [{streetName: String}, {houseNumber: Number}],
    location: [{zipCode: Number}, {city: String}],
    country: String
});

module.exports = mongoose.model("Address", addressSchema);