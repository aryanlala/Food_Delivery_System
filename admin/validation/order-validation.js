const Joi = require('joi');

// Schema for updating the order status
const updateOrderStatusSchema = Joi.object({
    status: Joi.string().valid('Pending', 'Preparing', 'Ready', 'Completed', 'Cancelled').required()
});

module.exports = { updateOrderStatusSchema };
