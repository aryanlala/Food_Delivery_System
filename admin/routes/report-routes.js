// routes/admin-routes.js
const express = require('express');
const { verifyToken } = require('../middlewares/auth-middleware');
const reportController = require('../controllers/report-controller');

const router = express.Router();

// Middleware to verify the token (admin only)
router.use(verifyToken);

// Route to get the most popular restaurants
router.get('/popular-restaurants', reportController.getMostPopularRestaurants);

// Route to get average delivery time
router.get('/average-delivery-time', reportController.getAverageDeliveryTime);

// Route to get order trends
router.get('/order-trends', reportController.getOrderTrends);

router.get('/active-users-by-role', reportController.getActiveUsersByRole);

router.get('/delivery-activity', reportController.getDeliveryActivity);

router.get('/order-statuses', reportController.getOrderStatuses);

module.exports = router;
