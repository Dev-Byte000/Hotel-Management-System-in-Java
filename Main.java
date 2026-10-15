import java.util.Scanner;

class Room {
    private int roomNo;
    private String type;
    private double rate;
    private boolean booked;
    private int capacity;

    public Room(int roomNo, String type,
                double rate, int capacity) {
        this.roomNo = roomNo;
        this.type = type;
        this.rate = rate;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
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
    private String phone;
    private String address;
    private int roomNo;
    private int daysStayed;
    private double foodCharges;

    public Guest(String guestId,
                 String name,
                 String phone,
                 String address,
                 int roomNo,
                 int daysStayed) {

        this.guestId = guestId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.roomNo = roomNo;
        this.daysStayed = daysStayed;
        this.foodCharges = 0;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public int getDaysStayed() {
        return daysStayed;
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

    String[] foodItems =
            {"Burger", "Fries", "Soda", "Pizza"};

    double[] foodPrices =
            {450, 200, 150, 800};

    public HotelManager() {

        rooms = new Room[5];

        rooms[0] = new Room(101,
                "Single", 1000, 1);

        rooms[1] = new Room(102,
                "Double", 1500, 2);

        rooms[2] = new Room(103,
                "Double", 1500, 2);

        rooms[3] = new Room(201,
                "Single", 1000, 1);

        rooms[4] = new Room(202,
                "Suite", 3000, 4);

        guests = new Guest[50];

        guestCount = 0;
        nextId = 1001;
        revenue = 0;
    }

    private int findRoom(int roomNo) {

        for (int i = 0; i < rooms.length; i++) {

            if (rooms[i].getRoomNo() == roomNo)
                return i;
        }

        return -1;
    }

    private int findGuest(String guestId) {

        for (int i = 0; i < guestCount; i++) {

            if (guests[i]
                    .getGuestId()
                    .equals(guestId))
                return i;
        }

        return -1;
    }

    public void displayRooms() {

        System.out.println(
                "\nRoomNo\tType\tRate\tCapacity\tStatus");

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
                            room.getCapacity() + "\t\t" +
                            status
            );
        }
    }

    public void checkIn() {

        displayRooms();

        System.out.print(
                "\nEnter Room Number: ");

        int roomNo = sc.nextInt();
        sc.nextLine();

        int roomIndex = findRoom(roomNo);

        if (roomIndex == -1 ||
                rooms[roomIndex].isBooked()) {

            System.out.println(
                    "Room Not Available!");
            return;
        }

        System.out.print(
                "Enter Guest Name: ");
        String name = sc.nextLine();

        System.out.print(
                "Enter Phone Number: ");
        String phone = sc.nextLine();

        System.out.print(
                "Enter Address: ");
        String address = sc.nextLine();

        System.out.print(
                "Enter Number of Days: ");
        int days = sc.nextInt();

        String guestId = "G" + nextId++;

        guests[guestCount] =
                new Guest(
                        guestId,
                        name,
                        phone,
                        address,
                        roomNo,
                        days
                );

        guestCount++;

        rooms[roomIndex]
                .setBooked(true);

        System.out.println(
                "\nCheck-In Successful");

        System.out.println(
                "Guest ID : " + guestId);
    }

    public void orderFood() {

        System.out.print(
                "Enter Guest ID: ");

        String guestId = sc.next();

        int guestIndex =
                findGuest(guestId);

        if (guestIndex == -1) {

            System.out.println(
                    "Guest Not Found!");
            return;
        }

        System.out.println(
                "\n----- FOOD MENU -----");

        for (int i = 0;
             i < foodItems.length;
             i++) {

            System.out.println(
                    (i + 1)
                            + ". "
                            + foodItems[i]
                            + " - Rs."
                            + foodPrices[i]);
        }

        while (true) {

            System.out.print(
                    "Enter Item Number (0 Exit): ");

            int choice =
                    sc.nextInt();

            if (choice == 0)
                break;

            if (choice < 1 ||
                    choice > 4) {

                System.out.println(
                        "Invalid Item!");
                continue;
            }

            System.out.print(
                    "Enter Quantity: ");

            int qty =
                    sc.nextInt();

            double cost =
                    foodPrices[
                            choice - 1]
                            * qty;

            guests[guestIndex]
                    .addFoodCharges(cost);

            System.out.println(
                    "Added. Cost = Rs."
                            + cost);
        }
    }

    public void searchGuest() {

        System.out.print(
                "Enter Guest ID: ");

        String id =
                sc.next();

        int index =
                findGuest(id);

        if (index == -1) {

            System.out.println(
                    "Guest Not Found");
            return;
        }

        Guest g =
                guests[index];

        System.out.println(
                "\nGuest Details");

        System.out.println(
                "ID : " +
                        g.getGuestId());

        System.out.println(
                "Name : " +
                        g.getName());

        System.out.println(
                "Phone : " +
                        g.getPhone());

        System.out.println(
                "Address : " +
                        g.getAddress());

        System.out.println(
                "Room No : " +
                        g.getRoomNo());
    }

    public void checkOut() {

        System.out.print(
                "Enter Guest ID: ");

        String guestId =
                sc.next();

        int guestIndex =
                findGuest(guestId);

        if (guestIndex == -1) {

            System.out.println(
                    "Guest Not Found!");
            return;
        }

        Guest guest =
                guests[guestIndex];

        int roomIndex =
                findRoom(
                        guest.getRoomNo());

        double roomBill =
                rooms[roomIndex].getRate()
                        * guest.getDaysStayed();

        double subtotal =
                roomBill
                        + guest.getFoodCharges();

        double discount = 0;

        if (subtotal > 10000)
            discount =
                    subtotal * 0.10;

        double afterDiscount =
                subtotal - discount;

        double gst =
                afterDiscount * 0.18;

        double finalBill =
                afterDiscount + gst;

        System.out.println(
                "\n=========== HOTEL BILL ===========");

        System.out.println(
                "Guest ID      : "
                        + guest.getGuestId());

        System.out.println(
                "Guest Name    : "
                        + guest.getName());

        System.out.println(
                "Room Number   : "
                        + guest.getRoomNo());

        System.out.println(
                "Days Stayed   : "
                        + 
