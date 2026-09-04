/**
 * Water
 * Purpose:
 * This class represents a resource used by multiple contender threads.
 * Only one contender can drink at a time because drink() is synchronized.
 */
public class Water {

    /**
     * Purpose:
     * Simulates a contender taking a water break.
     * Because this method is synchronized, only one thread can drink at a time.
     *
     * @param contenderName The name of the contender that is drinking.
     * @param drinkTimeMs   The amount of time the contender will drink.
     * @return void The method does not return a value. It only performs the drinking action
     */
    public synchronized void drink(String contenderName, int drinkTimeMs) {
        System.out.println(contenderName + " starts drinking water for " + drinkTimeMs + " ms...");

        try {
            /* Sleep simulates the contender taking a water break, while also blocking the other contender from
             * taking a water break.
             */
            Thread.sleep(drinkTimeMs);
        } catch (InterruptedException e) {
            // Standard practice: restore interrupt status
            Thread.currentThread().interrupt();
            System.out.println(contenderName + " was interrupted while drinking water.");
        }

        System.out.println(contenderName + " finishes drinking water.");
    }
}
