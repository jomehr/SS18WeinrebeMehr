const express = require("express");
let router = express.Router();
let receiptController = require("../controllers/receiptController");
// let settlementController = require("../controllers/settlementController");


router.get("/", receiptController.receipts_get_all);
router.get("/:receiptId/", receiptController.receipts_get_single);
router.post("/", receiptController.receipts_create_receipt/*, settlementController.settlements_create_settlement*/);
router.patch("/:receiptId", receiptController.receipts_update_receipt);
router.delete("/:receiptId", receiptController.receipts_delete_receipt);
//----------------------------------------------------------------------
//TODO: add get image
//----------------------------------------------------------------------
/*
router.get("/:receiptId/articles", receiptController.receipts_get_all_article,
receiptController.receipts_get_single_article);
router.post("/:receiptId/articles", receiptController.receipts_create_article);
router.get("/:receiptId/articles/:articleId",
receiptController.receipts_get_single_article);
router.patch("/:receiptId/articles/:articleId",
receiptController.receipt_update_article);
router.delete("/:receiptId/articles/:articleId",
receiptController.receipts_delete_article);
*/
//-----------------------------------------------------------------------
/*router.get("/:receiptId/articles/:articleId/participations",
receiptController.receipts_get_all_participant);
router.post("/:receiptId/articles/:articleId/participations",
receiptController.receipts_create_participant);
router.get("/:receiptId/articles/:articleId/participations/:participantId",
receiptController.receipts_get_single_participant);
router.patch("/:receiptId/articles/:articleId/participations/:participantId",
receiptController.receipt_update_participant);
router.delete("/:receiptId/articles/:articleId/participations/:participantId",
receiptController.receipts_delete_participant);
//--------------------------------------------------------------------------
router.get("/:receiptId/articles/:articleId/participations/:participantId/suggestion",
receiptController.receipt_get_suggestion);
router.post("/:receiptId/articles/:articleId/participations/:participantId/suggestion",
receiptController.receipts_create_suggestion);
router.patch("/:receiptId/articles/:articleId/participations/:participantId/suggestion",
receiptController.receipts_update_suggestion);
router.delete("/:receiptId/articles/:articleId/participations/:participantId/suggestion",
receiptController.receipts_delete_suggestion);
*/
module.exports = router;
