import java.sql.*;

public class ProductManagementApp {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            createProductTable(connection);
            insertSampleData(connection);
            retrieveProductData(connection);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createProductTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Product (" +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "price_per_unit DOUBLE," +
                    "active_for_sell BOOLEAN" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("Product table created successfully.");
        }
    }

    private static void insertSampleData(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Product (id, name, price_per_unit, active_for_sell) VALUES (?, ?, ?, ?)")) {

            statement.setInt(1, 1);
            statement.setString(2, "Product 1");
            statement.setDouble(3, 9.99);
            statement.setBoolean(4, true);
            statement.executeUpdate();

            statement.setInt(1, 2);
            statement.setString(2, "Product 2");
            statement.setDouble(3, 19.99);
            statement.setBoolean(4, false);
            statement.executeUpdate();

            System.out.println("Sample data inserted successfully.");
        }
    }

    private static void retrieveProductData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Product")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price_per_unit");
                boolean active = resultSet.getBoolean("active_for_sell");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Price per Unit: " + price);
                System.out.println("Active for Sell: " + active);
                System.out.println("--------------------------");
            }
        }
    }
}