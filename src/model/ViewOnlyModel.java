package model;

import dungeon.Location;
import dungeon.MonsterStatus;

import java.util.HashSet;

/**
 * This interface display the operations of text game.
 *
 */
public interface ViewOnlyModel {

  /**
   * Check whether it's safe or not when enter a new position.
   *
   * @return true if alive, false if eaten by monster
   */
  boolean checkSafety(); //end of checkSafety()

  /**
   * Check the state of monster.
   *
   * @return one of the 3 state of a monster
   */
  MonsterStatus checkMonsterState();

  /**
   * Update player's location info according to new coordinate.
   *
   * @param row number of row
   * @param col number of column
   */
  void updateByCoor(int row, int col);

  /**
   * Update player's location info according to new coordinate.
   *
   * @param pos the index of location (serial id)
   */
  void updateByIndex(int pos);

  /**
   * Shoot an arrow by specified number of caves and direction.
   *
   * @return the quantity of arrow
   */
  int getArrowQty();

  /**
   * Return the current position(coordinate) of player.
   *
   * @return an array contains the coordinate
   */
  int[] getPlayerPos();

  /**
   * Return the coordinate of a position provide by index(serial number on the grid).
   *
   * @param index (a serial number on the grid)
   * @return an array contains the coordinate
   */
  int[] indexToCoor(int index);

  /**
   * Return the index of a location specified by coordinate.
   *
   * @param row the row number
   * @param col the col number
   * @return index (a serial number on the grid)
   */
  int coorToIndex(int row, int col);

  /**
   * get the discription of a location.
   *
   * @return the info as string
   */
  String displayLocationDetail();


  /**
   * Get all available direction of current location.
   *
   * @return String stand for directions.
   *          for example "ws" means west and south are open
   */
  String getAllExit();

  /**
   * Check whether the player arrive the end or not.
   *
   * @return true if yes, false otherwise
   */
  boolean checkArriveEnd();

  /**
   * Return the entering direction of a tunnel.
   *
   * @param dir entering direction of player
   * @return the direction in the tunnel
   */
  String sourceDir(String dir);

  /**
   * Get the exit direction of a tunnel.
   *
   * @param location the location to inquire
   * @param dir the entered direction
   * @return another available direction
   */
  String exitOfTunnel(int location, String dir);

  /**
   * Return the object of current location.
   *
   * @return current location
   */
  Location currentPosition();

  /**
   * Get all the available directions of current location.
   *
   * @return string stand for initiative of directions,
   *         for example "ws" means west and south are open
   */
  String curAvailableDir();

  /**
   * Check the current location is cave.
   *
   * @return true if it's a cave
   */
  boolean inCaveNow();


  /**
   * Check whether the game is over.
   *
   * @return true if game is on, false otherwise
   */
  boolean isGameOn();

  /**
   * Get the description of dungeon size.
   *
   * @return the string of info
   */
  String getDungeonSize();

  /**
   * Get the InterConnectivity of a dungeon.
   *
   * @return the number of InterConnectivity
   */
  int getInterConnectivity();

  /**
   * Get the treasure percentage of a dungeon.
   *
   * @return the percentage of treasure
   */
  float getDungeonPercentage();

  /**
   * Get the treasure percentage of a dungeon.
   *
   * @return the percentage of treasure
   */
  float getTreasurePercentage();

  /**
   * Get the monster quantity of a dungeon.
   *
   * @return the number of monsters
   */
  int getMonsterQuantity();

  /**
   * Get the wrapping setting of a dungeon.
   *
   * @return true if it's wrapping, false otherwise
   */
  boolean getWrappingState();

  /**
   * Get the row quantity of a dungeon.
   *
   * @return the number of rows
   */
  int getDungeonNumberOfRows();

  /**
   * Get the column quantity of a dungeon.
   *
   * @return the number of columns
   */
  int getDungeonNumberOfCols();

  /**
   * Get the location history of player.
   *
   * @return the history hashSet
   */
  HashSet<Integer> getPlayerHistory();

  /**
   * Get the object of a location.
   *
   * @param id the id of location
   * @return a Location class object
   */
  Location getDungeonSpot(int id);

  /**
   * Get the updated player info.
   *
   * @return the description of player state
   */
  String getThePlayerState();

  /**
   * Get the end point of a game.
   *
   * @return id of end point
   */
  int getGameEndPoint();

  /**
   * Get the total number of treasure.
   *
   * @return the quantity
   */
  int getGemNumber();
}

