const express = require("express");
let router = express.Router();
let groupController = require("../controllers/groupController");

router.get("/", groupController.groups_get_all);
router.post("/", groupController.groups_create_group);
router.get("/:groupId/", groupController.groups_get_single);
router.patch("/:groupId", groupController.groups_update_group);
router.delete("/:groupId", groupController.groups_delete_group);
//-------------------------------------------------------------
router.get("/:groupId/participants", groupController.groups_get_participants);
router.post("/:groupId/participants", groupController.groups_add_participant);
router.delete("/:groupId/participants/:participantId", groupController.groups_remove_participant);
router.delete("/:groupId/participants", groupController.groups_remove_participant);
/*
router.get("/:groupId/participants",
groupController.groups_get_participants);
router.post("/:groupId/participants",
groupController.groups_create_participant);
//router.delete("/:groupId/participants"),
//groupController.groups_delete_participant);
//--------------------------------------------------------------

router.get("/:groupId/settlements", groupController.groups_get_settlements);
router.post("/:groupId/settlements", groupController.groups_add_settlement);
router.delete("/:groupId/settlements/:settlementId", groupController.groups_remove_settlement);
//--------------------------------------------------------------
router.get("/:groupId/receipts", groupController.groups_get_receipts);
router.post("/:groupId/receipts", groupController.groups_add_receipt);
router.delete("/:groupId/receipts/receiptId", groupController.groups_remove_receipt);
*/
module.exports = router;
