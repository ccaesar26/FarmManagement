import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DbUtils {
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
                "databaseName=dbFarm;user=sa;password=1q2w3e;encrypt=true;trustServerCertificate=true";

            connection = DriverManager.getConnection(connectionUrl);

            if (connection != null) {
                System.out.println("Connected");
            } else {
                System.out.println("Not connected");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Culture> selectAllCultures() {
        Connection connection = DbUtils.getConnection();

        List<Culture> cultures = new ArrayList<>();

        try {
            String storedProcedure = "{call spCultureSelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int cultureId = resultSet.getInt("CultureId");
                    String name = resultSet.getString("Name");

                    Culture culture = new Culture(cultureId, name);
                    cultures.add(culture);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return cultures;
    }

    public static Culture selectCultureAllActiveWhere(String cultureName) {
        Connection connection = DbUtils.getConnection();

        Culture culture = null;

        try {
            String storedProcedure = "{call spCultureSelectAllActiveWhere(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, cultureName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int cultureId = resultSet.getInt("CultureId");
                    String name = resultSet.getString("Name");

                    culture = new Culture(cultureId, name);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return culture;
    }

    public static void insertCulture(String name) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spCultureInsert(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateCulture(String oldName, String newName) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spCultureUpdate(?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldName);
                callableStatement.setString(2, newName);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteCulture(String name) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spCultureSoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    public static List<Farmer> selectAllFarmers() {
        Connection connection = DbUtils.getConnection();

        List<Farmer> Farmers = new ArrayList<>();

        try {
            String storedProcedure = "{call spFarmerSelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int FarmerId = resultSet.getInt("FarmerId");
                    String name = resultSet.getString("Name");
                    java.util.Date date = resultSet.getDate("EmploymentDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String culture = resultSet.getString("Culture");

                    Farmer Farmer = new Farmer(FarmerId, name, dateFormat.format(date), culture);
                    Farmers.add(Farmer);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return Farmers;
    }

    public static Farmer selectFarmerAllActiveWhere(String FarmerName) {
        Connection connection = DbUtils.getConnection();

        Farmer Farmer = null;

        try {
            String storedProcedure = "{call spFarmerSelectAllActiveWhere(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, FarmerName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int FarmerId = resultSet.getInt("FarmerId");
                    String name = resultSet.getString("Name");
                    java.util.Date date = resultSet.getDate("EmploymentDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String culture = resultSet.getString("Culture");

                    Farmer = new Farmer(FarmerId, name, dateFormat.format(date), culture);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return Farmer;
    }

    public static void insertFarmer(String name, String emplDate, String culture) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spFarmerInsert(?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                java.sql.Date sqlDate = convertToSqlDate(emplDate);
                callableStatement.setDate(2, sqlDate);
                callableStatement.setString(3, culture);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            UiUtils.showErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateFarmer(String oldName, String newName, String emplDate, String culture) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spFarmerUpdate(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldName);
                callableStatement.setString(2, newName);
                java.sql.Date sqlDate = convertToSqlDate(emplDate);
                callableStatement.setDate(3, sqlDate);
                callableStatement.setString(4, culture);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteFarmer(String name) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spFarmerSoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    public static List<LandLot> selectAllLandLots() {
        Connection connection = DbUtils.getConnection();

        List<LandLot> LandLots = new ArrayList<>();

        try {
            String storedProcedure = "{call spLandLotSelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int LandLotId = resultSet.getInt("LandLotId");
                    String location = resultSet.getString("Location");
                    int area = resultSet.getInt("Area");
                    java.util.Date plantingDate = resultSet.getDate("PlantingDate");
                    java.util.Date harvestDate = resultSet.getDate("HarvestDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String culture = resultSet.getString("Culture");

                    LandLot LandLot = new LandLot(LandLotId, location, area, dateFormat.format(plantingDate), dateFormat.format(harvestDate), culture);
                    LandLots.add(LandLot);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return LandLots;
    }

    public static LandLot selectLandLotAllActiveWhere(String LandLotName) {
        Connection connection = DbUtils.getConnection();

        LandLot LandLot = null;

        try {
            String storedProcedure = "{call spLandLotSelectAllActiveWhere(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, LandLotName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int LandLotId = resultSet.getInt("LandLotId");
                    String location = resultSet.getString("Location");
                    int area = resultSet.getInt("Area");
                    java.util.Date plantingDate = resultSet.getDate("PlantingDate");
                    java.util.Date harvestDate = resultSet.getDate("HarvestDate");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String culture = resultSet.getString("Culture");

                    LandLot = new LandLot(LandLotId, location, area, dateFormat.format(plantingDate), dateFormat.format(harvestDate), culture);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return LandLot;
    }

    public static void insertLandLot(String location, float area, String plantingDate, String harvestDate, String culture) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spLandLotInsert(?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, location);
                callableStatement.setFloat(2, area);
                java.sql.Date sqlDate = convertToSqlDate(plantingDate);
                callableStatement.setDate(3, sqlDate);
                sqlDate = convertToSqlDate(harvestDate);
                callableStatement.setDate(4, sqlDate);
                callableStatement.setString(5, culture);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            UiUtils.showErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateLandLot(String oldLocation, String newLocation, float area, String plantingDate, String harvestDate, String culture) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spLandLotUpdate(?, ?, ?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldLocation);
                callableStatement.setString(2, newLocation);
                callableStatement.setFloat(3, area);
                java.sql.Date sqlDate = convertToSqlDate(plantingDate);
                callableStatement.setDate(4, sqlDate);
                sqlDate = convertToSqlDate(harvestDate);
                callableStatement.setDate(5, sqlDate);
                callableStatement.setString(6, culture);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteLandLot(String location) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spLandLotSoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, location);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    public static List<ProductStock> selectAllProductStocks() {
        Connection connection = DbUtils.getConnection();

        List<ProductStock> ProductStocks = new ArrayList<>();

        try {
            String storedProcedure = "{call spProductStockSelectAllActive}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int ProductStockId = resultSet.getInt("ProductStockId");
                    String name = resultSet.getString("Name");
                    int quantity = resultSet.getInt("Quantity");
                    String culture = resultSet.getString("Culture");

                    ProductStock ProductStock = new ProductStock(ProductStockId, name, quantity, culture);
                    ProductStocks.add(ProductStock);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return ProductStocks;
    }

    public static ProductStock selectProductStockAllActiveWhere(String ProductStockName) {
        Connection connection = DbUtils.getConnection();

        ProductStock ProductStock = null;

        try {
            String storedProcedure = "{call spProductStockSelectAllActiveWhere(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, ProductStockName);
                ResultSet resultSet = callableStatement.executeQuery();

                while (resultSet.next()) {
                    int ProductStockId = resultSet.getInt("ProductStockId");
                    String name = resultSet.getString("Name");
                    int quantity = resultSet.getInt("Quantity");
                    String culture = resultSet.getString("Culture");

                    ProductStock = new ProductStock(ProductStockId, name, quantity, culture);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return ProductStock;
    }

    public static void insertProductStock(String name, int quantity, String culture) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spProductStockInsert(?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.setInt(2, quantity);
                callableStatement.setString(3, culture);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            UiUtils.showErrorMessage(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static void updateProductStock(String oldName, String newName, int quantity, String culture) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spProductStockUpdate(?, ?, ?, ?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, oldName);
                callableStatement.setString(2, newName);
                callableStatement.setInt(3, quantity);
                callableStatement.setString(4, culture);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }
    }

    public static Void softDeleteProductStock(String name) {
        Connection connection = DbUtils.getConnection();

        try {
            String storedProcedure = "{call spProductStockSoftDelete(?)}";

            try (CallableStatement callableStatement = connection.prepareCall(storedProcedure)) {
                callableStatement.setString(1, name);
                callableStatement.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.closeConnection(connection);
        }

        return null;
    }

    // ----------------------------------------------------------------------

    private static java.sql.Date convertToSqlDate(String dateString) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the string to obtain a java.util.Date object
            java.util.Date utilDate = dateFormat.parse(dateString);

            // Convert java.util.Date to java.sql.Date
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the parsing exception or throw it
            return null; // Return null in case of an error (you might want to handle it differently)
        }
    }
}
