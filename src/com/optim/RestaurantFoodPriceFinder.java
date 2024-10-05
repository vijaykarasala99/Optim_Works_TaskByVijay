package com.optim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class RestaurantFoodPriceFinder {

    // Method to load CSV data into a map with restaurant IDs and their food items
    public static Map<Integer, Map<String, Double>> loadCSVData(String filePath) {
        Map<Integer, Map<String, Double>> restaurantData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header line and ignore it
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split by comma for CSV format
                int restaurantId = Integer.parseInt(values[0].trim()); // Restaurant ID
                double price = Double.parseDouble(values[1].trim()); // Food item price

                for (int i = 2; i < values.length; i++) { // Iterate through food items
                    String foodItem = values[i].trim().toLowerCase(); // Normalize food item names
                    if (!foodItem.isEmpty()) { // Only add non-empty food items
                        restaurantData.putIfAbsent(restaurantId, new HashMap<>());
                        restaurantData.get(restaurantId).put(foodItem, price);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }

        return restaurantData;
    }

    // Method to find the minimum price for the specified food items in a restaurant
    public static String findMinimumPrice(Map<Integer, Map<String, Double>> restaurantData, String[] foodItems) {
        double minTotalPrice = Double.MAX_VALUE;
        int minRestaurantId = -1;

        // Convert foodItems array to a set for easy lookup
        Set<String> foodSet = new HashSet<>();
        for (String foodItem : foodItems) {
            foodSet.add(foodItem.toLowerCase());
        }

        // Traverse through each restaurant's menu
        for (Map.Entry<Integer, Map<String, Double>> entry : restaurantData.entrySet()) {
            int restaurantId = entry.getKey();
            Map<String, Double> menu = entry.getValue();

            // Check if all food items are available in the restaurant
            if (menu.keySet().containsAll(foodSet)) {
                // Calculate the total price of all food items in the restaurant
                double totalPrice = 0;
                for (String foodItem : foodItems) {
                    totalPrice += menu.get(foodItem.toLowerCase());
                }

                // Update minimum price and restaurant ID if the current price is lower
                if (totalPrice < minTotalPrice) {
                    minTotalPrice = totalPrice;
                    minRestaurantId = restaurantId;
                }
            }
        }

        // Return the result based on whether a matching restaurant was found
        if (minRestaurantId == -1) {
            return "No matching restaurant found";
        } else {
            return minRestaurantId + ", " + minTotalPrice;
        }
    }

    public static void main(String[] args) {
        // File path for the CSV data
        String filePath = "C:\\Users\\vijay\\Downloads\\data.csv"; // Update with your file path

        // Load restaurant data from CSV file
        Map<Integer, Map<String, Double>> restaurantData = loadCSVData(filePath);

        // Input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the food items separated by space (e.g., burger tofu_log): ");
        String input = scanner.nextLine();
        String[] foodItems = input.split(" ");

        // Find and display the minimum price for the specified food items
        String result = findMinimumPrice(restaurantData, foodItems);
        System.out.println(result);

        scanner.close();
    }
}
