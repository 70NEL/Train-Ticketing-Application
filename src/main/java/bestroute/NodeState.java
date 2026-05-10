package bestroute;

public class NodeState implements Comparable<NodeState>{
    private Node node;
    private int time;

    public NodeState(Node node, int time){
        this.node = node;
        this.time = time;
    }

    public Node getNode() {
        return node;
    }
    public void setNode(Node node) {
        this.node = node;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int compareTo(NodeState other){
        return Integer.compare(this.time, other.time);
    }
}
