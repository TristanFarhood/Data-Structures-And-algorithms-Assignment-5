public class Node {

    private int name;
    private boolean marked;

    public Node(int nodeName) {
        this.name = nodeName;
        this.marked = false;
    }

    public void setMark(boolean mark) {
        this.marked = mark;
    }

    public boolean getMark() {
        return this.marked;
    }

    public int getName() {
        return this.name;
    }

    

    public boolean equals(Node other) {
        if (other == null) return false;
        return this.name == other.name;
    }

    
}