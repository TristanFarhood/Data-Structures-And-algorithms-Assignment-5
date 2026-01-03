public class Node {

    int name;
    boolean marked; 

    Node(int nodeName){ // constructor
        this.name = nodeName; 
        this.marked = false; 
    }

    public void setMark(boolean mark){
        this.marked = mark; 
    }

    public int getName() {
        return this.name;
    }

    public boolean getMarked() {
        return this.marked;
    }


    // returns true of this node has the same name as otherNode; returns false otherwise. 
    boolean equals(Node otherNode){

        if (this.name == otherNode.getName()){
            return true;
        }
        return false; // returns false if othewise
    }
    
}
