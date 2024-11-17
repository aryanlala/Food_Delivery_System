const express = require('express');
const { verifyToken } = require('../middlewares/auth-middleware');
const orderController = require('../controllers/order-controller');
const validate = require('../middlewares/validator');
const { updateOrderStatusSchema } = require('../validation/order-validation');

const router = express.Router();

// View all orders
router.get('/', verifyToken, orderController.viewOrders);

// Update order status
router.put('/:id/status', verifyToken, validate(updateOrderStatusSchema), orderController.updateOrderStatus);

module.exports = router;