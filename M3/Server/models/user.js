const mongoose = require("mongoose"),
    bcrypt = require("bcrypt");

const SALT_WORKER_FACTOR = 10;
const Schema = mongoose.Schema;


const userSchema = new Schema ({
    name: {type: String, trim: true, required: true},
    address: {type: Schema.Types.ObjectId, ref: "Adress"},
    email: {type: String, required: true, trim: true, unique: true, match: [/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/, 'Please fill a valid email address']},
    password: {type: String, required: true},
    dateofbirth: Date,
    group: {type: Schema.Types.ObjectId, ref: "Group"},
    pastgroups: [{type: Schema.Types.ObjectId, ref: "Group"}],
    receipts: [{type: Schema.Types.ObjectId, ref: "Receipt"}],
    ratings: [{type: Schema.Types.ObjectId, ref: "Rating"}],
    paypal: String
});

userSchema.pre("save", function (next) {
    let user = this;

    if(!user.isModified("password")) return next();

    bcrypt.genSalt(SALT_WORKER_FACTOR, function (err, salt) {
        if (err) return next(err);

        bcrypt.hash(user.password, salt, function (err, hash) {
            if (err) return next(err);

            user.password = hash;
            next();
        });
    });
});

userSchema.methods.comparePassword = function (userPassword, callback) {
    bcrypt.compare(userPassword, this.password, function (err, isMatch) {
        if (err) return callback(err);
        callback(null, isMatch);
    })
};

module.exports = mongoose.model("User", userSchema);
