package org.example;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class WineProduct extends ProductType {
    public WineProduct() {
        super(50.0, 1.0, false); // Maximum sell price: $20, Minimum sell price: $5
    }

    @Override
    public void updateQuality(Product product) {
        int oldProductQuality = product.getQuality();
        double oldProductPrice = product.getSellPrice();

        // TODO: Fix the dates,
        // Store the initial quality, base price values separately and calculate the new quality and prices every time using these

        long daysPassed = ChronoUnit.DAYS.between(product.getDateAdded(), LocalDate.now(Supermarket.clock));
        int updatedQuality = product.getInitialQuality() + (int) daysPassed;

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

