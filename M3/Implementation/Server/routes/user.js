const express = require("express");
let router = express.Router();
let userController = require("../controllers/userController");


router.get("/", userController.users_get_all);
router.get("/:userId/", userController.users_get_single);
router.post("/", userController.users_create_user);
router.patch("/:userId", userController.users_update_user);
router.delete("/:userId", userController.users_delete_user);
//-------------------------------------------------------------
// TODO: GET POST ratings
//-------------------------------------------------------------

module.exports = router;
