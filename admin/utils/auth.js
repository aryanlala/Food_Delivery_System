const bcrypt = require('bcryptjs');

// Function to hash a password
const hashPassword = async (password) => {
    const salt = await bcrypt.genSalt(10); // Generate salt with a round of 10 (security level)
    const hashedPassword = await bcrypt.hash(password, salt); // Hash the password with the salt
    return hashedPassword;
};

module.exports = { hashPassword };
