import java.security.Provider.Service;
import java.util.*;

public class BookMyStay{
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

// Booking History (stores confirmed reservations)
public static class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation res) {
        history.add(res);
    }

    // Retrieve history (read-only purpose)
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Reporting Service
public static class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> history) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : history) {
            System.out.println(r);
        }
    }

    // Summary report
    public void generateSummary(List<Reservation> history) {
        Map<String, Integer> countByType = new HashMap<>();

        for (Reservation r : history) {
            countByType.put(r.roomType,
                countByType.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("\n--- Booking Summary Report ---");
        for (String type : countByType.keySet()) {
            System.out.println(type + " Rooms Booked: " + countByType.get(type));
        }

        System.out.println("Total Bookings: " + history.size());
    }
}

    public static void main(String[] args) {
       System.out.println("========================================");
        System.out.println("Booking History & Reporting System");
        System.out.println("========================================\n");

        // Booking history
        BookingHistory bookingHistory = new BookingHistory();

        // Simulate confirmed bookings (from previous system)
        bookingHistory.addReservation(new Reservation("R101", "Asha", "Single"));
        bookingHistory.addReservation(new Reservation("R102", "Ravi", "Double"));
        bookingHistory.addReservation(new Reservation("R103", "Neha", "Suite"));
        bookingHistory.addReservation(new Reservation("R104", "Karan", "Single"));

        // Reporting service
        BookingReportService reportService = new BookingReportService();

        // Admin views history
        reportService.showAllBookings(bookingHistory.getAllReservations());

        // Admin generates summary
        reportService.generateSummary(bookingHistory.getAllReservations());

        System.out.println("\nReporting completed without modifying booking data.");
        System.out.println("Program continues safely...");
    }
}