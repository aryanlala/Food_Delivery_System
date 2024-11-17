const Joi = require('joi');

// Schema for creating a user
const createUserSchema = Joi.object({
    name: Joi.string().min(3).max(100).required(),
    email: Joi.string().email().required(),
    password: Joi.string().min(6).required(),
    role: Joi.string().valid('CUSTOMER', 'RESTAURANT_OWNER', 'DELIVERY_PERSON').required(),
    status: Joi.string().valid('active', 'inactive').default('active')
});

// Schema for updating a user
const updateUserSchema = Joi.object({
    name: Joi.string().min(3).max(100),
    email: Joi.string().email(),
    password: Joi.string().min(6),
    role: Joi.string().valid('CUSTOMER', 'RESTAURANT_OWNER', 'DELIVERY_PERSON'),
    status: Joi.string().valid('active', 'inactive')
});

// Schema for creating/updating a delivery personnel
const createDeliveryPersonnelSchema = Joi.object({
    name: Joi.string().min(3).max(100).required(),
    email: Joi.string().email().required(),
    password: Joi.string().min(6).required(),
    role: Joi.string().valid('CUSTOMER', 'RESTAURANT_OWNER', 'DELIVERY_PERSON').required(),
    status: Joi.string().valid('active', 'inactive').default('active'),
    available: Joi.boolean().default(1),
    contact: Joi.string(),
    vehicleType: Joi.string()
});

// Schema for updating a user
const updateDeliveryPersonnelSchema = Joi.object({
    name: Joi.string().min(3).max(100),
    email: Joi.string().email(),
    password: Joi.string().min(6),
    role: Joi.string().valid('CUSTOMER', 'RESTAURANT_OWNER', 'DELIVERY_PERSON'),
    status: Joi.string().valid('active', 'inactive'),
    available: Joi.boolean().default(1),
    contact: Joi.string(),
    vehicleType: Joi.string()
});

// Schema for deactivating a user
const deactivateUserSchema = Joi.object({
    status: Joi.string().valid('inactive').required()
});

module.exports = { createUserSchema, updateUserSchema, createDeliveryPersonnelSchema, updateDeliveryPersonnelSchema, deactivateUserSchema };
