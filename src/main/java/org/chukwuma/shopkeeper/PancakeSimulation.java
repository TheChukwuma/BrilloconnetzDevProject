package org.chukwuma.shopkeeper;

import java.util.Random;

public class PancakeSimulation {

    public static void main(String[] args) {
        int totalTime = 60; // Total simulation time in seconds (2 intervals of 30 seconds)
        int pancakesMade = 0;
        int pancakesEaten = 0;
        int pancakesNotMet = 0;

        for (int interval = 1; interval <= totalTime / 30; interval++) {
            int pancakesPerInterval = generateRandomPancakes(12); // Shopkeeper's production
            int user1Pancakes = generateRandomPancakes(5);
            int user2Pancakes = generateRandomPancakes(5);
            int user3Pancakes = generateRandomPancakes(5);

            int totalDemand = user1Pancakes + user2Pancakes + user3Pancakes;

            pancakesMade += pancakesPerInterval;
            pancakesEaten += Math.min(totalDemand, pancakesPerInterval);

            int notMet = totalDemand - pancakesEaten;
            if (notMet > 0) {
                pancakesNotMet += notMet;
            }

            System.out.println("Interval " + interval + ":");
            System.out.println("Starting Time: " + ((interval - 1) * 30) + " seconds");
            System.out.println("Ending Time: " + (interval * 30) + " seconds");
            System.out.println("Pancakes Made: " + pancakesPerInterval);
            System.out.println("Pancakes Eaten: " + pancakesEaten);
            System.out.println("Pancake Orders Not Met: " + pancakesNotMet);
            System.out.println();
        }
    }

    private static int generateRandomPancakes(int max) {
        Random random = new Random();
        return random.nextInt(max + 1);
    }
}

