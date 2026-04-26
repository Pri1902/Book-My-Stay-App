import java.util.*;
import java.io.*;

public class BookMyStay{

    // Reservation class (Serializable)
    static class Reservation implements Serializable {
        String reservationId;
        String guestName;
        String roomType;

        public Reservation(String id, String name, String type) {
            this.reservationId = id;
            this.guestName = name;
            this.roomType = type;
        }

        public String toString() {
            return reservationId + " | " + guestName + " | " + roomType;
        }
    }

    // Wrapper class for system state
    static class SystemState implements Serializable {
        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        public SystemState(Map<String, Integer> inventory, List<Reservation> history) {
            this.inventory = inventory;
            this.bookingHistory = history;
        }
    }

    // Persistence Service
    static class PersistenceService {

        private static final String FILE_NAME = "system_state.dat";

        // Save state
        public static void save(SystemState state) {
            try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                oos.writeObject(state);
                System.out.println("\nState saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving state: " + e.getMessage());
            }
        }

        // Load state
        public static SystemState load() {
            try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                System.out.println("State loaded from file.");
                return (SystemState) ois.readObject();

            } catch (FileNotFoundException e) {
                System.out.println("No previous data found. Starting fresh.");
            } catch (Exception e) {
                System.out.println("Corrupted data. Starting with safe defaults.");
            }

            return null; // fallback
        }
    }

public static void main(String[] args) {
       System.out.println("========================================");
        System.out.println("Persistence & Recovery System");
        System.out.println("========================================\n");

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        // Load previous state (Recovery)
        SystemState loadedState = PersistenceService.load();

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookingHistory = loadedState.bookingHistory;
        } else {
            // Default initialization
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 1);

            bookingHistory = new ArrayList<>();
        }

        // Simulate a booking
        Reservation r1 = new Reservation("R" + System.currentTimeMillis(),
                "Asha", "Single");

        if (inventory.get("Single") > 0) {
            inventory.put("Single", inventory.get("Single") - 1);
            bookingHistory.add(r1);
            System.out.println("Booking Confirmed: " + r1);
        } else {
            System.out.println("No rooms available.");
        }

        // Show current state
        System.out.println("\nCurrent Inventory: " + inventory);
        System.out.println("Booking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        // Save state before shutdown
        SystemState state = new SystemState(inventory, bookingHistory);
        PersistenceService.save(state);

        System.out.println("\nSystem shutdown safely...");
    }
}