const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');
const Restaurant = require('./restaurant-model');

const Orders = sequelize.define('Orders', {
    customer_id: { type: DataTypes.INTEGER, allowNull: false },
    restaurant_id: { type: DataTypes.BIGINT, allowNull: false },
    status: { 
        type: DataTypes.ENUM,
        values: ['pending', 'in_progress', 'completed', 'cancelled', 'ready'],
        defaultValue: 'pending',
        allowNull: false
    },
    orderTime: { type: DataTypes.DATE, defaultValue: DataTypes.NOW },
    delivery_time: { type: DataTypes.DATE }
}, {
    timestamps: false,
    freezeTableName: true  // Prevent Sequelize from pluralizing the table name
});

Orders.belongsTo(Restaurant, { foreignKey: 'restaurant_id' });

module.exports = Orders;
