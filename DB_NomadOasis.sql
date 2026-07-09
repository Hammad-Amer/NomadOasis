CREATE DATABASE NOMADOASIS;
USE NOMADOASIS;

CREATE TABLE User1 (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    username1 VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password1 VARCHAR(255) NOT NULL,
    CNIC varchar(14) NOT NULL,
    DOB DATE NOT NULL,
    Gender ENUM('Male','Female') NOT NULL,
    userType ENUM('Traveler', 'Admin', 'Consultant') NOT NULL
);

INSERT INTO User1 (username1, email, password1, CNIC, DOB, Gender, userType)
VALUES ('c2', 'c2d@example.com', 'c2', '123', '3002-01-23', 'Male', 'Admin');

CREATE TABLE Item (
    itemID INT AUTO_INCREMENT PRIMARY KEY,
    name1 VARCHAR(255) NOT NULL,
    description1 VARCHAR(255),
    price INT,
    stock INT
);

CREATE TABLE Cart (
    cartID INT AUTO_INCREMENT PRIMARY KEY,
    travelerID INT NOT NULL,
    FOREIGN KEY (travelerID) REFERENCES User1(userID)
);

CREATE TABLE CartItems (
    cartItemID INT AUTO_INCREMENT PRIMARY KEY,
    cartID INT NOT NULL,
    itemID INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (cartID) REFERENCES Cart(cartID),
    FOREIGN KEY (itemID) REFERENCES Item(itemID)
);

CREATE TABLE Package (
    packageID INT AUTO_INCREMENT PRIMARY KEY,
    name1 VARCHAR(255) NOT NULL,
    destination VARCHAR(255),
    duration INT,
    description1 VARCHAR(255),
    price INT
);
CREATE TABLE Hotel (
    hotelID INT AUTO_INCREMENT PRIMARY KEY,
    name1 VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description1 VARCHAR(255) NOT NULL,
    rating DECIMAL(2, 1) 
);
CREATE TABLE Room (
    roomID INT AUTO_INCREMENT PRIMARY KEY,
    hotelID INT NOT NULL,
    roomnum INT NOT NULL,
    type1 VARCHAR(255),
    price INT,
    available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
);
CREATE TABLE BookingRoom (
    bookingID INT AUTO_INCREMENT PRIMARY KEY,
    travelerID INT NOT NULL,
    roomID INT,
    hotelID INT,
    bookingDate DATE NOT NULL,
    FOREIGN KEY (travelerID) REFERENCES User1(userID),
    FOREIGN KEY (roomID) REFERENCES Room(roomID),
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
);

CREATE TABLE BookingPackage (
    bookingID INT AUTO_INCREMENT PRIMARY KEY,
    travelerID INT NOT NULL,
    packageID INT,
    bookingDate DATE NOT NULL,
	FOREIGN KEY (travelerID) REFERENCES User1(userID),
    FOREIGN KEY (packageID) REFERENCES Package(packageID)
);

create Table query1(
	queryID INT AUTO_INCREMENT PRIMARY KEY,
    consultantID INT,
    travelerID INT,
    queryContent TEXT NOT NULL,
    response TEXT,
	
    FOREIGN KEY (consultantID) REFERENCES User1(userID),
    FOREIGN KEY (travelerID) REFERENCES User1(userID)
);

create Table logs1(
	logID INT AUTO_INCREMENT PRIMARY KEY,
    travelerID INT,
    logtext TEXT NOT NULL,
    Date1 DATE,
	
    FOREIGN KEY (travelerID) REFERENCES User1(userID)
);

-- Insert Consultant (userType = 'Consultant')
INSERT INTO User1 (username1, email, password1, CNIC, DOB, Gender, userType)
VALUES ('consultant_user', 'consultant@example.com', 'password123', '1234512345671', '1985-06-15', 'Male', 'Consultant');

-- Insert Admin (userType = 'Admin')
INSERT INTO User1 (username1, email, password1, CNIC, DOB, Gender, userType)
VALUES ('c6', 'awd@example.com', 'c6', '1234512345672', '1980-01-25', 'Female', 'Admin');

-- Insert Travelers (userType = 'Traveler')
INSERT INTO User1 (username1, email, password1, CNIC, DOB, Gender, userType)
VALUES 
('traveler1', 'traveler1@example.com', 'password1', '1234512345673', '1995-11-10', 'Male', 'Traveler'),
('traveler2', 'traveler2@example.com', 'password2', '1234512345674', '1992-03-20', 'Female', 'Traveler'),
('traveler3', 'traveler3@example.com', 'password3', '1234512345675', '1990-07-30', 'Male', 'Traveler'),
('traveler4', 'traveler4@example.com', 'password4', '1234512345676', '1993-09-14', 'Female', 'Traveler'),
('traveler5', 'traveler5@example.com', 'password5', '1234512345677', '1991-12-05', 'Male', 'Traveler');


-- Inserting hotels into the Hotel table
INSERT INTO Hotel (name1, location, description1, rating)
VALUES 
('Luxury Inn', 'Karachi, Pakistan', 'A luxurious 5-star hotel with all modern amenities.', 4.8),
('Mountain View Resort', 'Murree, Pakistan', 'A serene resort with stunning mountain views.', 4.5),
('Beachside Paradise', 'Karachi, Pakistan', 'A beachfront hotel offering scenic views of the Arabian Sea.', 4.2),
('Royal Garden Hotel', 'Lahore, Pakistan', 'A royal hotel with spacious rooms and an elegant garden.', 4.6),
('City Lights Hotel', 'Islamabad, Pakistan', 'A stylish hotel in the heart of Islamabad.', 3.9);


INSERT INTO Room (hotelID, roomnum, type1, price, available)
VALUES 
(1, 101, 'Single', 15000, TRUE),
(1, 102, 'Double', 25000, TRUE),
(1, 103, 'Suite', 40000, TRUE),
(2, 201, 'Single', 12000, TRUE),
(2, 202, 'Double', 20000, TRUE),
(2, 203, 'Suite', 35000, TRUE),
(3, 301, 'Single', 10000, TRUE),
(3, 302, 'Double', 18000, TRUE),
(3, 303, 'Suite', 30000, TRUE),
(4, 401, 'Single', 14000, TRUE),
(4, 402, 'Double', 22000, TRUE),
(4, 403, 'Suite', 37000, TRUE),
(5, 501, 'Single', 8000, TRUE),
(5, 502, 'Double', 15000, TRUE),
(5, 503, 'Suite', 28000, FALSE);


INSERT INTO Item (name1, description1, price, stock)
VALUES ('Hiking Boots', 'Boots', 10000, 50);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('Hiking Bag', 'Bag', 7000, 80);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('Campin Tent', 'Tent', 25000, 5);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('Waterproof Jacket', 'Jacket', 9000, 12);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('Thermal Gloves', 'Gloves', 3000, 80);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('Beanies', 'Caps', 2000, 30);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('First Aid Kit', 'Kit', 6000,20);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('LED Torch', 'Torch', 4000, 10);
INSERT INTO Item (name1, description1, price, stock)
VALUES ('Powerbank', 'power', 13000, 5);

ALTER TABLE Package MODIFY COLUMN description1 TEXT;

ALTER TABLE BookingPackage
ADD COLUMN quantity INT NOT NULL DEFAULT 1;


INSERT INTO Package (name1, destination, duration, description1, price)
VALUES ('Hunza Valley Tour', 'Hunza Valley, Karimabad, Altit Fort, Baltit Fort, Rakaposhi Viewpoint', 7, 
        'Experience the breathtaking beauty of Hunza Valley. Visit the picturesque Karimabad, explore the historic Altit and Baltit forts, and enjoy stunning views of Rakaposhi Peak. A 7-day adventure in one of the most scenic valleys in the world.', 35000);
        
INSERT INTO Package (name1, destination, duration, description1, price)
VALUES ('Swat Valley Adventure', 'Swat Valley, Mingora, Malam Jabba, Ushu Forest, Fizagat Park', 6, 
        'Embark on a 6-day journey through the scenic Swat Valley, often referred to as the "Switzerland of Pakistan." Visit the vibrant city of Mingora, explore the ski resort at Malam Jabba, and immerse yourself in the lush greenery of the Ushu Forest. Trek through the captivating Fizagat Park and enjoy breathtaking views of the surrounding mountains. This tour combines natural beauty, adventure, and culture, making it perfect for those seeking a peaceful escape amidst nature.', 30000);

INSERT INTO Package (name1, destination, duration, description1, price)
VALUES ('Skardu Valley & Deosai National Park Tour', 'Skardu, Deosai National Park, Shangrila Resort, Shigar Fort, Upper Kachura Lake', 8, 
        'Discover the majestic Skardu Valley and the expansive Deosai National Park, the second-highest plateau in the world. This 8-day adventure will take you through the pristine beauty of Skardu, the colorful Shangrila Resort, and the ancient Shigar Fort. You’ll also explore the enchanting Upper Kachura Lake, surrounded by snow-capped mountains and lush green meadows. This package is ideal for those who seek a unique combination of rugged landscapes, historical sites, and tranquility.', 45500);

INSERT INTO Package (name1, destination, duration, description1, price)
VALUES ('Naran Kaghan Valley Adventure', 'Naran, Kaghan, Lake Saif ul Malook, Babusar Top, Balakot', 5, 
        'Explore the beautiful Naran Kaghan Valley, known for its stunning landscapes and alpine lakes. This 5-day tour will take you to the famous Saif ul Malook Lake, surrounded by towering peaks and crystal-clear waters. Visit the scenic Babusar Top, offering panoramic views of the valley, and explore the charming town of Naran. The tour also includes a visit to Balakot, a peaceful town along the Kunhar River, known for its natural beauty and serene atmosphere.', 50000);

INSERT INTO Package (name1, destination, duration, description1, price)
VALUES ('Gilgit Baltistan Trekking Expedition', 'Gilgit Baltistan, Rakaposhi Base Camp, Nanga Parbat, Ratti Gali Lake', 10, 
        'Embark on a 10-day trekking expedition to the remote and unspoiled regions of Gilgit Baltistan. This adventure is perfect for experienced trekkers, taking you through the Rakaposhi Base Camp, the northernmost base camp of one of the highest peaks in the world. Explore the breathtaking Nanga Parbat and the mesmerizing Ratti Gali Lake, a high-altitude alpine lake. This package offers rugged trails, spectacular mountain vistas, and the chance to experience the culture and hospitality of the local communities.', 48000);

INSERT INTO Package (name1, destination, duration, description1, price)
VALUES ('Fairy Meadows & Nanga Parbat Base Camp Tour', 'Fairy Meadows, Nanga Parbat Base Camp, Rupal Valley', 7, 
        'Experience the ultimate trekking adventure in northern Pakistan with a 7-day tour to the breathtaking Fairy Meadows and Nanga Parbat Base Camp. Fairy Meadows offers an idyllic view of Nanga Parbat, the ninth-highest peak in the world. The trek takes you through lush forests, picturesque meadows, and eventually to the base camp of Nanga Parbat, where you’ll be surrounded by some of the most majestic mountains on earth. The package also includes a visit to the scenic Rupal Valley, known for its stunning landscapes and serenity.', 40000);
        
