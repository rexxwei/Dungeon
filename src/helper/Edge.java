package helper;

/**
 * Class of Edge for a graph.
 *
 */
public class Edge implements Comparable<Edge> {
  private int vertex1;  //an edge has 2 vertices & a weight
  private int vertex2;
  private int weight;

  /**
   *  Default constructor of edge object.
   *
   * @param vertex1 value of one vertex
   * @param vertex2 value of the other vertex
   * @param weight the weight of the edge
   */
  public Edge(int vertex1, int vertex2, int weight) {
    this.vertex1 = vertex1;
    this.vertex2 = vertex2;
    this.weight = weight;
  }

  /**
   * Return the vertex 1.
   *
   * @return vertex 1
   */
  public int getVertex1() {
    return vertex1;
  }

  /**
   * Return the vertex 2.
   *
   * @return vertex 2
   */
  public int getVertex2() {
    return vertex2;
  }

  /**
   * Return weight of edge.
   *
   * @return the weight
   */
  public int getWeight() {
    return weight;
  }


  /**
   * Return the info of edge, number of two vertices.
   *
   * @return number of two vertices as string in the format "01,02"
   */
  @Override
  public String toString() {
    return "" + getVertex1() + "," + getVertex2() + "";
  }

  /**
   * Compares this object with the specified object for order.  Returns a
   * negative integer, zero, or a positive integer as this object is less
   * than, equal to, or greater than the specified object.
   *
   */
  @Override
  public int compareTo(Edge o) {
    return 0;
  }

}