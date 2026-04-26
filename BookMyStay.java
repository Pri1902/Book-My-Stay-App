import java.util.*;

public class BookMyStay{
    public static class Reservation {
    String guestName;
    String roomType; // Single, Double, Suite

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return "Guest: " + guestName + " | Room Type: " + roomType;
    }
}
    public static void main(String[] args) {
     System.out.println("========================================");
        System.out.println("Booking Request Intake System (Queue)");
        System.out.println("========================================\n");

        Scanner sc = new Scanner(System.in);

        // Queue to store booking requests (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();

        System.out.print("Enter number of booking requests: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        // Accept booking requests
        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Request " + (i + 1));

            System.out.print("Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Room Type (Single/Double/Suite): ");
            String roomType = sc.nextLine();

            // Add to queue
            bookingQueue.add(new Reservation(name, roomType));
        }

        // Display queue (arrival order preserved)
        System.out.println("\nBooking Requests in Queue (FIFO Order):");
        for (Reservation r : bookingQueue) {
            System.out.println(r);
        }

        System.out.println("\nNote: No rooms allocated yet. Requests are waiting for processing.");
        System.out.println("Program continues safely...");
    }
}