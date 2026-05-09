package bestroute;

import java.util.*;

public class TrainRoutes {
    private static TrainRoutes instance;
    private Map<String, Node> stations;

    private TrainRoutes() {
        stations = new HashMap<>();
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
            Edge edge = new Edge(fromNode, toNode, train, dep, arr, dist);
            fromNode.getDepartingTrains().add(edge);
        }
    }

    public Node getStation(String stationName) {
        return stations.get(stationName);
    }
}
