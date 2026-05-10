package bestroute;

import java.util.*;

public class TrainRoutes {
    private static TrainRoutes instance;
    private Map<String, Node> stations;
    private Map<Integer, Train> allTrains = new HashMap<>();

    private TrainRoutes() {
        stations = new HashMap<>();
    }

    public void addOrUpdateTrain(Train train) {
        allTrains.put(train.getId(), train);
    }

    public void removeTrain(int id) {
        allTrains.remove(id);
    }

    public Collection<Train> getAllTrains() {
        return allTrains.values();
    }

    public Train getTrainById(int id) {
        return allTrains.get(id);
    }

    public static TrainRoutes getInstance() {
        if (instance == null) {
            instance = new TrainRoutes();
        }
        return instance;
    }

    public void addStation(String stationName) {
        stations.putIfAbsent(stationName, new Node(stationName));
    }

    public void createConnection(String from, String to, Train train, String dep, String arr, double dist) {
        Node fromNode = stations.get(from);
        Node toNode = stations.get(to);

        if (fromNode != null && toNode != null) {
            Train existingTrain = allTrains.get(train.getId());
            Edge edge = new Edge(fromNode, toNode, existingTrain, dep, arr, dist);
            fromNode.getDepartingTrains().add(edge);
        }
    }

    public Node getStation(String stationName) {
        return stations.get(stationName);
    }

    public Collection<Node> getStations() {
        return stations.values();
    }

    public void removeConnection(Edge edge) {
        Node from = edge.getFrom();
        if(from != null) {
            from.getDepartingTrains().remove(edge);
        }
    }
}
