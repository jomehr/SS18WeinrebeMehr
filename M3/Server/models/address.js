const mongoose = require("mongoose");

const Schema = mongoose.Schema;

//wird zurzeit nicht benutzt. Alle Adressen sind in den dazugehörigen Modellen eingebaut
const addressSchema = new Schema ({
    street: {streetName: String, streetNumber: Number},
    location: {zipCode: Number, city: String, state: String},
    country: String
});

module.exports = mongoose.model("Address", addressSchema);