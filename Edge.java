public class Edge {

    // The two nodes connected by the edge
    Node firstEndpoint;
    Node secondEndpoint;
    
    int type; 
    /* The type of an edge can be
    – 1: corridor
    – 2: brick wall
    – 3: rock wall
    – 4: metal wall */


    /* Creates an edge of the given type connecting nodes u and v. 
    For example let edge (u,v)represent a corridor of the labyrinth. The first endpoint of this edge
    is node u and the second endpoint is node v; the type of the edge is 1. */
    
    Edge(Node u, Node v, int edgeType){ // constructor
        this.firstEndpoint = u;
        this.secondEndpoint = v;
        this.type = edgeType; 
    }


    public Node firstEndpoint(){
        return this.firstEndpoint;
    }

    public Node secondEndpoint(){
        return this.secondEndpoint;
    }

    public int getType(){
        return this.type;
    }

    public void setType(int newType){
        this.type = newType; 
    }

    // returns true if this Edge object connects the same two nodes as otherEdge; returns false otherwise 
    public boolean equals(Edge otherEdge){
        if (this.firstEndpoint.name == otherEdge.firstEndpoint.getName() && this.secondEndpoint.name == otherEdge.secondEndpoint.getName()){
            return true; 
        }

        return false; // returns false if othewise

    }
    
}
