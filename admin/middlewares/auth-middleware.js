const jwt = require('jsonwebtoken');

const secretKey = 'pQDZf/mFPknvG7+BXOlOJ8wREufrO7PZV/jmVWo5c8peB+w/U30CJZaEJvAfQKxv3aQFylUyjt4i4FXrw21KHQ==';

const verifyToken = (req, res, next) => {
    const token = req.header('Authorization')?.replace('Bearer ', '');
    if (!token) return res.status(401).send('Access Denied');

    try {
        const decoded = jwt.verify(token, secretKey, { algorithms: ['HS512'] });
        req.user = decoded;
        if (req.user?.role !== 'ADMINISTRATOR') {
            return res.status(401).send('Access Denied');
        }
        next();
    } catch (err) {
        console.log(err);
        return res.status(400).send('Invalid Token');
    }
};

module.exports = { verifyToken };
