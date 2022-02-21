package helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to build a grid as the backbone for a dungeon.
 */
public class Grid {

  /**
   * The constructor to build the grid as input info.
   *
   * @param rows              number of rows
   * @param cols              number of columns
   * @param interConnectivity the interconnectivity of the grid.
   * @param wrapping          indicate whether wrapping or not.
   * @return A set that contains the edges for the dungeon required.
   */
  public static HashSet<String> grid(int rows, int cols, int interConnectivity, boolean wrapping) {

    // ====== get the grid for dungeon ======
    ArrayList<Edge> graphEdges = new ArrayList<Edge>();    //edge list, not adjacency list
    //Edges created in almost sorted order, only the last 2 are switched but this is unnecessary
    // as edges are sorted in the algorithm

    // building the set to store edges
    Set<String> edgeSet = new HashSet<String>();

    //System.out.println(String.format("In %d by %d Matrix.", rows, cols));
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        int num = cols * r + c;
        int north = num - cols;
        int south = num + cols;
        int east = num + 1;
        int west = num - 1;

        // add the north edge
        if (r - 1 >= 0) {
          // check edge exist or not
          if (edgeSet.contains(String.format("%d,%d", north, num)) 
                  || edgeSet.contains(String.format("%d,%d", num, north))) {
            //System.out.println("Edge already exist!");
          } else {
            edgeSet.add(String.format("%d,%d", num, north));
            graphEdges.add(new Edge(num, north, 1));
          }
        }
        // add the west edge
        if (c - 1 >= 0) {
          // check edge exist or not
          if (edgeSet.contains(String.format("%d,%d", west, num)) 
                  || edgeSet.contains(String.format("%d,%d", num, west))) {
            //System.out.println("Edge already exist!");
          } else {
            edgeSet.add(String.format("%d,%d", num, west));
            graphEdges.add(new Edge(num, west, 1));
          }
        }
        // add the south edge
        if (r + 1 < rows) {
          // check edge exist or not
          if (edgeSet.contains(String.format("%d,%d", south, num)) 
                  || edgeSet.contains(String.format("%d,%d", num, south))) {
            //System.out.println("Edge already exist!");
          } else {
            edgeSet.add(String.format("%d,%d", num, south));
            graphEdges.add(new Edge(num, south, 1));
          }
        }
        // add the east edge
        if (c + 1 < cols) {
          // check edge exist or not
          if (edgeSet.contains(String.format("%d,%d", east, num)) 
                  || edgeSet.contains(String.format("%d,%d", num, east))) {
            //System.out.println("Edge already exist!");
          } else {
            edgeSet.add(String.format("%d,%d", num, east));
            graphEdges.add(new Edge(num, east, 1));
          }
        }

      }
    } // end of generate grid

    Set<String> leftOver = new HashSet<>(Set.copyOf(edgeSet));
    // ------------- all the edge for a complete grid --------------

    //how many nodes. NODE COUNT MUST BE ENTERED MANUALLY.
    //No error handling between nodeCount and graphEdges
    int nodeCount = rows * cols;

    //CAREFUL: nodeCount must be correct. No error checking between nodeCount
    // & graphEdges to see how many nodes actually exist
    Kruskal graph = new Kruskal();

    // >>>>>>>>> get the edges for MST graph <<<<<<<<<<<<
    Set<String> edgeMstSet = new HashSet<>();
    edgeMstSet = graph.kruskalMst(graphEdges, nodeCount);


    // ------get the edge not in MST--------
    leftOver.removeAll(edgeMstSet);


    // --------- increase interconnectivity ----------
    //convert Set to Array
    String[] leftCopy = leftOver.toArray(new String[leftOver.size()]);

    int counter = interConnectivity - 1;
    for (int j = 1; j <= counter; j++) {
      edgeMstSet.add(leftCopy[j]);
    }


    //++++++++++ design wrapping dungeon +++++++++++++
    if (wrapping) {
      // find out vertices on edge
      // building the set to store edges
      Set<String> wrappingEdgeSet = new HashSet<String>();
      // add horizontal edges
      for (int p = 0; p < rows; p++) {
        wrappingEdgeSet.add(String.format("%d,%d", p * cols, (p + 1) * cols - 1));
      }

      // add vertical edges
      for (int o = 0; o < cols; o++) {
        wrappingEdgeSet.add(String.format("%d,%d", o, (rows - 1) * cols + o));
      }


      // add wrapping edges into edgeSEt

      for (String edge : wrappingEdgeSet) {
        String[] parts = edge.split(",");
        int v1 = Integer.parseInt(parts[0]);
        int v2 = Integer.parseInt(parts[1]);

        if (edgeMstSet.contains(String.format("%d,%d", v1, v2))
                || edgeMstSet.contains(String.format("%d,%d", v2, v1))) {
          System.out.println("Edge already exist!");
        } else {
          edgeMstSet.add(String.format("%d,%d", v1, v2));
          //graphEdges.add(new Edge(v1, v2, 1));
        }
      }
    }

    return (HashSet<String>) edgeMstSet;

  } //end of main()

} //end of Driver
