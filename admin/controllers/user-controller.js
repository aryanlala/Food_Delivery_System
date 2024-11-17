const Customer = require('../models/customer-model');
const DeliveryPersonnel = require('../models/delivery-personnel-model');
const RestaurantOwner = require('../models/restaurant-owner-model');
const { hashPassword } = require('../utils/auth');

// Create a new Customer (Admin functionality)
const createCustomer = async (req, res) => {
    try {
        const { name, email, password, role, status = 'active' } = req.body;
        const customer = await Customer.findOne({ where: { email }});
        if (customer) return res.status(400).json({ message: `${role.toLowerCase()} already exists` });
        const hashedPassword = await hashPassword(password);
        const newCustomer = await Customer.create({
            name, email, password: hashedPassword, role, status
        });
        
        res.status(201).json({ message: 'Customer created successfully', customer: newCustomer });
    } catch (err) {
        res.status(500).json({ message: 'Error creating Customer', error: err.message });
    }
};

// Update Customer
const updateCustomer = async (req, res) => {
    const { id } = req.params;
    const { name, email, password, role, status } = req.body;

    try {
        const customer = await Customer.findByPk(id);

        if (!customer) return res.status(404).json({ message: 'Customer not found' });

        if (password) customer.password = await hashPassword(password);
        if (name) customer.name = name;
        if (email) customer.email = email;
        if (role) customer.role = role;
        if (status) customer.status = status;

        await customer.save();
        res.status(200).json({ message: 'Customer updated successfully', Customer });
    } catch (err) {
        res.status(500).json({ message: 'Error updating Customer', error: err.message });
    }
};

// Deactivate Customer
const deactivateCustomer = async (req, res) => {
    const { id } = req.params;

    try {
        const customer = await Customer.findByPk(id);

        if (!customer) return res.status(404).json({ message: 'Customer not found' });

        customer.status = 'inactive';
        await customer.save();

        res.status(200).json({ message: 'Customer deactivated successfully' });
    } catch (err) {
        console.log(err);
        res.status(500).json({ message: 'Error deactivating Customer', error: err.message });
    }
};

// Create a new delivery personnel (Admin functionality)
const createDeliveryPersonnel = async (req, res) => {
    try {
        const { name, email, password, role, status = 'active', available, contact, vehicleType } = req.body;
        const user = await DeliveryPersonnel.findOne({ where: { email }});
        if (user) return res.status(400).json({ message: `${role.toLowerCase()} already exists` });
        const hashedPassword = await hashPassword(password);
        const newDeliveryPersonnel = await DeliveryPersonnel.create({
            name, email, password: hashedPassword, role, status, available, contact, vehicleType
        });
        
        res.status(201).json({ message: 'DeliveryPersonnel created successfully', deliveryPersonnel: newDeliveryPersonnel });
    } catch (err) {
        res.status(500).json({ message: 'Error creating DeliveryPersonnel', error: err.message });
    }
};

// Update delivery personnel
const updateDeliveryPersonnel = async (req, res) => {
    const { id } = req.params;
    const { name, email, password, role, status = 'active', available, contact, vehicleType } = req.body;

    try {
        const user = await DeliveryPersonnel.findByPk(id);

        if (!user) return res.status(404).json({ message: 'DeliveryPersonnel not found' });

        if (password) user.password = await hashPassword(password);
        if (name) user.name = name;
        if (email) user.email = email;
        if (role) user.role = role;
        if (status) user.status = status;
        if (req.body.hasOwnProperty("available")) user.available = available;
        if (contact) user.contact = contact;
        if (vehicleType) user.vehicleType = vehicleType;

        await user.save();
        res.status(200).json({ message: 'DeliveryPersonnel updated successfully', user });
    } catch (err) {
        res.status(500).json({ message: 'Error updating DeliveryPersonnel', error: err.message });
    }
};

// Deactivate delivery personnel
const deactivateDeliveryPersonnel = async (req, res) => {
    const { id } = req.params;

    try {
        const user = await DeliveryPersonnel.findByPk(id);

        if (!user) return res.status(404).json({ message: 'DeliveryPersonnel not found' });

        user.status = 'inactive';
        await user.save();

        res.status(200).json({ message: 'DeliveryPersonnel deactivated successfully' });
    } catch (err) {
        console.log(err);
        res.status(500).json({ message: 'Error deactivating DeliveryPersonnel', error: err.message });
    }
};

// Create a new restaurant (Admin functionality)
const createRestaurant = async (req, res) => {
    try {
        const { name, email, password, role, status = 'active' } = req.body;
        const user = await RestaurantOwner.findOne({ where: { email }});
        if (user) return res.status(400).json({ message: `${role.toLowerCase()} already exists` });
        const hashedPassword = await hashPassword(password);
        console.log( name, email, hashedPassword, role, status)
        const newRestaurantOwner = await RestaurantOwner.create({
            name, email, password: hashedPassword, role, status
        });
        
        res.status(201).json({ message: 'RestaurantOwner created successfully', restaurantOwner: newRestaurantOwner });
    } catch (err) {
        res.status(500).json({ message: 'Error creating RestaurantOwner', error: err.message });
    }
};

// Update restaurant
const updateRestaurant = async (req, res) => {
    const { id } = req.params;
    const { name, email, password, role, status = 'active' } = req.body;

    try {
        const user = await RestaurantOwner.findByPk(id);

        if (!user) return res.status(404).json({ message: 'RestaurantOwner not found' });

        if (password) user.password = await hashPassword(password);
        if (name) user.name = name;
        if (email) user.email = email;
        if (role) user.role = role;
        if (status) user.status = status;

        await user.save();
        res.status(200).json({ message: 'RestaurantOwner updated successfully', user });
    } catch (err) {
        res.status(500).json({ message: 'Error updating RestaurantOwner', error: err.message });
    }
};

// Deactivate restaurant
const deactivateRestaurant = async (req, res) => {
    const { id } = req.params;

    try {
        const user = await RestaurantOwner.findByPk(id);

        if (!user) return res.status(404).json({ message: 'RestaurantOwner not found' });

        user.status = 'inactive';
        await user.save();

        res.status(200).json({ message: 'RestaurantOwner deactivated successfully' });
    } catch (err) {
        console.log(err);
        res.status(500).json({ message: 'Error deactivating RestaurantOwner', error: err.message });
    }
};

module.exports = { 
    createCustomer, 
    updateCustomer, 
    deactivateCustomer, 
    createDeliveryPersonnel, 
    updateDeliveryPersonnel, 
    deactivateDeliveryPersonnel,
    createRestaurant,
    updateRestaurant,
    deactivateRestaurant
};
