package model;

import dungeon.Dungeon;
import dungeon.Game;
import dungeon.Location;
import dungeon.MonsterStatus;
import dungeon.Player;
import dungeon.TheDungeon;

/**
 * This interface display the operations of text game.
 *
 */
public interface Model extends ViewOnlyModel {

  /**
   * Get the backupDungeon.
   *
   * @return the backup dungeon
   */
  TheDungeon getBackupDungeon();

  /**
   * Move the player by the specified direction.
   *
   * @param dir the distance to move
   */
  void move(String dir);

  /**
   * Add current location to player's history.
   */
  void addCurrToHistory();

  /**
   * Check whether it's safe or not when enter a new position.
   *
   * @return true if alive, false if eaten by monster
   */
  boolean checkSafety(); //end of checkSafety()

  /**
   * Start a new game for the player.
   *
   * @return The result of start a game
   */
  String startAGame();

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
   *  @param dis the number of caves
   * @param dir the distance to move
   * @return String Builder contain the feedback
   */
  StringBuilder shoot(int dis, String dir);

  /**
   * Start a new game.
   */
  void freshNewGame();

  /**
   * Resume the same game.
   *
   * @return the result of re-start the game
   */
  String startPreGame();

  /**
   * Reset the location of player.
   */
  void relocatePlayer();

  /**
   * Reset the dungeon use backDungeon.
   */
  void restoreDungeon();

  /**
   * Shoot an arrow by specified number of caves and direction.
   *
   * @param obj the object to pickup
   */
  String pick(String obj);

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
   * Return the dungeon of the game.
   *
   * @return the dungeon of the game
   */
  Dungeon getTheDungeon();

  /**
   * Return the player class of the game.
   *
   * @return the player of the game
   */
  Player getThePlayer();


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

  MonsterStatus checkMonsterState();

  /**
   * Check whether the player arrive the end or not.
   *
   * @return true if yes, false otherwise
   */
  boolean checkArriveEnd();

  /**
   * Move to the north of current location.
   *
   * @return the coordinate of new position
   */
  int[] toNorth();

  /**
   * Move to the south of current location.
   *
   * @return the coordinate of new position
   */
  int[] toSouth();

  /**
   * Move to the west of current location.
   *
   * @return the coordinate of new position
   */
  int[] toWest();

  /**
   * Move to the east of current location.
   *
   * @return the coordinate of new position
   */
  int[] toEast();

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
   * Return the game.
   *
   * @return the game in using
   */
  Game getTheGame();

  /**
   * Terminate the current game.
   */
  void terminateCurGame();


  /**
   * Exchange 4 treasure for weapon to kill monster.
   */
  void exchangeForWeapon();

  /**
   * Kill the monster in the position.
   *
   * @param l the location
   */
  void killTheMonster(Location l);
}
