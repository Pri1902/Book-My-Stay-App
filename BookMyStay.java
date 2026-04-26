import java.util.*;

public class BookMyStay{

    // Reservation class
public static class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System
public static class BookingSystem {

    // Shared resources
    Queue<Reservation> bookingQueue = new LinkedList<>();
    Map<String, Integer> inventory = new HashMap<>();

    // Constructor
    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Add booking request (synchronized)
    public synchronized void addBooking(Reservation r) {
        bookingQueue.add(r);
        System.out.println(Thread.currentThread().getName() +
                " added request for " + r.guestName);
    }

    // Process booking (critical section)
    public void processBooking() {

        while (true) {

            Reservation r;

            // Synchronize queue access
            synchronized (this) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                r = bookingQueue.poll();
            }

            // Critical section: allocation + inventory update
            synchronized (this) {

                System.out.println(Thread.currentThread().getName() +
                        " processing " + r.guestName);

                if (inventory.getOrDefault(r.roomType, 0) > 0) {

                    // Allocate room safely
                    inventory.put(r.roomType, inventory.get(r.roomType) - 1);

                    System.out.println("Booking Confirmed for " + r.guestName +
                            " (" + r.roomType + ")");
                } else {
                    System.out.println("No rooms available for " + r.guestName);
                }
            }
        }
    }
}    

public static void main(String[] args) {
       System.out.println("========================================");
        System.out.println("Concurrent Booking System");
        System.out.println("========================================\n");

        BookingSystem system = new BookingSystem();

        // Simulate multiple guests (concurrent requests)
        system.addBooking(new Reservation("Asha", "Single"));
        system.addBooking(new Reservation("Ravi", "Single"));
        system.addBooking(new Reservation("Neha", "Single")); // extra → conflict case
        system.addBooking(new Reservation("Karan", "Double"));

        // Create threads
        Thread t1 = new Thread(() -> system.processBooking(), "Thread-1");
        Thread t2 = new Thread(() -> system.processBooking(), "Thread-2");

        // Start threads (concurrent execution)
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
        System.out.println("Thread interrupted");
    }
        System.out.println("\nFinal Inventory: " + system.inventory);
        System.out.println("System remains consistent (no double booking).");
    }
}