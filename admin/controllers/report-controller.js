const sequelize = require('sequelize');
const { fn, col } = require('sequelize');

const Customer = require('../models/customer-model');
const DeliveryPersonnel = require('../models/delivery-personnel-model');
const Restaurant = require('../models/restaurant-model');
const RestaurantOwner = require('../models/restaurant-owner-model');
const Order = require('../models/order-model');

// Get the most popular restaurants
exports.getMostPopularRestaurants = async (req, res) => {
  try {
    const popularRestaurants = await Order.findAll({
      attributes: [
        'restaurant_id',
        [sequelize.fn('COUNT', sequelize.col('restaurant_id')), 'orderCount']
      ],
      group: ['restaurant_id'],
      order: [[sequelize.fn('COUNT', sequelize.col('restaurant_id')), 'DESC']],
      limit: 10, // top 10
      include: [
        {
          model: Restaurant,
          attributes: ['name']
        }
      ],
    });
    res.status(200).json(popularRestaurants);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal server error' });
  }
};

// Get average delivery time for all orders
exports.getAverageDeliveryTime = async (req, res) => {
  try {
    const averageDeliveryTime = await Order.findAll({
      attributes: [
        [fn('AVG', fn('TIMESTAMPDIFF', sequelize.literal('HOUR'), col('orderTime'), col('delivery_time'))), 'avgDeliveryTimeInHours']
      ],
      logging: console.log // This will log the generated SQL query
    });

    res.status(200).json({
      avgDeliveryTimeInHours: averageDeliveryTime[0].dataValues.avgDeliveryTimeInHours
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal server error' });
  }
};

// Get order trends (number of orders by day)
exports.getOrderTrends = async (req, res) => {
  try {
    const orderTrends = await Order.findAll({
      attributes: [
        [sequelize.fn('DATE', sequelize.col('orderTime')), 'orderDate'],
        [sequelize.fn('COUNT', sequelize.col('id')), 'orderCount']
      ],
      group: ['orderDate'],
      order: [['orderDate', 'ASC']]
    });

    res.status(200).json(orderTrends);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal server error' });
  }
};

exports.getActiveUsersByRole = async (req, res) => {
  try {
    if (!req.query?.role) return res.status(400).json({ message: `Role is required` });
    let activeUsers = 0;
    switch (req.query.role) {
      case 'customer': 
        activeUsers = await Customer.count({
          where: { status: 'active' }  // Assuming 'status' field is used to mark users as active
        });
        break;
      case 'delivery_personnel': 
        activeUsers = await DeliveryPersonnel.count({
          where: { status: 'active' }  // Assuming 'status' field is used to mark users as active
        });
        break;
      case 'restaurant': 
        activeUsers = await RestaurantOwner.count({
          where: { status: 'active' }  // Assuming 'status' field is used to mark users as active
        });
        break;
    }
    res.status(200).json({ activeUsers, role: req.query.role });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal Server Error' });
  }
}

exports.getDeliveryActivity = async (req, res) => {
  try {
    const activeDeliveries = await Order.count({
      where: { status: 'Pending' },
    });

    const completedDeliveries = await Order.count({
      where: { status: 'Completed' }
    });

    res.status(200).json({
      activeDeliveries,
      completedDeliveries
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal Server Error' });
  }
}

exports.getOrderStatuses = async (req, res) => {
  try {
    const orderStatuses = await Order.findAll({
      attributes: [
        'status', 
        [sequelize.fn('COUNT', sequelize.col('status')), 'statusCount']
      ],
      group: 'status'
    });

    res.status(200).json(orderStatuses);
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Internal Server Error' });
  }
}
