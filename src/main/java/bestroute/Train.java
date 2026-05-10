package bestroute;

import businesslogic.Booking;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Train {
    private int id;
    private int nrSeats;
    private int bookedSeats;
    private int delayMinutes = 0;
    private List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        if(this.bookedSeats + booking.getNumberOfTickets() <= this.nrSeats) {
            this.bookings.add(booking);
            this.bookedSeats += booking.getNumberOfTickets();
        }
    }

    public void delayNotification() {
        if(bookings.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Notification sent to: ");
        for(Booking booking: bookings) {
            sb.append(booking.getEmail()).append("\n");
        }

        JOptionPane.showMessageDialog(null, "Train " +id+ "is delayed by " + delayMinutes + " minutes!\n" + sb.toString(), "Admin Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
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
