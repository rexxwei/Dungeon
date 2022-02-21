package dungeon;

import java.util.HashSet;

/**
 * The interface indication all methods for a Player class.
 */
public interface Player {


  /**
   * Update the player as a new one.
   *
   */
  void refreshPlayer();

  /**
   * Set a location for player.
   *
   * @param position the index of spot on the grid
   */
  void setPosition(int position);

  /**
   * Function to add a location that has been visited.
   *
   * @param location the index of a location
   */
  void setHistory(int location);

  /**
   * Function to check whether a location has been visited or not.
   *
   * @param location index of location
   * @return true if has been visited, false otherwise
   */
  boolean checkHistory(int location);

  /**
   * Return the number(index) of location.
   *
   * @return int index
   */
  int getLocation();

  /**
   * Return the history of player.
   */
  HashSet<Integer> getHistory();

  /**
   * Set up the coordinate for player.
   * @param row row number
   * @param col column number
   */
  void setPlayerCoor(int row, int col);

  /**
   * Display the status of player.
   */
  void pickGem(Location id);

  /**
   * Display the status of player.
   */
  void pickArrow(Location id);


  /**
   * Return the row index number of current location.
   *
   * @return row index of the location
   */
  int inquireRowIndex();

  /**
   * Return the col index number of current location.
   *
   * @return col index of the location
   */
  int inquireColIndex();

  /**
   * Return the current quantity of arrow.
   *
   * @return number of arrows.
   */
  int getArrowQty();

  /**
   * Update the arrow quantity.
   *
   * @param i number to add/subtract
   * @return updated quantity
   */
  int setArrowQty(int i);

  /**
   * Get the total quantity of treasure.
   *
   * @return the number
   */
  int getGemQty();

  void deductTreasure();
}
