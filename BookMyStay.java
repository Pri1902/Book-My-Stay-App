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

    //  Make subclasses static
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

    public static void main(String[] args) {
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Hotel Room Initialization:\n");

        single.displayDetails(singleAvailable);
        doubleRoom.displayDetails(doubleAvailable);
        suite.displayDetails(suiteAvailable);
    }
}
