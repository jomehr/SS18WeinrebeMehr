let Group = require("../models/group");
let User = require("../models/user");
let Settlement = require("../models/settlement");

exports.groups_get_all = function (req, res) {
    Group.find({}, function (err, result) {
        if (err) console.log(err);

        res.send(result);

        //DEV CONSOLE OUTPUT
        let copy = [];
        console.log(result.length + " Gruppen in DB gefunden");
        result.forEach(function (group, index) {
            copy.push(group._id);
            console.log("Gruppe " + (index + 1) + " mit ID: " + group._id.toString());
        });
        console.log(copy)
    });
};

exports.groups_create_group = function (req, res) {

    let group = new Group({
        name: req.body.name,
        creator: req.body.creator,
        startDate: req.body.startDate,
        endDate: req.body.endDate,
        participants: req.body.participants,
        startLocation: req.body.startLocation,
        endLocation: req.body.endLocation,
        finished: req.body.finished,
        interval: req.body.interval
    });

    group.save(function (err, result) {
        if (err) console.log(err);

        console.log(result);
        res.send(result);
    });
};

exports.groups_get_single = function (req, res) {
    let id = req.params.groupId;

    Group.findById(id)
        .populate("creator", "name")
        .populate("receipts")
        .exec(function (err, result) {
            if (err) console.log(err);

            console.log(result);
            res.send(result);
        });
};

exports.groups_update_group = function (req, res) {
    let id = req.params.groupId;

    Group.findByIdAndUpdate(id,
        {
            $set:
                {
                    name: req.body.name,
                    creator: req.body.creator,
                    startDate: req.body.startDate,
                    endDate: req.body.endDate,
                    startLocation: req.body.startLocation,
                    endLocation: req.body.endLocation,
                    finished: req.body.finished,
                    interval: req.body.interval
                }
        },
        {new: true},
        function (err, result) {
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

exports.groups_get_participants = function (req, res) {
    let id = req.params.groupId;

    Group.findById(id, function (err, result) {
        if (err) console.log(err);

        console.log(result.participants);
        res.send(result.participants);
    });
};

exports.groups_add_participant = function (req, res) {
    let id = req.params.groupId;

    console.log(req.body.participants);

    Group.findByIdAndUpdate(
        id, {$addToSet: req.body}, {new: true}, function (err, result) {
            if (err) console.log(err);

            console.log(result.participants);
            res.send(result.participants);
        });
};

exports.groups_remove_participant = function (req, res) {
    let id = req.params.groupId;
    let participant = req.params.participantId;

    Group.findByIdAndUpdate(
        id, {$pull: {participants: participant}}, {new: true}, function (err, result) {
            if (err) console.log(err);

            console.log(result.participants);
            res.send(result.participants);
        });
};

exports.groups_get_settlements = function (req, res) {
    let id = req.params.groupId;

    Group.findById(id, function (err, result) {
        if (err) console.log(err);

        console.log(result.settlements);
        res.send(result.settlements);
    });
};

exports.groups_add_settlement = function (req, res) {
    let id = req.params.groupId;

    Group.findByIdAndUpdate(id,
        {
            $push:
                {
                    settlements: req.body.settlements
                }
        },
        {new: true},
        function (err, result) {
            if (err) console.log(err);

            console.log(result);
            res.send(result);

            console.log(result.settlements);
            res.send(result.settlements);
        });
};

exports.groups_remove_settlement = function (req, res) {
    let id = req.params.groupId;

    Group.findByIdAndUpdate(id,
        {
            $pull:
                {
                    settlements: req.body.settlements
                }
        },
        {new: true},
        function (err, result) {
            if (err) console.log(err);

            console.log(result);
            res.send(result);

            console.log(result.settlements);
            res.send(result.settlements);
        });
};

exports.groups_get_receipts = function (req, res) {
    let id = req.params.groupId;

    Group.findById(id, function (err, result) {
        if (err) console.log(err);

        console.log(result.receipts);
        res.send(result.receipts);
    });
};

exports.groups_add_receipt = function (req, res) {
    let id = req.params.groupId;

    Group.findByIdAndUpdate(id,
        {
            $push:
                {
                    receipts: req.body.receipts
                }
        },
        {new: true},
        function (err, result) {
            if (err) console.log(err);

            console.log(result);
            res.send(result);

            console.log(result.receipts);
            res.send(result.receipts);
        });
};

exports.groups_remove_receipt = function (req, res) {
    let id = req.params.groupId;

    Group.findByIdAndUpdate(id,
        {
            $pull:
                {
                    receipts: req.body.receipts
                }
        },
        {new: true},
        function (err, result) {
            if (err) console.log(err);

            console.log(result);
            res.send(result);

            console.log(result.receipts);
            res.send(result.receipts);
        });
};
