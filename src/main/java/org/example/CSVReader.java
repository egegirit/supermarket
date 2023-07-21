package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVReader {
    private String filePath;

    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    public void createShelves(String filePath, Supermarket market){
        // TODO: Create all needed shelves first, then add products to them
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerLine = true;
            while ((line = reader.readLine()) != null) {
                if (headerLine) {
                    headerLine = false;
                    continue; // Skip the header line
                }
                String[] data = line.split(",");
                if (data.length == 12) {
                    String shelfName = data[11].trim();

                    // Create shelves, add products to shelves
                    List<Shelf> allShelves = market.getShelves();
                    // If there are no shells, create the first one
                    if (allShelves.isEmpty()){
                        Shelf newShelf = new Shelf(shelfName);
                        market.addShelf(newShelf);
                        System.out.println("Created shelf " + shelfName);
                    }
                    else{
                        boolean shelfNameExist = false;
                        for ( Shelf shelf : allShelves ) {
                            if (Objects.equals(shelf.getName(), shelfName)){
                                shelfNameExist = true;
                            }
                        }
                        if (!shelfNameExist){
                            Shelf newShelf = new Shelf(shelfName);
                            market.addShelf(newShelf);
                            System.out.println("Created shelf " + shelfName);
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public Supermarket readProducts(String filePath, Supermarket supermarket) throws IOException {
        this.filePath = filePath;
        System.out.println("Reading products from " + filePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        createShelves(filePath, supermarket);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerLine = true;
            while ((line = reader.readLine()) != null) {
                if (headerLine) {
                    headerLine = false;
                    continue; // Skip the header line
                }
                String[] data = line.split(",");
                if (data.length == 12) {
                    String productType = data[0].trim();
                    String name = data[1].trim();
                    double basePrice = Double.parseDouble(data[2].trim());
                    double maxSellPrice = Double.parseDouble(data[3].trim());
                    double minSellPrice = Double.parseDouble(data[4].trim());
                    int quality = Integer.parseInt(data[5].trim());
                    int minQuality = Integer.parseInt(data[6].trim());
                    int maxQuality = Integer.parseInt(data[7].trim());
                    LocalDate addedDate = LocalDate.parse(data[8].trim(), formatter);
                    LocalDate expirationDate = LocalDate.parse(data[9].trim(), formatter);
                    boolean canExpire = Boolean.parseBoolean(data[10].trim());
                    String shelfName = data[11].trim();

                    // Create the product based on productType
                    ProductType type;
                    if (productType.equalsIgnoreCase("WineProduct")) {
                        type = new WineProduct();
                    } else if (productType.equalsIgnoreCase("CheeseProduct")) {
                        type = new CheeseProduct();
                    } else {
                        continue; // Skip if the productType is not recognized
                    }

                    Product product = new Product(type, name, basePrice, maxSellPrice, minSellPrice, quality, minQuality, maxQuality, addedDate, expirationDate, canExpire);
                    System.out.println("Read: " + product.toString());

                    // Add products to shelves
                    List<Shelf> allShelves = supermarket.getShelves();
                    for ( Shelf shelf : allShelves ) {
                        if (Objects.equals(shelf.getName(), shelfName)){
                            if ((!product.isExpired() && product.canExpire()) || !product.canExpire()){
                                shelf.addProduct(product);
                                product.setShelfName(shelfName);
                                System.out.println("Added product " + name + " to shelf " + shelfName );
                            }
                            else{
                                System.out.println("Expired product " + name + " not added to shelf " + shelfName );
                            }
                        }
                    }

                }
            }
        }
        catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return supermarket;
    }
}
