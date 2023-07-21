package org.example;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class CheeseProduct extends ProductType {
    public CheeseProduct() {
        super(15.0, 2.0, true); // Maximum sell price: $10, Minimum sell price: $2
    }
    @Override
    public void updateQuality(Product product) {
        long daysPassed = ChronoUnit.DAYS.between(product.getDateAdded(), LocalDate.now(Supermarket.clock));
        int oldProductQuality = product.getQuality();
        double oldProductPrice = product.getSellPrice();
        int updatedQuality = product.getInitialQuality() - (int) (daysPassed / 3);

        // Apply the maximum and minimum quality limits
        updatedQuality = clampQuality(updatedQuality, product.getMinQuality(), product.getMaxQuality());

        product.setQuality(updatedQuality);

        // Apply the maximum and minimum sell price limits
        double updatedSellPrice = product.calculateSellPrice();
        if (updatedSellPrice > product.getMaxSellPrice()) {
            updatedSellPrice = product.getMaxSellPrice();
        } else if (updatedSellPrice < product.getMinSellPrice()) {
            updatedSellPrice = product.getMinSellPrice();
        }
        product.setSellPrice(updatedSellPrice);

        System.out.println("  Logs for (" + product.getName() + "): ");
        System.out.println("    Days passed: " + daysPassed);
        System.out.println("    Updated Quality from " + oldProductQuality + " to " + updatedQuality);
        System.out.println("    Updated Price from " + oldProductPrice + " to " + updatedSellPrice);

    }
}