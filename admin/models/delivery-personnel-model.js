const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const DeliveryPersonnel = sequelize.define('DeliveryPersonnel', {
    name: { type: DataTypes.STRING, allowNull: false },
    email: { type: DataTypes.STRING, allowNull: false, unique: true },
    password: { type: DataTypes.STRING, allowNull: false },
    role: { 
        type: DataTypes.ENUM,
        values: ['delivery_personnel'],
        allowNull: false
    },
    status: { 
        type: DataTypes.ENUM,
        values: ['active', 'inactive'],
        defaultValue: 'active',
        allowNull: false
    },
    available: {
        type: DataTypes.BOOLEAN,
        defaultValue: 1
    }, 
    contact: {
        type: DataTypes.STRING
    },
    vehicleType: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    timestamps: false,
    freezeTableName: true  // Prevent Sequelize from pluralizing the table name
});

module.exports = DeliveryPersonnel;
