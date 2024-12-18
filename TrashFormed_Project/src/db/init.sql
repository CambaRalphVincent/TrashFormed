CREATE DATABASE trashformed_db;

CREATE TABLE Users(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    user_password VARCHAR(50) NOT NULL,
    user_role VARCHAR(10) NOT NULL
);

CREATE TABLE WasteItems(
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    waste_type VARCHAR(50) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES Users(user_id),
    INDEX(waste_type) -- Create index on waste_type
);

CREATE TABLE RecyclingTips(
    tip_id INT AUTO_INCREMENT PRIMARY KEY,
    waste_type VARCHAR(50) NOT NULL,
    tip TEXT NOT NULL,
    FOREIGN KEY(waste_type) REFERENCES WasteItems(waste_type) ON DELETE CASCADE
);


#USER OPERATIONS
#Insert New User
INSERT INTO users (username, user_password, user_role) VALUES (?, ?, ?);

#Validate User Login
SELECT * FROM users WHERE username = ? AND user_password = ?;

#Check if User is Admin
SELECT user_role FROM users WHERE username = ?;

#Fetch User ID by Username
SELECT user_id FROM Users WHERE username = ?;

#Fetch User Details by Username
SELECT * FROM users WHERE username = ?;


#WASTE ITEMS OPERATIONS
#Insert Waste Item
INSERT INTO WasteItems (user_id, item_name, quantity, waste_type) VALUES (?, ?, ?, ?);

#Display Recycling Summary
SELECT waste_type, SUM(quantity) AS total_quantity 
FROM WasteItems 
WHERE user_id = ? 
GROUP BY waste_type;


#RECYCLING TIPS OPERATIONS
#Insert Recycling Tip
INSERT INTO RecyclingTips (waste_type, tip) VALUES (?, ?);

#Fetch Tips by Waste Type
SELECT GROUP_CONCAT(tip SEPARATOR '; ') AS tips 
FROM RecyclingTips 
WHERE waste_type = ?;

#Fetch All Recycling Tips
SELECT waste_type, GROUP_CONCAT(tip SEPARATOR '; ') AS tips 
FROM RecyclingTips 
GROUP BY waste_type;

#Fetch Individual Tips by Waste Type
SELECT tip FROM RecyclingTips WHERE waste_type = ?;

#Delete Specific Tip by Waste Type and Tip Number
DELETE FROM RecyclingTips WHERE waste_type = ? AND tip_id = ?;

#Fetch Tip IDs by Waste Type
SELECT tip_id FROM RecyclingTips WHERE waste_type = ?;
