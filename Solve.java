import java.util.*;
import java.io.*;

public class Solve {

    UndirectedGraph graph; // graph representing the labyrinth
    Node entrance; // entrance node
    Node exit; // exit node

    int width;  // number of rooms per row
    private int length; // number of rooms per column
    private int blast;  // number of blast bombs allowed (k1)
    private int melt;   // number of melt bombs allowed (k2)

    private Stack<Node> path; // stores the current dfs path to allow backtracking

    // constants representing edge types from input file
    final int CORRIDOR = 1;
    final int BRICK = 2;
    final int ROCK = 3;
    final int METAL = 4;

    public Solve(String inputFile) throws LabyrinthException {
        File f = new File(inputFile);
        if (!f.exists()) throw new LabyrinthException("Input file not found."); // if file DNE

        try {
            Scanner sc = new Scanner(f);

            // read the scale factor 
            int S = Integer.parseInt(sc.nextLine().trim());

            // read width, length, and allowed bombs
            width = Integer.parseInt(sc.nextLine().trim());
            length = Integer.parseInt(sc.nextLine().trim());
            blast = Integer.parseInt(sc.nextLine().trim());
            melt = Integer.parseInt(sc.nextLine().trim());

            int total = width * length; // total number of nodes in the graph

            graph = new UndirectedGraph(total); // create the graph

            for (int row = 0; row < length; row++) { // begin reading the labyrinth layout

                String roomLine = sc.nextLine();  // line containing rooms & horizontal walls

                // loop through each room in the current row
                for (int col = 0; col < width; col++) {

                    int idx = row * width + col; // unique index for this room right now
                    Node curr = graph.getNode(idx);

                    char roomChar = roomLine.charAt(2 * col); // characters are separated by walls

                    // detect entrance and exit
                    if (roomChar == 'e') entrance = curr;
                    else if (roomChar == 'x') exit = curr;

                    // add horizontal edge to the right if it exists
                    if (col < width - 1) {
                        char edgeChar = roomLine.charAt(2 * col + 1);
                        int type = toType(edgeChar);
                        if (type != 0) {
                            graph.insertEdge(curr, graph.getNode(idx + 1), type);
                        }
                    }
                }

                // for all rows except the last row, read vertical connections
                if (row < length - 1) {
                    String vLine = sc.nextLine();

                    // vertical edges at even-numbered positions in line
                    for (int col = 0; col < width; col++) {
                        char vChar = vLine.charAt(2 * col);
                        int type = toType(vChar);

                        if (type != 0) {
                            int top = row * width + col;
                            int bottom = top + width;
                            graph.insertEdge(graph.getNode(top), graph.getNode(bottom), type);
                        }
                    }
                }
            }

            sc.close();

        } catch (Exception e) {
            throw new LabyrinthException("Error reading file.");
        }

        // ensure we actually found entrance + exit
        if (entrance == null || exit == null)
            throw new LabyrinthException("Missing entrance or exit.");
    }

    private int toType(char c) {
        // convert the file symbols into edge types
        switch (c) {
            case '-':
            case '|': return CORRIDOR; // normal corridor
            case 'b':
            case 'B': return BRICK;    // brick wall (costs 1 blast)
            case 'r':
            case 'R': return ROCK;     // rock wall (costs 2 blasts)
            case 'm':
            case 'M': return METAL;    // metal wall (costs 1 melt)
            default:  return 0;        // '*' = no connection
        }
    }

    public UndirectedGraph getGraph() {
        return graph;
    }

    public Iterator<Node> findExit() {

        // unmark all nodes before new dfs
        try {
            for (int i = 0; i < graph.numNodes; i++)
                graph.getNode(i).setMark(false);
        } catch (Exception e) {
            return null;
        }

        // path stack that will store the solution
        path = new Stack<>();

        try {
            // start dfs from entrance using available bombs
            if (dfs(entrance, blast, melt))
                return path.iterator();
        } catch (GraphException e) {
            return null;
        }

        return null;
    }

    // dfs helper method 
    private boolean dfs(Node curr, int blastsLeft, int meltsLeft) throws GraphException {

        // mark current node as visited and push onto path
        curr.setMark(true);
        path.push(curr);

        // check if we reached the exit
        if (curr.equals(exit))
            return true;

        // get all incident edges in sorted order from the graph
        ArrayList<Edge> edges = graph.incidentEdges(curr);

        if (edges != null) {
            for (Edge e : edges) {

                // determine the node on the opposite end of this edge
                Node next = (e.firstEndpoint() == curr)
                                ? e.secondEndpoint()
                                : e.firstEndpoint();

                // skip if already visited
                if (next.getMark())
                    continue;

                // determine the number of bombs required for this edge
                int needBlast = 0;
                int needMelt = 0;

                int type = e.getType();
                if (type == BRICK) needBlast = 1;
                else if (type == ROCK) needBlast = 2;
                else if (type == METAL) needMelt = 1;

                // skip this direction if we don't have enough bombs at the mom,ent 
                if (blastsLeft < needBlast || meltsLeft < needMelt)
                    continue;

                // attempt dfs through this neighbor
                if (dfs(next, blastsLeft - needBlast, meltsLeft - needMelt))
                    return true;
            }
        }

        // backtrack, unmark node and remove from path
        curr.setMark(false);
        path.pop();
        return false;
    }
}