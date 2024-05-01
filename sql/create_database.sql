USE master;
GO

DROP DATABASE IF EXISTS dbFarm;

CREATE DATABASE dbFarm;
GO

USE dbFarm;
GO

-- Culture Table
CREATE TABLE Culture (
    CultureId INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL UNIQUE,
    Active BIT DEFAULT 1
);


-- Farmer Table
CREATE TABLE Farmer (
    FarmerId INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL,
    EmploymentDate DATE NOT NULL,
    Culture NVARCHAR(255) NOT NULL,
    Active BIT DEFAULT 1,
    FOREIGN KEY (Culture) REFERENCES Culture(Name)
);


-- LandLot Table
CREATE TABLE LandLot (
    LandLotId INT IDENTITY(1,1) PRIMARY KEY,
    Location NVARCHAR(255) NOT NULL UNIQUE,
    Area FLOAT NOT NULL,
    PlantingDate DATE NOT NULL,
    HarvestDate DATE NOT NULL,
    Culture NVARCHAR(255) NOT NULL,
    Active BIT DEFAULT 1,
    FOREIGN KEY (Culture) REFERENCES Culture(Name)
);


-- ProductStock Table
CREATE TABLE ProductStock (
    ProductStockId INT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(255) NOT NULL UNIQUE,
    Quantity INT NOT NULL,
    Culture NVARCHAR(255) NOT NULL,
    Active BIT DEFAULT 1,
    FOREIGN KEY (Culture) REFERENCES Culture(Name)
);

INSERT INTO Culture (Name) VALUES
('Wheat'),
('Corn'),
('Rice'),
('Soybean'),
('Barley');

INSERT INTO Farmer (Name, EmploymentDate, Culture) VALUES
('John Smith', '2023-01-15', 'Wheat'),
('Alice Johnson', '2023-02-20', 'Corn'),
('Michael Brown', '2023-03-10', 'Rice'),
('Emily Wilson', '2023-04-05', 'Soybean'),
('Daniel Lee', '2023-05-18', 'Barley');

INSERT INTO LandLot (Location, Area, PlantingDate, HarvestDate, Culture) VALUES
('Field A', 100.5, '2023-03-01', '2023-06-01', 'Wheat'),
('Field B', 150.2, '2023-04-10', '2023-08-15', 'Corn'),
('Field C', 80.3, '2023-05-15', '2023-09-20', 'Rice'),
('Field D', 120.8, '2023-06-20', '2023-10-25', 'Soybean'),
('Field E', 90.0, '2023-07-10', '2023-11-30', 'Barley');

INSERT INTO ProductStock (Name, Quantity, Culture) VALUES
('Wheat Grain', 5000, 'Wheat'),
('Corn Grain', 7000, 'Corn'),
('Rice Grain', 6000, 'Rice'),
('Soybean Grain', 8000, 'Soybean'),
('Barley Grain', 5500, 'Barley');
