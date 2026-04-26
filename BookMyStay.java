import java.util.*;

public class BookMyStay{
// Custom Exception
public static class InvalidBookingException extends RuntimeException {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
public static class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Validator Class (Fail-Fast)
public static class BookingValidator {

    // Allowed room types
    private static final Set<String> validRoomTypes =
        new HashSet<>(Arrays.asList("Single", "Double", "Suite"));

    public static void validate(Reservation res, Map<String, Integer> inventory) {

        // Validate guest name
        if (res.guestName == null || res.guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!validRoomTypes.contains(res.roomType)) {
            throw new InvalidBookingException("Invalid room type: " + res.roomType);
        }

        // Validate inventory existence
        if (!inventory.containsKey(res.roomType)) {
            throw new InvalidBookingException("Room type not available in system.");
        }

        // Validate availability
        if (inventory.get(res.roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for " + res.roomType);
        }
    }
}

    public static void main(String[] args) {
        // Inventory
    Map<String, Integer> inventory = new HashMap<>();

       System.out.println("========================================");
        System.out.println("Booking Validation System");
        System.out.println("========================================\n");

        Scanner sc = new Scanner(System.in);

        // Initialize inventory
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 0); // intentionally zero to test validation

        try {
            // Input
            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String type = sc.nextLine();

            Reservation res = new Reservation("R101",name, type);

            // Validation (Fail-Fast)
            BookingValidator.validate(res, inventory);

            // If validation passes → safe allocation
            inventory.put(type, inventory.get(type) - 1);

            System.out.println("\nBooking Confirmed!");
            System.out.println("Guest: " + name);
            System.out.println("Room Type: " + type);

        } catch (InvalidBookingException e) {
            // Graceful failure handling
            System.out.println("\nError: " + e.getMessage());
        }

        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("System remains stable...");
    }
}