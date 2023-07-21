package org.example;

abstract class ProductType {
    private final double maxSellPrice;
    private final double minSellPrice;
    private final boolean canExpire;

    public ProductType(double maxSellPrice, double minSellPrice, boolean canExpire) {
        this.maxSellPrice = maxSellPrice;
        this.minSellPrice = minSellPrice;
        this.canExpire = canExpire;
    }

    public double getMaxSellPrice() {
        return maxSellPrice;
    }

    public double getMinSellPrice() {
        return minSellPrice;
    }

    public boolean canExpire() {
        return canExpire;
    }

    // Helper method to ensure quality is within the limits
    protected int clampQuality(int quality, int minQuality, int maxQuality) {
        return Math.min(Math.max(quality, minQuality), maxQuality);
    }

    public abstract void updateQuality(Product product);

}
