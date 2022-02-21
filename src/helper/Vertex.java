package helper;

/**
 * The class represent a vertex of in a grid.
 *
 */
public class Vertex {
  int index;
  int rowNumber;
  int colNumber;
  int east;
  int west;
  int south;
  int north;

  /**
   * Constructor of vertex instance.
   *
   * @param index the order of the vertex within the grid.
   * @param rows number of row on the grid
   * @param cols number of column on the grid
   */
  public Vertex(int index, int rows, int cols) {
    this.index = index;
    this.rowNumber = index / cols;
    this.colNumber = index % cols;
    this.east = 0;
    this.west = 0;
    this.south = 0;
    this.north = 0;
  }

  /**
   * Return the index of location on the grid.
   *
   * @return the number of index
   */
  public int getIndex() {
    return index;
  }

  /**
   * Set the index of a location.
   *
   * @param index the order for the location
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * Return the row number of current location.
   *
   * @return the row number
   */
  public int getRowNumber() {
    return rowNumber;
  }

  /**
   * Set the row number of current location.
   *
   * @param rowNumber the row number
   */
  public void setRowNumber(int rowNumber) {
    this.rowNumber = rowNumber;
  }

  /**
   * Return the column number of current location.
   *
   * @return the column number
   */
  public int getColNumber() {
    return colNumber;
  }

  /**
   * Set the column number of current location.
   *
   * @param colNumber the column number
   */
  public void setColNumber(int colNumber) {
    this.colNumber = colNumber;
  }

  /**
   * Return value for this direction, 1 means available otherwise 0.
   *
   * @return the value of this direction
   */
  public int getEast() {
    return east;
  }

  /**
   * Update the value of that direction for current vertex.
   *
   * @param east the value of that direction
   */
  public void setEast(int east) {
    this.east = east;
  }

  /**
   * Return value for this direction, 1 means available otherwise 0.
   *
   * @return the value of this direction
   */
  public int getWest() {
    return west;
  }

  /**
   * Update the value of that direction for current vertex.
   *
   * @param west the value of that direction
   */
  public void setWest(int west) {
    this.west = west;
  }

  /**
   * Return value for this direction, 1 means available otherwise 0.
   *
   * @return the value of this direction
   */
  public int getSouth() {
    return south;
  }

  /**
   * Update the value of that direction for current vertex.
   *
   * @param south the value of that direction
   */
  public void setSouth(int south) {
    this.south = south;
  }

  /**
   * Return value for this direction, 1 means available otherwise 0.
   *
   * @return the value of this direction
   */
  public int getNorth() {
    return north;
  }

  /**
   * Update the value of that direction for current vertex.
   *
   * @param north the value of that direction
   */
  public void setNorth(int north) {
    this.north = north;
  }

  /**
   * Return a description for the vertex.
   *
   * @return the string contains that info
   */
  @Override
  public String toString() {
    return "Vertex {" + "location: " + index
            + ", row: " + rowNumber + ", col: " + colNumber
            + ", east: " + east + ", west: " + west
            + ", south: " + south + ", north: " + north + '}';
  }
}