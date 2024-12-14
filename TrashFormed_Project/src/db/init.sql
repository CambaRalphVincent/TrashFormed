CREATE DATABASE trashformed_db;

CREATE TABLE Users(
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(10) NOT NULL
);

CREATE TABLE wasteitems(
	item_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    waste_type VARCHAR(50) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

CREATE TABLE recyclingTips(
	tip_id INT AUTO_INCREMENT PRIMARY KEY,
    waste_type VARCHAR(50) PRIMARY KEY,
    tip TEXT NOT NULL
);

#Insert a New User
INSERT INTO Users (username, password, role) VALUES (?, ?, ?);

#Validate User Login
SELECT * FROM Users WHERE username = ? AND password = ?;

#Check Admin Role
SELECT role FROM Users WHERE username = ?;

#Insert or Update Recycling Tip
INSERT INTO RecyclingTips (waste_type, tip) VALUES (?, ?)
ON DUPLICATE KEY UPDATE tip = VALUES(tip);

#Get Tips by Waste Type
SELECT GROUP_CONCAT(tip SEPARATOR '; ') AS tips FROM RecyclingTips WHERE waste_type = ?;

#Delete a Recycling Tip
DELETE FROM RecyclingTips WHERE tip_id = ?;

#Insert a Waste Item
INSERT INTO WasteItems (user_id, item_name, quantity, waste_type) VALUES (?, ?, ?, ?);

#Get Recycling Summary for User
SELECT waste_type, SUM(quantity) AS total_quantity 
FROM WasteItems 
WHERE user_id = ? 
GROUP BY waste_type;

#Get All Recycling Tips
SELECT waste_type, tip FROM RecyclingTips;

#Get User ID by Username
SELECT user_id FROM Users WHERE username = ?;


