package businesslogic;

import bestroute.*;

import javax.swing.*;
import java.util.*;

public class Booking {
    private String from;
    private String to;
    private String departureTime;
    private int numberOfTickets;
    private String email;
    RoutingService routingService = new RoutingService();

    public void confirmBooking(List<Edge> path, int numberOfTickets) {
        for(Edge edge: path) {
            edge.getTrain().addBooking(this);
        }
    }

    public void submitBooking(String from, String to, String departureTime, int numberOfTickets, String email) {
        List<Edge> path = routingService.findPath(from, to, departureTime, numberOfTickets);
        if(!path.isEmpty()) {
            confirmBooking(path, numberOfTickets);
            String message = "Booking successfully submitted for " + numberOfTickets + " tickets, from " + from + " to " + to + "!\n For more information check the confirmation sent to " + email + "!";
            JOptionPane.showMessageDialog(null, message, "Booking confirmation", JOptionPane.INFORMATION_MESSAGE);
        }else {
            String errorMessage = "No possible link found between " + from + " and " + to + "\nfor " + numberOfTickets + " tickets.";
            JOptionPane.showMessageDialog(null, errorMessage, "Route Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
