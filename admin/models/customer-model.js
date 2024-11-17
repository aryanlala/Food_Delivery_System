const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');

const Customer = sequelize.define('Customer', {
    name: { type: DataTypes.STRING, allowNull: false },
    email: { type: DataTypes.STRING, allowNull: false, unique: true },
    password: { type: DataTypes.STRING, allowNull: false },
    role: { 
        type: DataTypes.ENUM,
        values: ['customer'],
        allowNull: false
    },
    status: { 
        type: DataTypes.ENUM,
        values: ['active', 'inactive'],
        defaultValue: 'active',
        allowNull: false
    }
}, {
    timestamps: false,
    freezeTableName: true  // Prevent Sequelize from pluralizing the table name
});

module.exports = Customer;