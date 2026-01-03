public class Edge {

    private Node firstEndpoint;
    private Node secondEndpoint;
    private int type;

    public Edge(Node u, Node v, int edgeType) {
        this.firstEndpoint = u;
        this.secondEndpoint = v;
        this.type = edgeType;
    }

    public Node firstEndpoint() {
        return this.firstEndpoint;
    }

    public Node secondEndpoint() {
        return this.secondEndpoint;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int newType) {
        this.type = newType;
    }

}