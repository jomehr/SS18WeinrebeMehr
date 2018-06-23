const express = require("express");


let router = express.Router();

let settlementController = require("../controllers/settlementController");


router.get("/", settlementController.settlements_get_all);
router.get("/:settlementId/", settlementController.settlements_get_single);
router.patch("/:settlementId", settlementController.settlements_update_settlement);
//--------------------------------------------------------------------------
router.get("/:settlementId/depts",
settlementController.settlements_get_totaldept);
// router.get("/:settlementId/depts/:deptId",
// settlementController.settlements_get_dept);
// router.patch("/:settlementId/depts/:deptId",
// settlementController.settlements_update_dept);
// router.delete("/:settlementId/depts/:deptId",
// settlementController.settlements_delete_dept);
// //---------------------------------------------------------------------------
// router.get("/:settlementId/receipts", settlementController.settlements_get_receipts);
// router.post("/:settlementId/receipts", settlementController.settlement_create_receipt);
// router.delete("/:settlementId/receipts", settlementController.settlements_delete_receipt);


module.exports = router;
