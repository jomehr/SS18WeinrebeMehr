let Group = require("../models/group");
let User = require("../models/user");
let Receipt = require("../models/receipt");
let Adress = require("../models/address");

exports.users_get_all = function (req, res) {
  User.find({}, function (err, data) {
      if (err) console.log(err);
      res.send(data);

      //DEV CONSOLE OUTPUT
      console.log(data.length + " User in DB gefunden");
      data.forEach(function (user, index) {
      console.log("User "+ (index + 1) +" mit ID: " + user._id.toString());
      });
  });
  };

exports.users_create_user = function (req, res) {

        let user = new User({
          name: req.body.name,
          address: req.body.address,
          email: req.body.email,
          password: req.body.password,
          dateofbirth: req.body.dateofbirth,
          group: req.body.group,
          pastgroups: req.body.pastgroups,
          //receipts: req.body.receipts//,
          //ratings: req.body.ratings,
          paypal: req.body.paypal

        });
        console.log(user);
        user.save(function (err, result) {
            if (err) console.log(err);
            res.send(result);
        });
    };

exports.users_get_single = function (req, res) {
        let id = req.params.userId;

        user.findById(id, function (err, result) {
            if (err) console.log(err);

            console.log(result);
            res.send(result);
        });
    };

exports.users_update_user = function (req, res) {
        let id = req.params.userId;

      User.findByIdAndUpdate(id, {

          $set: {
            name: req.body.name,
            address: req.body.address,
            email: req.body.email,
            password: req.body.password,
            dateofbirth: req.body.dateofbirth,
            group: req.body.group,
            pastgroups: req.body.pastgroups,
            //receipts: req.body.receipts//,
            //ratings: req.body.ratings,
            paypal: req.body.paypal
          }
      }, function (err, result) {
          if (err) console.log(err);

          console.log(result);
          res.send(result);
      });
    };

  exports.users_delete_user = function (req, res) {
        let id = req.params.userId;

        User.findByIdAndRemove(id, function (err, result) {
            if (err) console.log(err);

            console.log(result);
            res.send(result);
        });
    };
