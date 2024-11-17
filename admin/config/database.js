const { Sequelize } = require('sequelize');

// Create a Sequelize instance
const sequelize = new Sequelize('defaultdb', 'avnadmin', 'AVNS_0seU5cT3nVxOQqEjqFr', {
    host: 'mysql-73fa769-wilp-e4c5.h.aivencloud.com',
    port: 17919,
    dialect: 'mysql',
    logging: false,
});

module.exports = sequelize;
