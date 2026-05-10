package bestroute;

import businesslogic.TimeCalculator;

public class Edge implements TimeCalculator {
    private Node from;
    private Node to;
    private Train train;
    private double distance;
    private String departureTime;
    private String arrivalTime;

    public Edge(Node fromNode, Node toNode, Train train, String dep, String arr, double dist) {
        this.to = toNode;
        this.from = fromNode;
        this.train = train;
        this.distance = dist;
        this.departureTime = dep;
        this.arrivalTime = arr;
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
