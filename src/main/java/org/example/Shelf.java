package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

class Shelf {
    private String name;
    private List<Product> products;

    public Shelf(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product.canExpire() && product.isExpired()) {
            System.out.println("Cannot add '" + product.getName() + "' to shelf '" + name + "' as it is already expired.");
        } else {
            products.add(product);
            System.out.println("Added '" + product.getName() + "' to shelf '" + name + "'.");
        }
    }

    public void removeProduct(Product product) {
        products.remove(product);
        System.out.println("Removed " + product.getName() + " from shelf " + name + ".");
    }

    public void removeExpiredProducts() {
        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.canExpire()){
                if (product.isExpired()) {
                    System.out.println("Removed expired product: " + product.getName() + " from shelf " + name + ".");
                    iterator.remove();
                } else if (product.isAboutToExpire()) {
                    System.out.println("(!) Warning: Product " + product.getName() + " is about to expire soon.");
                }
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getName(){
        return name;
    }

    public void displayContent() {
        System.out.println(name);
        int itemIndex = 1;
        for (Product product : products) {
            System.out.print("    " + itemIndex + ". Item: ");
            System.out.println(product.toString());
            itemIndex++;
        }
    }

    public Optional<Product> findProductByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst();
    }
}

