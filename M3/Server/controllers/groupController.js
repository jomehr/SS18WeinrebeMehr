let Group = require("../models/group");
let User = require("../models/user");
//let Participation = require("../models/participation");
//let Settlement = require("../models/settlement");

exports.groups_get_all = function(req, res) {
    Group.find({}, function (err, result) {
        if (err) console.log(err);

        res.send(result);

        //DEV CONSOLE OUTPUT
        let copy = [];
        console.log(result.length + " Gruppen in DB gefunden")
        result.forEach(function (group, index) {
            copy.push(group._id);
            console.log("Gruppe " + (index + 1) + " mit ID: " + group._id.toString());
        });
        console.log(copy)
    });
};

exports.groups_create_group = function (req, res) {
        console.log(req.body);

        let group = new Group({
            name: req.body.name,
            creator: req.body.creator,
            startDate: req.body.startDate,
            endDate: req.body.endDate,
            finished: req.body.finished,
            participants: req.body.participants,
            startLocation: req.body.startLocation,
            endLocation: req.body.endLocation,
            settlement: req.body.settlement,
            finished: req.body.finished,
            interval: req.body.interval
            });
      console.log(group);

      group.save(function (err, result) {
          if (err) console.log(err);

          console.log(result);
          res.send(result);
      });
    };

exports.groups_get_single = function (req, res) {
            let id = req.params.groupId;

            Group.findById(id, function (err, result) {
                if (err) console.log(err);

                console.log(result);
                res.send(result);
            });
        };

exports.groups_update_group = function (req, res) {
            let id = req.params.groupId;

            Group.findByIdAndUpdate(id, {

                $set: {
                    name: req.body.name,
                    creator: req.body.creator,
                    startDate: req.body.startDate,
                    endDate: req.body.endDate,
                    finished: req.body.finished,
                    participants: req.body.participants,
                    startLocation: req.body.startLocation,
                    endLocation: req.body.endLocation,
                    settlement: req.body.settlement,
                    finished: req.body.finished,
                    interval: req.body.interval
                  }
              }, function (err, result) {
                  if (err) console.log(err);

                  console.log(result);
                  res.send(result);
              });
            };

exports.groups_delete_group = function (req, res) {
              let id = req.params.groupId;

              Group.findByIdAndRemove(id, function (err, result) {
                  if (err) console.log(err);

                  console.log(result);
                  res.send(result);
              });
          };
