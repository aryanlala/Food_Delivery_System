const express = require('express');
const { verifyToken } = require('../middlewares/auth-middleware');
const userController = require('../controllers/user-controller');
const validate = require('../middlewares/validator');
const { createUserSchema, updateUserSchema, createDeliveryPersonnelSchema, updateDeliveryPersonnelSchema, deactivateUserSchema } = require('../validation/user-validation');

const router = express.Router();

router.use(verifyToken);

// Create a new customer (Admin functionality)
router.post('/customers', validate(createUserSchema), userController.createCustomer);

// Update a customer (Admin functionality)
router.put('/customers/:id', validate(updateUserSchema), userController.updateCustomer);

// Deactivate a customer (Admin functionality)
router.put('/customers/:id/deactivate', validate(deactivateUserSchema), userController.deactivateCustomer);

// Create a new deliverypersonnel (Admin functionality)
router.post('/deliverypersonnel', validate(createDeliveryPersonnelSchema), userController.createDeliveryPersonnel);

// Update a deliverypersonnel (Admin functionality)
router.put('/deliverypersonnel/:id', validate(updateDeliveryPersonnelSchema), userController.updateDeliveryPersonnel);

// Deactivate a deliverypersonnel (Admin functionality)
router.put('/deliverypersonnel/:id/deactivate', validate(deactivateUserSchema), userController.deactivateDeliveryPersonnel);

// Create a new restaurant (Admin functionality)
router.post('/restaurant', validate(createUserSchema), userController.createRestaurant);

// Update a restaurant (Admin functionality)
router.put('/restaurant/:id', validate(updateUserSchema), userController.updateRestaurant);

// Deactivate a restaurant (Admin functionality)
router.put('/restaurant/:id/deactivate', validate(deactivateUserSchema), userController.deactivateRestaurant);

module.exports = router;
