package bestroute;

import java.util.*;

public class Node {
    private String name;
    private List<Edge> departingTrains = new ArrayList<>();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getDepartingTrains() {
        return departingTrains;
    }

    public void setDepartingTrains(List<Edge> departingTrains) {
        this.departingTrains = departingTrains;
    }
}
