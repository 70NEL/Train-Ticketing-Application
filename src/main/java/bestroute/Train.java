package bestroute;

import businesslogic.Booking;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private int id;
    private int nrSeats;
    private int bookedSeats;
    List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        if(this.bookedSeats + booking.getNumberOfTickets() <= this.nrSeats) {
            this.bookings.add(booking);
            this.bookedSeats += booking.getNumberOfTickets();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrSeats() {
        return nrSeats;
    }

    public void setNrSeats(int nrSeats) {
        this.nrSeats = nrSeats;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public boolean hasFreeSeats() {
        return bookedSeats < nrSeats;
    }
}
