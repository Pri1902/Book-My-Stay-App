import java.util.*;

public class BookMyStay{

    // Reservation class
    static class Reservation {
        String reservationId;
        String guestName;
        String roomType;
        String roomId;

        public Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
        }
    }

    // Inventory
    static Map<String, Integer> inventory = new HashMap<>();

    // Active bookings
    static Map<String, Reservation> activeBookings = new HashMap<>();

    // History (for audit)
    static List<String> bookingHistory = new ArrayList<>();

    // Stack for rollback (released room IDs)
    static Stack<String> rollbackStack = new Stack<>();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Cancellation System (Safe Rollback)");
        System.out.println("========================================\n");

        // Initialize inventory
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);

        // Simulate confirmed booking
        Reservation r1 = new Reservation("R101", "Asha", "Single", "S1");
        activeBookings.put(r1.reservationId, r1);
        inventory.put("Single", inventory.get("Single") - 1);
        bookingHistory.add("Booked: " + r1.reservationId);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Reservation ID to cancel: ");
        String resId = sc.nextLine();

        cancelBooking(resId);

        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("Rollback Stack: " + rollbackStack);
        System.out.println("Booking History: " + bookingHistory);

        System.out.println("\nSystem remains consistent...");
    }

    // Cancellation Logic
    public static void cancelBooking(String reservationId) {

        // Validate existence
        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("Error: Reservation does not exist or already cancelled.");
            return;
        }

        Reservation res = activeBookings.get(reservationId);

        System.out.println("\nProcessing cancellation for: " + res.guestName);

        // Step 1: Push room ID to rollback stack (LIFO tracking)
        rollbackStack.push(res.roomId);

        // Step 2: Restore inventory
        inventory.put(res.roomType, inventory.get(res.roomType) + 1);

        // Step 3: Remove from active bookings
        activeBookings.remove(reservationId);

        // Step 4: Update history
        bookingHistory.add("Cancelled: " + reservationId);

        // Confirmation
        System.out.println("Cancellation successful!");
        System.out.println("Room Released: " + res.roomId);
    }
}