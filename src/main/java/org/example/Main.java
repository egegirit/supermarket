package org.example;

import java.io.File;
import java.net.URL;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        Supermarket supermarket = new Supermarket();

//        Shelf shelf1 = new Shelf("A");
//        Shelf shelf2 = new Shelf("B");
//
//        supermarket.addShelf(shelf1);
//        supermarket.addShelf(shelf2);
//        WineProduct wineProductType = new WineProduct();
//        CheeseProduct cheeseProductType = new CheeseProduct();
//
//        // Add max and min sell prices when creating the products
//        Product product1 = new Product(wineProductType, "Wine 1", 10.0,
//                20.0, 4.0, 3, -20, 10, LocalDate.now(), LocalDate.of(2024, 1, 1), false);
//        Product product2 = new Product(cheeseProductType, "Cheese 1", 5,
//                10.0, 3.0, 4, -2, 10, LocalDate.now(), LocalDate.of(2023, 7, 26), true);
//        Product product3 = new Product(wineProductType, "Wine 2", 5.0,
//                200.0, 0.0, 5, -20, 20, LocalDate.now(), LocalDate.of(2025, 1, 16), false);
//        Product product4 = new Product(cheeseProductType, "Cheese 2", 5,
//                00.0, 3.0, 4, -2, 10, LocalDate.now(), LocalDate.of(2023, 7, 2), true);
//        Product product5 = new Product(wineProductType, "Wine 3", 10.0,
//                30.0, 6.0, 6, -30, 90, LocalDate.now(), LocalDate.of(2022, 1, 4), false);
//        Product product6 = new Product(cheeseProductType, "Cheese that does not expire", 5,
//                14.0, 2.0, 50, 0, 140, LocalDate.now(), LocalDate.of(2022, 4, 26), false);
//
//        shelf1.addProduct(product1);
//        shelf1.addProduct(product3);
//        shelf1.addProduct(product4);
//        shelf2.addProduct(product2);
//        shelf2.addProduct(product5);
//        shelf2.addProduct(product6);

        // Import products from CSV file and add them to shelves
        String filename = "example_products.txt"; // Replace "file.txt" with the name of your file in the resources folder
        ClassLoader classLoader = Main.class.getClassLoader();
        URL resourceURL = classLoader.getResource(filename);
        String filePath = "";
        if (resourceURL == null) {
            // Handle the case where the resource is not found
            // Print an error message or throw an exception
        } else {
            filePath = resourceURL.getPath(); // Get the file path as a string

        }
        supermarket.importProductsFromCSV(filePath);

        System.out.println("==== Initial Inventory ====");
        supermarket.displayContent();
        System.out.println("===========================");

        int simulationDays = 100;
        LocalDate currentDate = LocalDate.now(); // Get the current system date
        for (int day = 1; day <= simulationDays; day++) {
            LocalDate simulationDate = currentDate.plusDays(day); // Increment the simulation date
            System.out.println("==== Day " + day + " [ " + simulationDate +  " ] ====");
            Clock customClock = Clock.fixed(simulationDate.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
            supermarket.setClock(customClock);
            supermarket.updateProducts();
            supermarket.displayContent();
            System.out.println();
        }
    }
}


