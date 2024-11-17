const Order = require('../models/order-model');

// View all orders
const viewOrders = async (req, res) => {
    try {
        const orders = await Order.findAll();
        res.status(200).json({ orders });
    } catch (err) {
        res.status(500).json({ message: 'Error fetching orders', error: err.message });
    }
};

// Update order status
const updateOrderStatus = async (req, res) => {
    const { id } = req.params;
    const { status } = req.body;

    try {
        const order = await Order.findByPk(id);

        if (!order) return res.status(404).json({ message: 'Order not found' });

        order.status = status;
        await order.save();

        res.status(200).json({ message: 'Order status updated', order });
    } catch (err) {
        res.status(500).json({ message: 'Error updating order', error: err.message });
    }
};

module.exports = { 
    viewOrders, 
    updateOrderStatus, 
}