package test2.service;

import test2.Booking;
import test2.Room;
import test2.RoomType;
import test2.User;

import java.util.ArrayList;
import java.util.Date;

public class HotelManagementService {
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        // Check if room exists
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setPricePerNight(roomPricePerNight);
                return;
            }
        }
        // Room doesn't exist, create new one
        rooms.add(new Room(roomNumber, roomType, roomPricePerNight));
    }

    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        // First validate dates (initially forgot this check)
        if (checkIn.after(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        User user = null;
        Room room = null;

        // Find user
        for (User u : users) {
            if (u.getUserId() == userId) {
                user = u;
                break;
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Find room
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                room = r;
                break;
            }
        }

        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }

        // Check if room is available
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber() == roomNumber &&
                    !(checkOut.before(booking.getCheckIn()) || checkIn.after(booking.getCheckOut()))) {
                throw new IllegalStateException("Room is already booked for the selected dates");
            }
        }

        // Calculate total cost
        Booking newBooking = new Booking(user, room, checkIn, checkOut);
        int totalCost = newBooking.getTotalPrice();

        if (user.getBalance() < totalCost) {
            throw new IllegalStateException("Insufficient balance");
        }

        // Deduct from balance and create booking
        user.setBalance(user.getBalance() - totalCost);
        bookings.add(newBooking);
    }

    public void setUser(int userId, int balance) {
        // Check if user exists
        for (User user : users) {
            if (user.getUserId() == userId) {
                user.setBalance(balance);
                return;
            }
        }
        // User doesn't exist, create new one
        users.add(new User(userId, balance));
    }

    public void printAll() {
        System.out.println("=== All Rooms ===");
        for (int i = rooms.size() - 1; i >= 0; i--) {
            System.out.println(rooms.get(i));
        }

        System.out.println("\n=== All Bookings ===");
        for (int i = bookings.size() - 1; i >= 0; i--) {
            System.out.println(bookings.get(i));
        }
    }

    public void printAllUsers() {
        System.out.println("=== All Users ===");
        for (int i = users.size() - 1; i >= 0; i--) {
            System.out.println(users.get(i));
        }
    }
}