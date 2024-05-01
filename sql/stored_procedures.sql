CREATE PROCEDURE spCultureSelectAllActive
AS
BEGIN
    SELECT * FROM Culture WHERE Active = 1;
END;
GO

CREATE PROCEDURE spCultureSelectAllActiveWhere
    @CultureName NVARCHAR(255)
AS
BEGIN
    SELECT * FROM Culture WHERE Name = @CultureName AND Active = 1;
END;
GO

CREATE PROCEDURE spCultureInsert
    @CultureName NVARCHAR(255)
AS
BEGIN
    INSERT INTO Culture (Name) VALUES (@CultureName);
END;
GO

CREATE PROCEDURE spCultureUpdate
    @OldCultureName NVARCHAR(255),
    @NewCultureName NVARCHAR(255)
AS
BEGIN
    UPDATE Culture SET Name = @NewCultureName WHERE Name = @OldCultureName;
END;
GO

CREATE PROCEDURE spCultureSoftDelete
    @CultureName NVARCHAR(255)
AS
BEGIN
    UPDATE Culture SET Active = 0 WHERE Name = @CultureName;
END;
GO

CREATE PROCEDURE spFarmerSelectAllActive
AS
BEGIN
    SELECT * FROM Farmer WHERE Active = 1;
END;
GO

CREATE PROCEDURE spFarmerSelectAllActiveWhere
    @FarmerName NVARCHAR(255)
AS
BEGIN
    SELECT * FROM Farmer WHERE Name = @FarmerName AND Active = 1;
END;
GO

CREATE PROCEDURE spFarmerInsert
    @FarmerName NVARCHAR(255),
    @EmploymentDate DATE,
    @Culture NVARCHAR(255)
AS
BEGIN
    INSERT INTO Farmer (Name, EmploymentDate, Culture) VALUES (@FarmerName, @EmploymentDate, @Culture);
END;
GO

CREATE PROCEDURE spFarmerUpdate
    @OldFarmerName NVARCHAR(255),
    @NewFarmerName NVARCHAR(255),
    @EmploymentDate DATE,
    @Culture NVARCHAR(255)
AS
BEGIN
    UPDATE Farmer SET Name = @NewFarmerName, EmploymentDate = @EmploymentDate, Culture = @Culture WHERE Name = @OldFarmerName;
END;
GO

CREATE PROCEDURE spFarmerSoftDelete
    @FarmerName NVARCHAR(255)
AS
BEGIN
    UPDATE Farmer SET Active = 0 WHERE Name = @FarmerName;
END;
GO

CREATE PROCEDURE spLandLotSelectAllActive
AS
BEGIN
    SELECT * FROM LandLot WHERE Active = 1;
END;
GO

CREATE PROCEDURE spLandLotSelectAllActiveWhere
    @LandLotLocation NVARCHAR(255)
AS
BEGIN
    SELECT * FROM LandLot WHERE Location = @LandLotLocation AND Active = 1;
END;
GO

CREATE PROCEDURE spLandLotInsert
    @Location NVARCHAR(255),
    @Area FLOAT,
    @PlantingDate DATE,
    @HarvestDate DATE,
    @Culture NVARCHAR(255)
AS
BEGIN
    INSERT INTO LandLot (Location, Area, PlantingDate, HarvestDate, Culture) VALUES (@Location, @Area, @PlantingDate, @HarvestDate, @Culture);
END;
GO

CREATE PROCEDURE spLandLotUpdate
    @OldLocation NVARCHAR(255),
    @NewLocation NVARCHAR(255),
    @Area FLOAT,
    @PlantingDate DATE,
    @HarvestDate DATE,
    @Culture NVARCHAR(255)
AS
BEGIN
    UPDATE LandLot SET Location = @NewLocation, Area = @Area, PlantingDate = @PlantingDate, HarvestDate = @HarvestDate, Culture = @Culture WHERE Location = @OldLocation;
END;
GO

CREATE PROCEDURE spLandLotSoftDelete
    @Location NVARCHAR(255)
AS
BEGIN
    UPDATE LandLot SET Active = 0 WHERE Location = @Location;
END;
GO

CREATE PROCEDURE spProductStockSelectAllActive
AS
BEGIN
    SELECT * FROM ProductStock WHERE Active = 1;
END;
GO

CREATE PROCEDURE spProductStockSelectAllActiveWhere
    @ProductStockName NVARCHAR(255)
AS
BEGIN
    SELECT * FROM ProductStock WHERE Name = @ProductStockName AND Active = 1;
END;
GO

CREATE PROCEDURE spProductStockInsert
    @Name NVARCHAR(255),
    @Quantity INT,
    @Culture NVARCHAR(255)
AS
BEGIN
    INSERT INTO ProductStock (Name, Quantity, Culture) VALUES (@Name, @Quantity, @Culture);
END;
GO

CREATE PROCEDURE spProductStockUpdate
    @OldName NVARCHAR(255),
    @NewName NVARCHAR(255),
    @Quantity INT,
    @Culture NVARCHAR(255)
AS
BEGIN
    UPDATE ProductStock SET Name = @NewName, Quantity = @Quantity, Culture = @Culture WHERE Name = @OldName;
END;
GO

CREATE PROCEDURE spProductStockSoftDelete
    @Name NVARCHAR(255)
AS
BEGIN
    UPDATE ProductStock SET Active = 0 WHERE Name = @Name;
END;
GO