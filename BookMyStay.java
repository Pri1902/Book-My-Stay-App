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

// Add-On Service class
public static class Service {
    String serviceName;
    double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
public static class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (Service s : getServices(reservationId)) {
            total += s.cost;
        }
        return total;
    }
}

    public static void main(String[] args) {
      System.out.println("========================================");
        System.out.println("Add-On Services System");
        System.out.println("========================================\n");

        Scanner sc = new Scanner(System.in);

        // Create a sample reservation (already booked earlier)
        Reservation res = new Reservation("R101", "Asha", "Single");

        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.println("Reservation ID: " + res.reservationId);
        System.out.println("Guest: " + res.guestName);

        // Available services
        List<Service> availableServices = Arrays.asList(
            new Service("Breakfast", 200),
            new Service("WiFi", 100),
            new Service("Airport Pickup", 500)
        );

        System.out.println("\nAvailable Services:");
        for (int i = 0; i < availableServices.size(); i++) {
            System.out.println((i + 1) + ". " + availableServices.get(i));
        }

        // User selects services
        System.out.print("\nEnter number of services to add: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Select service number: ");
            int choice = sc.nextInt();

            if (choice >= 1 && choice <= availableServices.size()) {
                Service selected = availableServices.get(choice - 1);
                manager.addService(res.reservationId, selected);
            }
        }

        // Display selected services
        System.out.println("\nSelected Services:");
        List<Service> selectedServices = manager.getServices(res.reservationId);

        for (Service s : selectedServices) {
            System.out.println(s);
        }

        // Total cost
        double totalCost = manager.calculateTotalCost(res.reservationId);
        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);

        System.out.println("\nNote: Core booking and inventory remain unchanged.");
        System.out.println("Program continues safely...");
    }
}