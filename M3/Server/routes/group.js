const express = require("express");
let router = express.Router();
let groupController = require("../controllers/groupController");
let userController = require("../controllers/userController");

router.get("/", groupController.groups_get_all);
router.post("/", groupController.groups_create_group);
router.get("/:groupId/", groupController.groups_get_single);
router.patch("/:groupId", groupController.groups_update_group);
router.delete("/:groupId", groupController.groups_delete_group);
//-------------------------------------------------------------
/*
router.get("/:groupId/participants",
groupController.groups_get_participants);
router.post("/:groupId/participants",
groupController.groups_create_participant);
router.delete("/:groupId/participants"),
groupController.groups_delete_participant);
//--------------------------------------------------------------
router.get("/:groupId/settlements", groupController.groups_get_settlement);
*/

module.exports = router;
