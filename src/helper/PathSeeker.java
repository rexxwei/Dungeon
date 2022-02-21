package helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * https://www.geeksforgeeks.org/print-all-shortest-paths-between-given-source-
 * and-destination-in-an-undirected-graph/
 *
 */
public class PathSeeker {

  /**
   * Function to form edge between two vertices src and dest.
   *
   * @param adj adjacent list info.
   * @param src the source vertex
   * @param dest the destination vertex.
   */
  static void addEdge(ArrayList<ArrayList<Integer>> adj, int src, int dest) {
    adj.get(src).add(dest);
    adj.get(dest).add(src);
  }


  /**
   * Function which finds all the paths and stores it in paths array.
   *
   * @param paths The list contains paths records.
   * @param path the list contain path info.
   * @param parent list contain info.
   * @param n source vertex
   * @param u destination vertex
   */
  static void findPaths(ArrayList<ArrayList<Integer>> paths, ArrayList<Integer> path,
                        ArrayList<ArrayList<Integer>> parent, int n, int u) {
    // Base Case
    if (u == -1) {
      paths.add(new ArrayList<>(path));
      return;
    }

    // Loop for all the parents of the given vertex
    for (int par : parent.get(u)) {

      // Insert the current vertex in path
      path.add(u);

      // Recursive call for its parent
      findPaths(paths, path, parent, n, par);

      // Remove the current vertex
      path.remove(path.size() - 1);
    }
  }

  /**
   * Function which performs bfs from the given source vertex.
   *
   * @param adj the adjacent list.
   * @param parent the list contains edge info.
   * @param n the number
   * @param start number of start location
   */
  static void bfs(ArrayList<ArrayList<Integer>> adj, ArrayList<ArrayList<Integer>> parent,
                  int n, int start) {
    // dist will contain the shortest distance from start to every other vertex
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    Queue<Integer> q = new LinkedList<>();

    // Insert source vertex in queue and make
    // its parent -1 and distance 0
    q.offer(start);
    parent.get(start).clear();
    parent.get(start).add(-1);
    dist[start] = 0;

    // Until Queue is empty
    while (!q.isEmpty()) {
      int u = q.poll();

      for (int v : adj.get(u)) {
        if (dist[v] > dist[u] + 1) {
          // A shorter distance is found So erase all the previous parents
          // and insert new parent u in parent[v]
          dist[v] = dist[u] + 1;
          q.offer(v);
          parent.get(v).clear();
          parent.get(v).add(u);
        }
        else if (dist[v] == dist[u] + 1) {
          // Another candidate parent for shortest path found
          parent.get(v).add(u);
        }
      }
    }
  } //end of BFS


  /**
   * Function which calculate all the paths from start to end.
   *
   * @param adj adjacent list
   * @param n number of vertices
   * @param start start location
   * @param end end location
   *
   * @return List contains all the path between two locations
   */
  static ArrayList<ArrayList<Integer>> pathCalculator(ArrayList<ArrayList<Integer>> adj,
                                                      int n, int start, int end) {
    ArrayList<ArrayList<Integer>> allPaths = new ArrayList<>();
    ArrayList<Integer> path = new ArrayList<>();
    ArrayList<ArrayList<Integer>> parent = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      parent.add(new ArrayList<>());
    }

    // Function call to bfs
    bfs(adj, parent, n, start);

    // Function call to find_paths
    findPaths(allPaths, path, parent, n, end);

    for (ArrayList<Integer> v : allPaths) {
      // Since paths contain each path in reverse order,
      // so reverse it
      Collections.reverse(v);
    }

    return allPaths;

  } //end of print_paths()

  /**
   * Accept input from caller to calculate paths between to spots.
   *
   * @param v number of vertices
   * @param mstSet the set store edge info
   * @param src start point on the grid (index from 0)
   * @param des destination on the grid
   * @return 2D list contains all possible paths
   */
  public static ArrayList<ArrayList<Integer>> pathSeeker(int v,
                             Set<String> mstSet, int src, int des) {

    // array of vectors is used to store the graph
    // in the form of an adjacency list
    ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
    for (int i = 0; i < v; i++) {
      adjList.add(new ArrayList<>());
    }

    // given graph edge info
    int i = 1;
    for (String edge : mstSet) {
      String[] parts = edge.split(",");
      addEdge(adjList, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
      i++;
    }

    // Function Call
    ArrayList<ArrayList<Integer>> routesFound;
    routesFound = pathCalculator(adjList, v, src, des);
    // display how many routes found
    System.out.println("found (" + routesFound.size() + ") routes");

    return routesFound;
  }

}
