import java.util.HashMap;
import java.util.Map;

public class BookMyStay {

    // Abstract Room class
    static abstract class Room {
        protected int beds;
        protected int size;
        protected double pricePerNight;

        public Room(int beds, int size, double pricePerNight) {
            this.beds = beds;
            this.size = size;
            this.pricePerNight = pricePerNight;
        }

        public abstract String getRoomType();

        public void displayDetails(int availability) {
            System.out.println(getRoomType() + " Room:");
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sqft");
            System.out.println("Price per night: " + pricePerNight);
            System.out.println("Available Rooms: " + availability);
            System.out.println();
        }
    }

    // Concrete Room Classes
    static class SingleRoom extends Room {
        public SingleRoom() {
            super(1, 250, 1500.0);
        }

        public String getRoomType() {
            return "Single";
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super(2, 400, 2500.0);
        }

        public String getRoomType() {
            return "Double";
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super(3, 750, 5000.0);
        }

        public String getRoomType() {
            return "Suite";
        }
    }

    // Inventory class using HashMap
    static class Inventory {
        private Map<String, Integer> availabilityMap;

        // Constructor initializes availability
        public Inventory() {
            availabilityMap = new HashMap<>();
            availabilityMap.put("Single", 5);
            availabilityMap.put("Double", 3);
            availabilityMap.put("Suite", 2);
        }

        // Get availability
        public int getAvailability(String roomType) {
            return availabilityMap.getOrDefault(roomType, 0);
        }

        // Add rooms (increase availability)
        public void addRooms(String roomType, int count) {
            availabilityMap.put(roomType,
                    getAvailability(roomType) + count);
        }

        // Book room (decrease availability safely)
        public boolean bookRoom(String roomType) {
            int available = getAvailability(roomType);
            if (available > 0) {
                availabilityMap.put(roomType, available - 1);
                return true;
            } else {
                System.out.println("No " + roomType + " rooms available!");
                return false;
            }
        }

        // Display full inventory
        public void displayInventory(Room room) {
            int available = getAvailability(room.getRoomType());
            room.displayDetails(available);
        }
    }

    public static void main(String[] args) {

        // Initialize rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize inventory
        Inventory inventory = new Inventory();

        System.out.println("Hotel Room Inventory Status\n");

        // Display initial inventory
        inventory.displayInventory(single);
        inventory.displayInventory(doubleRoom);
        inventory.displayInventory(suite);

    }
}