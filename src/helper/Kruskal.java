package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Kruskal that help to generate the backbone of dungeon.
 *
 *
 */
public class Kruskal {

  /**
   * main method of the class.
   *
   * @param args default arguments
   */
  public static void main(String[] args) {

    ArrayList<Edge> graphEdges = new ArrayList<Edge>();    //edge list, not adjacency list

    // building the set to store edges
    Set<String> edgeSet = new HashSet<String>();

    int raw = 2;
    int col = 2;
    for (int r = 0; r < raw; r++) {
      for (int c = 0; c < col; c++) {
        int num = col * r + c;
        int north = num - col;
        int south = num + col;
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
        if (r + 1 < raw) {
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
        if (c + 1 < col) {
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
    System.out.println(leftOver.toString());
    System.out.println(String.format("Generate total %d edges.", graphEdges.size()));
    System.out.println((graphEdges.toString()));

    int nodeCount = raw * col;
    Kruskal graph = new Kruskal();

    Set<String> edgeMstSet = new HashSet<String>();
    edgeMstSet = graph.kruskalMst(graphEdges, nodeCount);
    System.out.println(String.format("MST has %d edges.", edgeMstSet.size()));
    System.out.println((edgeMstSet.toString()));

    // get the edge not in MST
    leftOver.removeAll(edgeMstSet);
    System.out.println(String.format("Left over %d edges.", leftOver.size()));
    System.out.println(leftOver.toString());

    // build the vertices for the grid
    ArrayList<Vertex> vertices = new ArrayList<>();
    for (int r = 0; r < raw; r++) {
      for (int c = 0; c < col; c++) {
        int index = col * r + c;
        int north = index - col;
        int south = index + col;
        int east = index + 1;
        int west = index - 1;

        // add a new vertex
        vertices.add(new Vertex(index, raw, col));

        // check north edge
        if (r - 1 >= 0) {
          // check edge exist or not
          if (edgeMstSet.contains(String.format("%d,%d", north, index))
                  || edgeMstSet.contains(String.format("%d,%d", index, north))) {
            vertices.get(index).setNorth(1);
          }
        }
        // check west edge
        if (c - 1 >= 0) {
          // check edge exist or not
          if (edgeMstSet.contains(String.format("%d,%d", west, index))
                  || edgeMstSet.contains(String.format("%d,%d", index, west))) {
            vertices.get(index).setWest(1);
          }
        }
        // check south edge
        if (r + 1 < raw) {
          // check edge exist or not
          if (edgeMstSet.contains(String.format("%d,%d", south, index))
                  || edgeMstSet.contains(String.format("%d,%d", index, south))) {
            vertices.get(index).setSouth(1);
          }
        }
        // add the east edge
        if (c + 1 < col) {
          // check edge exist or not
          if (edgeMstSet.contains(String.format("%d,%d", east, index))
                  || edgeMstSet.contains(String.format("%d,%d", index, east))) {
            vertices.get(index).setEast(1);
          }
        }

      }
    } // end of initiate vertices

    for (int i = 0; i < vertices.size(); i++) {
      System.out.println(vertices.get(i).toString());
    }

  }

  /**
   * The method to generate MST through Kruskal algorithm.
   *
   * @param graphEdges the graph to generate MST
   * @param nodeCount  the number of vertices in the graph.
   * @return the list contains the MST edges
   */
  public Set<String> kruskalMst(ArrayList<Edge> graphEdges, int nodeCount) {
    Set<String> edgeMstSet = new HashSet<String>();
    String outputMessage = "";

    Collections.sort(graphEdges);    //sort edges with smallest weight 1st
    ArrayList<Edge> mstEdges = new ArrayList<Edge>();
    //list of edges included in the Minimum spanning tree (initially empty)

    DisjointSet nodeSet = new DisjointSet(nodeCount);

    for (int i = 0; i < graphEdges.size() && mstEdges.size() < (nodeCount); i++) {
      //loop over all edges. Start @ 1 (ignore 0th as placeholder).
      Edge currentEdge = graphEdges.get(i);
      int root1 = nodeSet.find(currentEdge.getVertex1());    //Find root of 1 vertex of the edge
      int root2 = nodeSet.find(currentEdge.getVertex2());

      String unionMessage = ",\tNo union performed\n";
      //assume no union is to be performed, changed later if a union DOES happen
      if (root1 != root2) {      //if roots are in different sets the DO NOT create a cycle
        mstEdges.add(currentEdge);    //add the edge to the graph
        edgeMstSet.add(String.format("%d,%d", currentEdge.getVertex1(), currentEdge.getVertex2()));
        nodeSet.union(root1, root2);  //union the sets
      }

    }

    outputMessage += "\nMST has (" + mstEdges.size() + ") edges.\n";
    int mstTotalEdgeWeight = 0;
    outputMessage += "edges: ";
    for (Edge edge : mstEdges) {
      outputMessage += edge;    //print each edge
      mstTotalEdgeWeight += edge.getWeight();
    }
    outputMessage += "\nTotal weight of MST = " + mstTotalEdgeWeight;


    // output to file
    try (PrintWriter outputFile = new PrintWriter(new File("MSToutput.txt"));) {
      outputFile.println(outputMessage);
      // System.out.println("\nOpen \"MSToutput.txt\" for backup copy of answers");
    } catch (FileNotFoundException e) {
      System.out.println("Error! Couldn't create file");
    }

    return edgeMstSet;
  } //end of kruskalMST()

} //end of Kruskal()





