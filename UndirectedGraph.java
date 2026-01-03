import java.util.ArrayList;
import java.util.HashMap;

public class UndirectedGraph implements UndirectedGraphADT{

    int numNodes;
    //ArrayList<Node> listOfNodes = new ArrayList<>(); // array list of nodes 
    HashMap<Node, ArrayList<Edge>> adjacencyList = new HashMap<>(); 


    UndirectedGraph(int n){ // constructor
        this.numNodes = n; 
        for(int i = 0; i < n; i++){ // looping through the passed in parameter 
            Node node = new Node(i); // creating a node object to hold the nodes 

            // this node is the key of the hashmap
            adjacencyList.put(node, new ArrayList<Edge>()); // adds the nodes as keys of the hashMap and the future values of the hashmap will be adjacent nodes
        }

    }

    /* adds to the graph an edge connecting nodes u and v. The type for this new edge is as indicated by the last parameters.
    This method throws a GraphException if either node does not exist (this means that the name of node
    u or v is negative or larger than or equal to n), or if there is already an edge connecting the given
    nodes. */
    public void insertEdge(Node u, Node v, int edgeType) throws GraphException {

        if (u.getName() >= numNodes || u.getName() < 0 || v.getName() >= numNodes || v.getName() < 0){
            throw new GraphException("node does not exist, u or v is either negative or larger than or equal to n"); 

        }

        ArrayList<Edge> edgesOfU = adjacencyList.get(u); 
        // for loop through edgesOfU see if any value of equal to passed in v if its = then its already there throw exception, if not add it
        for (Edge eachEdgeOfU : edgesOfU) {
            if(eachEdgeOfU.secondEndpoint().getName() == v.getName()){
                throw new GraphException("u already exists in the list cant add");
            }
        }
        // comes here u is not in the adjacencyList
        Edge newEdge = new Edge(u, v, edgeType); 
        edgesOfU.add(newEdge); 
        adjacencyList.put(u, edgesOfU); 

        
        
        // below is the opposite of what happens above to confirm that the hashtable is bi-directional 
        // if 2 in the key goes to value 3, then key 3 must exist and must go to a value 2

        ArrayList<Edge> edgesOfV = adjacencyList.get(v);
        for (Edge eachEdgeOfV : edgesOfV) {
            if(eachEdgeOfV.secondEndpoint().getName() == u.getName()){
                throw new GraphException("v already exists in the list cant add");
            }
        }
        // comes here v is not in the adjacencyList
        Edge newEdge2 = new Edge(v, u, edgeType);
        edgesOfV.add(newEdge2); 
        adjacencyList.put(v, edgesOfV);


    }



    // returns the node with the specified name. If no node with this name exists, the method must throw a GraphException. 

    public Node getNode(int name) throws GraphException {
        
        for (Node n : adjacencyList.keySet()) { // loop through the keys of the list 
            if(name == n.getName()){
                return n; // returns 
            }     
        }
        throw new GraphException("No node with that name exists"); // will come here after looping through the keys
    }

    // returns a list storing all the edges incident on node u. It returns null if u does not have any edges incident on it. 
    // If node u does not exist the method must throw a GraphException. 

    public ArrayList<Edge> incidentEdges(Node u) throws GraphException {

        if (!adjacencyList.containsKey(u)){ // if the list does not contain u 
            throw new GraphException("Node u does not exist"); 
        }

        // need to check through the values of Node u and store the values in storingEdges

        ArrayList<Edge> storingEdges = adjacencyList.get(u); 
        
        if (storingEdges == null || storingEdges.isEmpty()){
            return null; 
        }
        
        return storingEdges; // comes here if all above conditions are not met returns the list of edges
        
    }

    // returns the edge connecting nodes u and v. This method throws a GraphExceptionif there is no edge between u and v, 
    // or if u or v do not exist. 
    public Edge getEdge(Node u, Node v) throws GraphException {

        if(u == null || v == null){ 
            throw new GraphException("Exception in getEdge method. Node u or Node v is null"); 
        }

        // comes here with proper non null node
        ArrayList<Edge> edgesOfU = this.incidentEdges(u); // gives us edges of U
        if(edgesOfU == null){
            throw new GraphException("Edges of node U are empty null"); // protects us below from looping through null 
        }

        for (Edge eachEdge : edgesOfU){ // eachEdge is each elements of the array list edgesOfU
            if(eachEdge.secondEndpoint.getName() == v.getName()){
                return eachEdge;
            }
        }

        throw new GraphException("There is no edge between u and v"); // comes here if no edge exists 

    }


    // returns true if and only if nodes u and v are adjacent. This method throws a GraphExceptionif either node does not exist.
    public boolean areAdjacent(Node u, Node v) throws GraphException {



        if(u == null || v == null){ 
            throw new GraphException("Exception in areAdjacent method. Node u or Node v is null"); 
        }

        // want to make sure passed node exist otherwise throw exception
        if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)){ // if the list does not contain u 
            throw new GraphException("Node u or v does not exist"); 
        }

        // comes here with proper non null node
        ArrayList<Edge> edgesOfU = this.incidentEdges(u); // gives us edges of U
        if(edgesOfU == null){
            throw new GraphException("Edges of node U are empty null"); // protects us below from looping through null 
        }

        for (Edge eachEdge : edgesOfU){ // eachEdge is each elements of the array list edgesOfU
            if(eachEdge.secondEndpoint.getName() == v.getName()){
                return true;
            }
        }

        // throw new GraphException("There is no edge between u and v"); // comes here if no edge exists 
        return false;
        
    }


    public static void main(String[] args) {
    try {
        UndirectedGraph g = new UndirectedGraph(3);

        Node a0 = g.getNode(0);
        Node b1 = g.getNode(1);
        Node c2 = g.getNode(2); 

        g.insertEdge(a0, b1, 1);
        g.insertEdge(b1, c2, 2);

        ArrayList<Edge> edgesOfNode = g.incidentEdges(b1);
        Edge edge = g.getEdge(b1, c2); 
        boolean adj = g.areAdjacent(b1, c2);
        boolean adj2 = g.areAdjacent(a0, c2);
        boolean adj3 = g.areAdjacent(b1, null);
        boolean adj4 = g.areAdjacent(b1, new Node(4));


        


        System.out.println("Adjacency of 0:");
        for (Edge e : g.adjacencyList.get(a0)) {
            System.out.println(" -> " + e.secondEndpoint().getName() + 
                               " (type " + e.getType() + ")");
        }

        System.out.println("Adjacency of 1:");
        for (Edge e : g.adjacencyList.get(b1)) {
            System.out.println(" -> " + e.secondEndpoint().getName() + 
                               " (type " + e.getType() + ")");
        }

        System.out.println("Adjacency of 2:");
        for (Edge e : g.adjacencyList.get(c2)) {
            System.out.println(" -> " + e.secondEndpoint().getName() +
                               " (type " + e.getType() + ")");
        }

        g.insertEdge(a0, b1, 1);

    } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
    }
}
    
}
