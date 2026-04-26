import java.util.*;

public class BookMyStay{
    public static class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}
    // Inventory: room type → available count
    static Map<String, Integer> inventory = new HashMap<>();

    // Allocated rooms: room type → set of room IDs
    static Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Queue for booking requests (FIFO)
    static Queue<Reservation> bookingQueue = new LinkedList<>();

    // Unique room ID generator
    static int roomCounter = 1;

    public static void main(String[] args) {
      System.out.println("========================================");
        System.out.println("Room Allocation System (Safe Booking)");
        System.out.println("========================================\n");

        Scanner sc = new Scanner(System.in);

        // Initialize inventory
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());

        // Input booking requests
        System.out.print("Enter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Request " + (i + 1));

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String type = sc.nextLine();

            bookingQueue.add(new Reservation(name, type));
        }

        // Process booking requests
        processBookings();

        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("Allocated Rooms: " + allocatedRooms);

        System.out.println("\nProgram continues safely...");
    }

    // Booking processing logic
    public static void processBookings() {

        while (!bookingQueue.isEmpty()) {

            Reservation req = bookingQueue.poll(); // FIFO

            String type = req.roomType;

            System.out.println("\nProcessing booking for: " + req.guestName);

            // Check availability
            if (!inventory.containsKey(type) || inventory.get(type) == 0) {
                System.out.println("No rooms available for " + type);
                continue;
            }

            // Generate unique room ID
            String roomId = type.substring(0, 1).toUpperCase() + roomCounter++;

            // Ensure uniqueness using Set
            Set<String> roomSet = allocatedRooms.get(type);

            if (roomSet.contains(roomId)) {
                System.out.println("Error: Duplicate room detected");
                continue;
            }

            // Atomic allocation
            roomSet.add(roomId);
            inventory.put(type, inventory.get(type) - 1);

            // Confirm booking
            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + req.guestName);
            System.out.println("Room Type: " + type);
            System.out.println("Room ID: " + roomId);
        }
    }
}