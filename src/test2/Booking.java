package test2;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Booking {
    private User user;
    private Room room;
    private Date checkIn;
    private Date checkOut;

    public Booking(User user, Room room, Date checkIn, Date checkOut) {
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getTotalPrice() {
        long diff = checkOut.getTime() - checkIn.getTime();
        int days = (int) (diff / (1000 * 60 * 60 * 24));
        return days * room.getPricePerNight();
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Booking: " + room.toString() + ", User: " + user.toString() +
                ", Check-in: " + sdf.format(checkIn) + ", Check-out: " + sdf.format(checkOut) +
                ", Total: " + getTotalPrice();
    }
}