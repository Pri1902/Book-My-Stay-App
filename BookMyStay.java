import java.util.*;

// Main Class
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
            System.out.println("Available: " + availability);
            System.out.println();
        }
    }

    // Room Types
    static class SingleRoom extends Room {
        public SingleRoom() { super(1, 250, 1500.0); }
        public String getRoomType() { return "Single"; }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() { super(2, 400, 2500.0); }
        public String getRoomType() { return "Double"; }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() { super(3, 750, 5000.0); }
        public String getRoomType() { return "Suite"; }
    }

    // Inventory Class
    static class Inventory {
        private Map<String, Integer> availabilityMap;

        public Inventory() {
            availabilityMap = new HashMap<>();
            availabilityMap.put("Single", 5);
            availabilityMap.put("Double", 3);
            availabilityMap.put("Suite", 2);
        }

        public int getAvailability(String roomType) {
            return availabilityMap.getOrDefault(roomType, 0);
        }

        // Booking logic (separate from search)
        public boolean bookRoom(String roomType) {
            int available = getAvailability(roomType);
            if (available > 0) {
                availabilityMap.put(roomType, available - 1);
                return true;
            }
            return false;
        }
    }

    // Room Search 
    static class RoomSearch {

        private Inventory inventory;

        public RoomSearch(Inventory inventory) {
            this.inventory = inventory;
        }

        public void searchAvailableRooms(List<Room> rooms) {
            System.out.println("Room Search\n");

            for (Room room : rooms) {
                int available = inventory.getAvailability(room.getRoomType());

                // Filter unavailable rooms
                if (available > 0) {
                    room.displayDetails(available);
                }
            }
        }
    }

    public static void main(String[] args) {

        // Initialize rooms
        List<Room> rooms = Arrays.asList(
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        );

        // Initialize inventory
        Inventory inventory = new Inventory();

        // Initialize search system
        RoomSearch search = new RoomSearch(inventory);

        // Perform search 
        search.searchAvailableRooms(rooms);
    }
}