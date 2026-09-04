/**
 * Joyana Wilson
 * Course: COP 3330C - Object-Oriented Programming
 * Date: 02/22/2026
 *
 * Program Objective / Problem Description:
 * This program simulates a battle between Peanut Butter and Grape Jelly to see who spreads the easiest on bread.
 * Each contender has its own thread, which allows them to act concurrently. Only one contender can take a water break
 * at a time
 *
 * Inputs:
 * - No user input is required. All values are defined and coded to the application before the battle begins.
 *
 * Outputs:
 * - Prints each contender’s setup information before the battle starts.
 * - Prints each contender’s running spread total and how many times they performed the action.
 * - Prints messages when a contender starts and finishes drinking water.
 * - Announces the winner once a contender performs the action 200 times.
 * - The winner is announced and the battle ends
 */
public class UltimateBattleApp {

    public static void main(String[] args) {

        System.out.println("=== ULTIMATE BATTLE: Peanut Butter vs. Grape Jelly ===");
        System.out.println("Battle Rule: First contender to perform the action 200 times wins!\n");

        // One shared Water object used by both threads
        Water sharedWater = new Water();

        /*
         * Requirement:
         * One contender rests longer but performs more action per turn.
         * Also, contender #1 drinks longer than contender #2.
         */
        Contender peanutButter = new Contender(
                "Peanut Butter",
                4,      // spreads more each action
                250,    // rests longer
                sharedWater,
                300     // drinks longer
        );

        Contender grapeJelly = new Contender(
                "Grape Jelly",
                2,      // spreads less each action
                120,    // rests less
                sharedWater,
                120     // drinks faster
        );

        // Print contender setup
        System.out.println("Contender Setup: ");
        System.out.println(peanutButter);
        System.out.println(grapeJelly);
        System.out.println();

        // Generate threads
        Thread t1 = new Thread(peanutButter);
        Thread t2 = new Thread(grapeJelly);

        // Ensure user threads
        t1.setDaemon(false);
        t2.setDaemon(false);

        // Start the threads. The battle begins
        t1.start();
        t2.start();

        // Join threads so main waits for battle to finish
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(" WARNING: Main thread was interrupted.");
        }

        System.out.println("\n=== Battle ended. ===");
    }
}