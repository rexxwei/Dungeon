package dungeon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The interface of Dungeon.
 *
 */
public interface Dungeon {

  /**
   * Return the number of total vertices within the dungeon.
   *
   * @return number of total vertices
   */
  int getNumberOfVertices();

  /**
   * Return the size of the tree (i.e., the number of elements in this tree).
   *
   * @return The number of elements in this tree
   */
  String getSize();

  /**
   * Refresh the dungeon for a new game.
   */
  void refreshDungeon();

  /**
   * Return the height of the tree.
   *
   * @return the height of the tree
   */
  int numberOfRows();

  /**
   * Return the height of the tree.
   *
   * @return the height of the tree
   */
  int numberOfCols();

  /**
   * Return the height of the tree.
   *
   * @return the height of the tree
   */
  public HashSet<String> getEdges();

  /**
   * Display the info about a location.
   *
   * @return the array list
   */
  public ArrayList<Location> listAllLocation();

  /**
   * add more gems into the dungeon.
   *
   * @param number of gems
   */
  void addMoreGems(int number);

  /**
   * Display the info about a location.
   *
   */
  public void searchGems();

  /**
   * Return the info about a location.
   *
   */
  Location getTheSpot(int id);

  /**
   * Return the Interconnectivity.
   *
   * @return the number of Interconnectivity
   */
  int getInterConnectivity();

  /**
   * Return the treasure percentage of dungeon.
   *
   * @return a float value
   */
  float getPercentage();

  /**
   * Set percentage of the dungeon.
   *
   * @param newPercentage the new number
   */
  void setPercentage(float newPercentage);

  /**
   * Get the quantity of Caves.
   *
   * @return the value in float
   */
  float getCaveQty();

  /**
   * Get the quantity of tunnels.
   *
   * @return the value in float
   */
  float getTunnelQty();

  /**
   * Print the shape of dungeon in console.
   *
   */
  void print();

  /**
   * check whether the dungeon is wrapping or not.
   *
   * @return true if it's wrapping
   */
  boolean getWrapping();

  /**
   * Reset the value of wrapping.
   * @param newWrapping new value
   */
  void setWrapping(boolean newWrapping);

  /**
   *  add more monsters into dungeon.
   */
  void addMonster();

  /**
   * Update the smell of nearby location.
   *
   * @param location current location
   * @param distance specified distance
   */
  void updateNearbySmell(int location, int distance);

  /**
   * Search all spots with distance two.
   * @param location current location
   * @param distance specified distance
   */
  void searchDistanceTwo(int location, int distance);

  /**
   *  check the id of a coordinate.
   * @param row number of row
   * @param col number of column
   * @return the number of id
   */
  int inquireIndex(int row, int col);

  /**
   *  Return the row and column number of a position from its id.
   * @param index the id
   * @return the row and column number
   */
  int[] inquireCoor(int index);

  /**
   *  Return the row number of a position from its id.
   * @param index the id
   * @return the row number
   */
  int inquireCoorRow(int index);

  /**
   *  Return the column number of a position from its id.
   * @param index the id
   * @return the column number
   */
  int inquireCoorCol(int index);

  /**
   * get all the available exits of a location.
   *
   * @param location the location to check
   * @return a set contain the value
   */
  Set<String> findAllExit(int location);


  /**
   * get the difficulty of dungeon.
   *
   * @return the float value
   */
  float getDifficulty();

  void killMonsterAt(Location l);
}
