import java.util.ArrayList;
import java.util.HashMap;

public class UndirectedGraph implements UndirectedGraphADT {

    int numNodes; // number of nodes in the graph

    Node[] nodes; // array storing all node objects (stable references guaranteed)

    // adjacency list, but keyed by node names (integers) instead of node objects
    HashMap<Integer, ArrayList<Edge>> adj;  
    

    public UndirectedGraph(int n) {
        // store number of nodes
        numNodes = n;

        // create array of node references
        nodes = new Node[n];

        // create adjacency list
        adj = new HashMap<>();

        // initialize graph with n nodes and empty edge lists
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i); // create node with index i
            adj.put(i, new ArrayList<Edge>()); // create empty edge list
        }
    }

    public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
        int uname = u.getName();
        int vname = v.getName();

        // check if the nodes are valid 
        if (uname < 0 || uname >= numNodes || vname < 0 || vname >= numNodes) {
            throw new GraphException("Node does not exist");
        }

        // check if edge already exists by scanning adjacency list of node u
        // prevents duplicate edges from being created
        for (Edge e : adj.get(uname)) {
            // find the opposite endpoint of the edge
            Node end = (e.firstEndpoint().getName() == uname) 
                        ? e.secondEndpoint() 
                        : e.firstEndpoint();

            // if it matches vname, the edge is already there
            if (end.getName() == vname) {
                throw new GraphException("Edge already exists");
            }
        }

        // create one single edge object
        // important because undirected graphs must use the same object from both sides
        Edge e = new Edge(nodes[uname], nodes[vname], edgeType);

        // add the edge object to both adjacency lists to maintain undirected structure
        adj.get(uname).add(e);
        adj.get(vname).add(e);
    }

    public Node getNode(int name) throws GraphException {
        // check if node exists
        if (name < 0 || name >= numNodes) {
            throw new GraphException("Node does not exist");
        }
        return nodes[name];
    }

    public ArrayList<Edge> incidentEdges(Node u) throws GraphException {
        int a = u.getName();

        // ensure node exists
        if (a < 0 || a >= numNodes) throw new GraphException("Node does not exist");

        ArrayList<Edge> list = adj.get(a);

        // if no edges, return null as required by specification
        if (list.isEmpty()) return null;

        // sort edges so dfs explores nodes in deterministic order
        // this is needed because a hashmap does not store edges in any fixed order
        list.sort((e1, e2) ->
            getOtherEndpoint(e1, a).getName() - getOtherEndpoint(e2, a).getName()
        );

        return list;
    }

    private Node getOtherEndpoint(Edge e, int current) {
        // given an edge and one endpoint, return the opposite endpoint
        // this is useful because adjacency lists store only one copy of the edge
        return (e.firstEndpoint().getName() == current)
                ? e.secondEndpoint()
                : e.firstEndpoint();
    }

    public Edge getEdge(Node u, Node v) throws GraphException {
        int a = u.getName();
        int b = v.getName();

        // ensure nodes exist
        if (a < 0 || a >= numNodes || b < 0 || b >= numNodes)
            throw new GraphException("Node does not exist");

        // search adjacency list for a potential matching endpoint
        for (Edge e : adj.get(a)) {
            Node end = getOtherEndpoint(e, a);
            if (end.getName() == b) return e;
        }

        //comes here if no matching edge found
        throw new GraphException("No such edge");
    }

    public boolean areAdjacent(Node u, Node v) throws GraphException {
        int a = u.getName();
        int b = v.getName();

        // validate nodes
        if (a < 0 || a >= numNodes || b < 0 || b >= numNodes)
            throw new GraphException("Node does not exist");

        // scan the adjacency list
        for (Edge e : adj.get(a)) {
            Node end = getOtherEndpoint(e, a);
            if (end.getName() == b) return true; // adjacency found
        }

        return false; // comes here, not adjacent
    }
}