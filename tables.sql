-- Create Users table
CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,username VARCHAR(255),email VARCHAR(255) UNIQUE,password VARCHAR(255),user_type ENUM('staff', 'customer'), security_question TEXT NULL,security_answer TEXT NULL);

-- Create Restaurants table
CREATE TABLE restaurants (restaurant_id INT PRIMARY KEY AUTO_INCREMENT,restaurant_name VARCHAR(100) NOT NULL);

-- Create Staff table
CREATE TABLE staff (staff_id INT PRIMARY KEY AUTO_INCREMENT,staff_name VARCHAR(100) NOT NULL,restaurant_id INT,FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id));

-- Create Ratings table
CREATE TABLE ratings (rating_id INT PRIMARY KEY AUTO_INCREMENT,order_id INT,thumbs_up INT DEFAULT 0,thumbs_down INT DEFAULT 0,FOREIGN KEY (order_id) REFERENCES orders(order_id));

-- Create Orders table
CREATE TABLE orders (order_id INT PRIMARY KEY AUTO_INCREMENT,customer_name VARCHAR(100) NOT NULL,staff_id INT,restaurant_id INT,order_time DATETIME DEFAULT CURRENT_TIMESTAMP,status ENUM('pending', 'ready', 'collected') DEFAULT 'pending',FOREIGN KEY (staff_id) REFERENCES staff(staff_id),FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id));


