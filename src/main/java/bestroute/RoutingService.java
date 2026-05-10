package bestroute;

import java.util.*;

public class RoutingService implements TimeCalculator {
    TrainRoutes routes;

    public RoutingService() {
        routes = TrainRoutes.getInstance();
    }

    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        for(Node station : routes.getStations()) {
            edges.addAll(station.getDepartingTrains());
        }

        return edges;
    }

    public List<Edge> pathReconstruction(Map<Node, Edge> pathEdges, Node from, Node to) {
        LinkedList<Edge> path = new LinkedList<>();
        Node current = to;
        while(current!=null && !current.equals(from)) {
            Edge edge = pathEdges.get(current);
            path.addFirst(edge);
            current = edge.getFrom();
        }

        return path;
    }

    public List<Edge> findPath(String from, String to, String departure, int requestedNrTickets) {
        Node fromNode = routes.getStation(from);
        Node toNode = routes.getStation(to);
        int time = timeAsInt(departure);

        Map<Node, Integer> minArrivalTimes = new HashMap<>();
        Map<Node, Edge> pathEdges = new HashMap<>();
        PriorityQueue<NodeState> queue = new PriorityQueue<>();
        queue.add(new NodeState(fromNode, time));
        minArrivalTimes.put(fromNode, time);

        while (!queue.isEmpty()) {
            NodeState curr = queue.poll();
            Node node = curr.getNode();
            if(node.equals(toNode)){
                break;
            }

            if(curr.getTime() > minArrivalTimes.getOrDefault(node, Integer.MAX_VALUE)) {
                continue;
            }

            for(Edge edge : node.getDepartingTrains()) {
                int delay = edge.getTrain().getDelayMinutes();
                int actualDepartureTime = timeAsInt(edge.getDepartureTime())  +  delay;
                int actualArrivalTime = timeAsInt(edge.getArrivalTime())  +  delay;

                if(actualDepartureTime >= curr.getTime()) {
                    if(edge.getTrain().getBookedSeats() + requestedNrTickets <= edge.getTrain().getNrSeats()) {
                        if(actualArrivalTime < minArrivalTimes.getOrDefault(edge.getTo(), Integer.MAX_VALUE)) {
                            minArrivalTimes.put(edge.getTo(), actualArrivalTime);
                            pathEdges.put(edge.getTo(), edge);
                            queue.add(new NodeState(edge.getTo(), actualArrivalTime));
                        }
                    }
                }
            }

        }

        if(!pathEdges.containsKey(toNode)) {
            return new ArrayList<>();
        }

        return pathReconstruction(pathEdges, fromNode, toNode);
    }

}
