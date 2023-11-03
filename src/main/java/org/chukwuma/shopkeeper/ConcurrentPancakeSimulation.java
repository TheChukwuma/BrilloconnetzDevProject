package org.chukwuma.shopkeeper;

import java.util.Random;

public class ConcurrentPancakeSimulation {
    public static void main(String[] args) {
        int totalSimulationTime = 60; // Total simulation time in seconds (2 intervals of 30 seconds each)
        int maxPancakesPerInterval = 12;
        int maxPancakesPerUser = 5;
        int numUsers = 3;

        int intervalTime = totalSimulationTime / 2; // 30 seconds per interval

        Random random = new Random();

        int totalPancakesMade = 0;
        int totalPancakesEaten = 0;
        int totalPancakesWasted = 0;
        int unmetOrders = 0;

        for (int interval = 0; interval < 2; interval++) {
            int pancakesMade = random.nextInt(maxPancakesPerInterval + 1);
            int pancakesOrderedByUsers = 0;

            for (int user = 0; user < numUsers; user++) {
                int pancakesOrdered = random.nextInt(maxPancakesPerUser + 1);
                pancakesOrderedByUsers += pancakesOrdered;
                totalPancakesEaten += Math.min(pancakesOrdered, maxPancakesPerUser);
            }

            totalPancakesMade += pancakesMade;

            if (pancakesMade < pancakesOrderedByUsers) {
                totalPancakesWasted += (pancakesOrderedByUsers - pancakesMade);
                unmetOrders++;
            }

            System.out.println("Interval Start Time: " + (interval * intervalTime));
            System.out.println("Interval End Time: " + ((interval + 1) * intervalTime));
            System.out.println("Pancakes Made: " + pancakesMade);
            System.out.println("Pancakes Ordered by Users: " + pancakesOrderedByUsers);
            System.out.println("Pancakes Eaten: " + totalPancakesEaten);
            System.out.println("Pancakes Wasted: " + totalPancakesWasted);
            System.out.println("Unmet Orders: " + unmetOrders);
            System.out.println("-------------");
        }

        System.out.println("Total Pancakes Made: " + totalPancakesMade);
        System.out.println("Total Pancakes Eaten: " + totalPancakesEaten);
        System.out.println("Total Pancakes Wasted: " + totalPancakesWasted);
        System.out.println("Total Unmet Orders: " + unmetOrders);
    }
}

