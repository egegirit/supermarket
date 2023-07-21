package org.example;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

class Product {

    private String name;
    private String shelfName;
    private double initialBasePrice;
    private double basePrice;
    private double sellPrice;
    private double maxSellPrice;
    private double minSellPrice;
    private int initialQuality;
    private int quality;
    private int minQuality;
    private int maxQuality;
    private LocalDate expirationDate;
    private LocalDate dateAdded;
    private ProductType productType;
    private boolean canExpire;

    public Product(ProductType productType, String name, double basePrice, double maxSellPrice, double minSellPrice,
                   int quality, int minQuality, int maxQuality, LocalDate dateAdded, LocalDate expirationDate, boolean canExpire) {
        this.name = name;
        this.basePrice = basePrice;
        this.initialBasePrice = basePrice;
        this.quality = quality;
        this.initialQuality = quality;
        this.minQuality = minQuality;
        this.maxQuality = maxQuality;
        this.expirationDate = expirationDate;
        this.dateAdded = dateAdded;
        this.productType = productType;
        this.maxSellPrice = maxSellPrice;
        this.minSellPrice = minSellPrice;
        this.canExpire = canExpire;
        sellPrice = calculateSellPrice();
    }

    // Getters and Setters for the Product class
    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getInitialBasePrice() {
        return this.initialBasePrice;
    }

    public int getInitialQuality() {
        return this.initialQuality;
    }

    public int getMinQuality() {
        return minQuality;
    }

    public int getMaxQuality() {
        return maxQuality;
    }

    public double getMaxSellPrice() {
        return maxSellPrice;
    }

    public double getMinSellPrice() {
        return minSellPrice;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public int getQuality() {
        return quality;
    }

    public boolean canExpire() {
        return canExpire;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setMinQuality(int minQuality) {
        this.minQuality = minQuality;
    }

    public void setMaxQuality(int maxQuality) {
        this.maxQuality = maxQuality;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void updateQuality() {
        if (!isExpired()) {
            productType.updateQuality(this);
        }
    }

    // Method to calculate the sell price based on the quality and base price
    public double calculateSellPrice() {
        // double sellPrice = basePrice * (1 + 0.1 * quality); // Assuming a 10% increase in sell price for each quality point

        double sellPrice = initialBasePrice + quality; // Assuming a 10% increase in sell price for each quality point

        // Apply the maximum and minimum sell price limits from the ProductType
        if (sellPrice > productType.getMaxSellPrice()) {
            sellPrice = productType.getMaxSellPrice();
        } else if (sellPrice < productType.getMinSellPrice()) {
            sellPrice = productType.getMinSellPrice();
        }

        return sellPrice;

    }

    // Method to check if the product is expired
    public boolean isExpired() {
        // Get the current instant from the Clock
        Instant currentInstant = Instant.now(Supermarket.clock);
        // Convert the Instant to LocalDate using the default time zone
        LocalDate currentDate = currentInstant.atZone(ZoneId.systemDefault()).toLocalDate();
        // Check if the current date is after the expiration date
        if (this.canExpire){
            return currentDate.isAfter(this.expirationDate);
        } else{
            return false;
        }
    }

    // Displays a warning message that the product will expire in one week
    public boolean isAboutToExpire() {
        if (!this.canExpire){
            return false;
        }
        // Get the current instant from the Clock
        Instant currentInstant = Instant.now(Supermarket.clock);
        // Convert the Instant to LocalDate using the default time zone
        LocalDate currentDate = currentInstant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate oneWeekFromNow = currentDate.plusDays(7);
        return !isExpired() && expirationDate.isBefore(oneWeekFromNow);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Sell Price: " + sellPrice + ", Base Price: $" + basePrice + ", Quality: " + quality +
                ", Expiration Date: " + expirationDate + ", Date Added: " + dateAdded +
                ", Max Sell Price: $" + maxSellPrice + ", Min Sell Price: $" + minSellPrice;
    }
}
