# Supermarket Product Management System

This is a Java program for a supermarket to manage their products. The program allows users to add, remove, and manage products in the supermarket's inventory. 
Each product has various attributes such as name, base price, quality value, expiration date, and date added to the inventory. 
The sell price of products are calculated automatically and it is based on their quality and base price.
The program simulates the next 100 days and displays updates to products, their quality, and sell prices on each day of the simulation. This information will help users understand the program's behavior when they interact with it.

## Features

- Add new products to the inventory manually in the code.
- Manage product quality and sell price based on expiration date and product type.
- Simulate product quality and sell price over multiple days.
- Manage products on shelves with their associated information.
- Import products from a CSV file.
- (TODO) Import products from an SQLite database.

## Product Types

The program includes two example product types: `WineProduct` and `CheeseProduct`. Each product type has specific characteristics related to quality and expiration.
New product types can be added by extending the ProductType class.

## Requirements

- Java SE 8 or higher.

You can customize the example product information in the `example_products.csv` file or create your own SQLite database (`products.db`) with the `products` table.

## Data Files

- `example_products.csv`: An example CSV file containing product information for initialization.
- (TODO) `products.db`: An example SQLite database containing product information for initialization.

Feel free to use and modify the code as per your requirements.
