package org.example;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Supermarket {

    // Custom clock for simulation
    public static Clock clock;
    private List<Shelf> shelves;

    public Supermarket() {
        this.shelves = new ArrayList<>();
        clock = Clock.systemDefaultZone();
    }

    public Supermarket(Supermarket supermarket) {
        clock = supermarket.getClock();
        this.shelves = supermarket.getShelves();
    }

    public void setClock(Clock clock) {
        Supermarket.clock = clock;
    }
    public Clock getClock() {
        return clock;
    }

    public void addShelf(Shelf shelf) {
        shelves.add(shelf);
    }

    public List<Shelf> getShelves(){
        return shelves;
    }

    public void displayContent() {
        int shelfIndex = 1;
        for (Shelf shelf : shelves) {
            System.out.print("  " + shelfIndex + ". Shelf Name: ");
            shelf.displayContent();
            shelfIndex++;
        }
    }

    public void updateProducts() {
        for (Shelf shelf : shelves) {
            for (Product product : shelf.getProducts()) {
                if (!product.isExpired()) {
                    ProductType productType = product.getProductType();
                    productType.updateQuality(product);
                }
            }
            shelf.removeExpiredProducts(); // Automatically remove expired products from the shelf
        }
    }

    public void importProductsFromCSV(String filePath) {
        CSVReader csvReader = new CSVReader(filePath);
        try {
            Supermarket result = csvReader.readProducts(filePath, this);
            this.clock = result.getClock();
            this.shelves = result.getShelves();
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public void importProductsFromDatabase() {
        SQLReader sqlReader = new SQLReader();
        List<Product> products = sqlReader.readProductsFromDatabase();
        for (Product product : products) {
            Shelf targetShelf = shelves.stream()
                    .filter(shelf -> shelf.getName().equals(product.getShelfName()))
                    .findFirst()
                    .orElse(null);

            if (targetShelf != null) {
                if (!product.isExpired()) {
                    targetShelf.addProduct(product);
                    product.setShelfName(targetShelf.getName()); // Set the shelf name for the product
                } else {
                    System.out.println("Cannot add '" + product.getName() + "' to shelf '" +
                            targetShelf.getName() + "' as it is already expired.");
                }
            } else {
                System.out.println("Shelf '" + product.getShelfName() + "' not found for product '" +
                        product.getName() + "'.");
            }
        }
    }

}

