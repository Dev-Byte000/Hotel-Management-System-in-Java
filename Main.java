import java.util.Scanner;

class Room {
    private int roomNo;
    private String type;
    private double rate;
    private boolean booked;

    public Room(int roomNo, String type, double rate) {
        this.roomNo = roomNo;
        this.type = type;
        this.rate = rate;
        this.booked = false;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public String getType() {
        return type;
    }

    public double getRate() {
        return rate;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}

class Guest {
    private String guestId;
    private String name;
    private int roomNo;
    private double foodCharges;

    public Guest(String guestId, String name, int roomNo) {
        this.guestId = guestId;
        this.name = name;
        this.roomNo = roomNo;
        this.foodCharges = 0;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public double getFoodCharges() {
        return foodCharges;
    }

    public void addFoodCharges(double amount) {
        foodCharges += amount;
    }
}

class HotelManager {

    private Room[] rooms;
    private Guest[] guests;

    private int guestCount;
    private int nextId;
    private double revenue;

    Scanner sc = new Scanner(System.in);

    String[] foodItems = {"Burger", "Fries", "Soda", "Pizza"};
    double[] foodPrices = {450, 200, 150, 800};

    public HotelManager() {

        rooms = new Room[5];

        rooms[0] = new Room(101, "Single", 1000);
        rooms[1] = new Room(102, "Double", 1500);
        rooms[2] = new Room(103, "Double", 1500);
        rooms[3] = new Room(201, "Single", 1000);
        rooms[4] = new Room(202, "Suite", 3000);

        guests = new Guest[50];

        guestCount = 0;
        nextId = 1001;
        revenue = 0;
    }

    private int findRoom(int roomNo) {

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getRoomNo() == roomNo) {
                return i;
            }
        }
        return -1;
    }

    private int findGuest(String guestId) {

        for (int i = 0; i < guestCount; i++) {
            if (guests[i].getGuestId().equals(guestId)) {
                return i;
            }
        }
        return -1;
    }

    public void displayRooms() {

        System.out.println("\nRoom No\tType\tRate\tStatus");

        for (Room room : rooms) {

            String status;

            if (room.isBooked())
                status = "Booked";
            else
                status = "Available";

            System.out.println(
                room.getRoomNo() + "\t" +
                room.getType() + "\t" +
                room.getRate() + "\t" +
                status
            );
        }
    }

    public void checkIn() {

        displayRooms();

        System.out.print("\nEnter Room Number: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        int roomIndex = findRoom(roomNo);

        if (roomIndex == -1 || rooms[roomIndex].isBooked()) {
            System.out.println("Room not available!");
            return;
        }

        System.out.print("Enter Guest Name: ");
        String name = sc.nextLine();

        String guestId = "G" + nextId++;

        guests[guestCount] =
                new Guest(guestId, name, roomNo);

        guestCount++;

        rooms[roomIndex].setBooked(true);

        System.out.println("\nCheck-In Successful");
        System.out.println("Guest ID: " + guestId);
    }

    public void orderFood() {

        System.out.print("Enter Guest ID: ");
        String guestId = sc.next();

        int guestIndex = findGuest(guestId);

        if (guestIndex == -1) {
            System.out.println("Guest not found!");
            return;
        }

        System.out.println("\n------ FOOD MENU ------");

        for (int i = 0; i < foodItems.length; i++) {

            System.out.println(
                    (i + 1) + ". " +
                    foodItems[i] +
                    " - Rs." +
                    foodPrices[i]
            );
        }

        while (true) {

            System.out.print("Enter Item Number (0 to Exit): ");
            int choice = sc.nextInt();

            if (choice == 0)
                break;

            if (choice < 1 || choice > 4) {
                System.out.println("Invalid Item!");
                continue;
            }

            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            double cost = foodPrices[choice - 1] * qty;

            guests[guestIndex].addFoodCharges(cost);

            System.out.println("Added. Cost = Rs." + cost);
        }
    }

    public void checkOut() {

        System.out.print("Enter Guest ID: ");
        String guestId = sc.next();

        int guestIndex = findGuest(guestId);

        if (guestIndex == -1) {
            System.out.println("Guest not found!");
            return;
        }

        Guest guest = guests[guestIndex];

        int roomIndex = findRoom(guest.getRoomNo());

        double roomBill =
                rooms[roomIndex].getRate() * 3;

        double totalBill =
                roomBill + guest.getFoodCharges();

        System.out.println("\n===== FINAL BILL =====");

        System.out.println(
                "Guest Name: " +
                guest.getName());

        System.out.println(
                "Room Charges: Rs." +
                roomBill);

        System.out.println(
                "Food Charges: Rs." +
                guest.getFoodCharges());

        System.out.println(
                "Total Bill: Rs." +
                totalBill);

        revenue += totalBill;

        rooms[roomIndex].setBooked(false);

        for (int i = guestIndex;
             i < guestCount - 1;
             i++) {

            guests[i] = guests[i + 1];
        }

        guestCount--;

        System.out.println("Check-Out Successful");
    }

    public void viewGuests() {

        if (guestCount == 0) {
            System.out.println("No Guests Available.");
            return;
        }

        for (int i = 0; i < guestCount; i++) {

            System.out.println("\nGuest ID: "
                    + guests[i].getGuestId());

            System.out.println("Name: "
                    + guests[i].getName());

            System.out.println("Room No: "
                    + guests[i].getRoomNo());

            System.out.println("Food Charges: Rs."
                    + guests[i].getFoodCharges());
        }
    }

    public void viewRevenue() {

        System.out.println(
                "\nTotal Revenue Collected: Rs."
                        + revenue);
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        HotelManager hotel =
                new HotelManager();

        int choice;

        do {

            System.out.println("\n===== HOTEL MANAGEMENT SYSTEM =====");
            System.out.println("1. Display Rooms");
            System.out.println("2. Check In");
            System.out.println("3. Check Out");
            System.out.println("4. View Guests");
            System.out.println("5. Order Food");
            System.out.println("6. View Revenue");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    hotel.displayRooms();
                    break;

                case 2:
                    hotel.checkIn();
                    break;

                case 3:
                    hotel.checkOut();
                    break;

                case 4:
                    hotel.viewGuests();
                    break;

                case 5:
                    hotel.orderFood();
                    break;

                case 6:
                    hotel.viewRevenue();
                    break;

                case 7:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 7);

        sc.close();
    }
}