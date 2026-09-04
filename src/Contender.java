import java.util.concurrent.ThreadLocalRandom;

/**
 * Contender
 * Purpose:
 * This class represents a battle contender.
 * Each contender runs as its own thread by implementing the Runnable interface. This allows both contenders to compete
 * at the same time
 */
public class Contender implements Runnable {

    // Shared flag: when set to true, the battle ends for all contenders
    public static boolean winner = false;

    // Instance variables required by the assignment
    private String name;          // contender name
    private int spreadAmount;     // iterable action amount per turn
    private int totalSpread;      // running total for the action
    private int maxRestMs;        // maximum rest time (ms)

    // Tracks how many times the contender has performed the action
    private int actionCount;

    // Resource used by both threads
    private Water water;

    // Determines how long THIS contender drinks
    private int drinkTimeMs;

    /**
     * Purpose:
     * Constructs a Contender object with the required values.
     *
     * @param name         contender name
     * @param spreadAmount amount added to totalSpread each action
     * @param maxRestMs    maximum amount of time that a contender can rest
     * @param water        shared Water object used for drinking breaks
     * @param drinkTimeMs  the measurement of how long this contender drinks during each break
     */
    public Contender(String name, int spreadAmount, int maxRestMs, Water water, int drinkTimeMs) {
        this.name = name;
        this.spreadAmount = spreadAmount;
        this.maxRestMs = maxRestMs;
        this.water = water;
        this.drinkTimeMs = drinkTimeMs;

        // Conditions before battle begins
        this.totalSpread = 0;
        this.actionCount = 0;
    }

    /**
     * Purpose:
     * Returns a readable description of the contender’s settings.
     *
     * @return String description of this contender
     */
    @Override
    public String toString() {
        return "Contender{name='" + name + '\'' +
                ", spreadAmount=" + spreadAmount +
                ", maxRestMs=" + maxRestMs +
                ", drinkTimeMs=" + drinkTimeMs +
                ", totalSpread=" + totalSpread +
                ", actionCount=" + actionCount +
                '}';
    }

    /**
     * Purpose:
     * Runs the contender thread.
     * Continues to Loop until a winner is determined.
     */
    @Override
    public void run() {
        while (!winner) {

            // 1) Rest (random time <= maxRestMs)
            int restTime = ThreadLocalRandom.current().nextInt(maxRestMs + 1);
            try {
                Thread.sleep(restTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(name + " was interrupted while resting.");
                return;
            }

            // 2) Perform action (iterate running total + action counter)
            totalSpread += spreadAmount; // arithmetic calculation: increases spread score
            actionCount++;               // counts how many times the action was performed

            // 3) Print progress
            System.out.println(name + " spreads! Total spread score = " + totalSpread
                    + " | Actions performed = " + actionCount);

            // 4) Check winner condition (200 actions)
            if (actionCount >= 200) {
                winner = true;
                System.out.println("\n WINNER: " + name + " spreads the easiest on bread!");
                break;
            }

            // 5) Water break (shared synchronized method)
            water.drink(name, drinkTimeMs);
        }
    }
}
