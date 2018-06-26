const express = require("express");


let router = express.Router();

let settlementController = require("../controllers/settlementController");


router.get("/", settlementController.settlements_get_all);
//router.post("/", settlementController.settlements_create_settlement)
router.get("/:settlementId/", settlementController.settlements_get_single);
router.patch("/:settlementId", settlementController.settlements_update_settlement);
//--------------------------------------------------------------------------
// router.get("/:settlementId/debts",
// settlementController.settlements_get_totaldebt);
router.get("/:settlementId/debts/:receiptdebtId",
settlementController.settlements_get_debt);
// router.patch("/:settlementId/debts/:debtId",
// settlementController.settlements_update_debt);
// router.delete("/:settlementId/debts/:debtId",
// settlementController.settlements_delete_debt);
// //---------------------------------------------------------------------------
// router.get("/:settlementId/receipts", settlementController.settlements_get_receipts);
// router.post("/:settlementId/receipts", settlementController.settlements_create_receipt);
// router.delete("/:settlementId/receipts", settlementController.settlements_delete_receipt);


module.exports = router;
