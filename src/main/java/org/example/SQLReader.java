package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLReader {
    public List<Product> readProductsFromDatabase() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products")) {

            while (resultSet.next()) {
                String productType = resultSet.getString("type");
                String name = resultSet.getString("name");
                double basePrice = resultSet.getDouble("basePrice");
                double maxSellPrice = resultSet.getDouble("maxSellPrice");
                double minSellPrice = resultSet.getDouble("minSellPrice");
                int quality = resultSet.getInt("quality");
                int minQuality = resultSet.getInt("minQuality");
                int maxQuality = resultSet.getInt("maxQuality");
                LocalDate addedDate = resultSet.getDate("addedDate").toLocalDate();
                LocalDate expirationDate = resultSet.getDate("expirationDate").toLocalDate();
                boolean canExpire = resultSet.getBoolean("canExpire");

                // Create the product based on productType
                ProductType type;
                if (productType.equalsIgnoreCase("WineProduct")) {
                    type = new WineProduct();
                } else if (productType.equalsIgnoreCase("CheeseProduct")) {
                    type = new CheeseProduct();
                } else {
                    continue; // Skip if the productType is not recognized
                }

                // Create the product and add it to the list
                Product product = new Product(type, name, basePrice, maxSellPrice, minSellPrice, quality, minQuality, maxQuality, addedDate, expirationDate, canExpire);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error accessing the database: " + e.getMessage());
        }

        return products;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:products.db";
        return DriverManager.getConnection(url);
    }
}
