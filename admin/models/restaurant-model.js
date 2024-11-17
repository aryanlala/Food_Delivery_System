const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const Restaurant = sequelize.define('Restaurant', {
    name: { type: DataTypes.STRING, allowNull: false, unique: true },
    cuisine_type: { type: DataTypes.STRING, allowNull: false },
    address: { type: DataTypes.STRING, allowNull: false },
    phone_number: { type: DataTypes.STRING, allowNull: false },
}, {
    timestamps: true,
    freezeTableName: true  // Prevent Sequelize from pluralizing the table name
});

module.exports = Restaurant;