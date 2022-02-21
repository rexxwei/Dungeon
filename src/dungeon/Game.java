package dungeon;

/**
 * The interface to indicate all the method for a game object.
 *
 */
public interface Game {

  /**
   * Set up the start location for a game.
   *
   * @param startPoint id of start point
   */
  void setStartPoint(int startPoint);

  /**
   * Set up the finish location for a game.
   *
   * @param endPoint id of start point
   */
  void setEndPoint(int endPoint);

  /**
   * Get the start location of a game.
   *
   */
  int getStartPoint();

  /**
   * Get the end location of a game.
   *
   * @return id of end point
   */
  int getEndPoint();

  /**
   * Get the dungeon of the game.
   *
   * @return the dungeon object in use
   */
  Dungeon getDungeon();

  /**
   * Get the player of the game.
   *
   * @return the player object in use
   */
  Player getPlayer();

  /**
   * Terminate a game.
   */
  void turnGameOff();

  /**
   * Check whether the game is on or not.
   *
   * @return true if yes, false otherwise
   */
  boolean checkGameOn();

  /**
   * Set a game to be on.
   */
  void turnGameOn();

  /**
   * Reset the dungeon use another one.
   * @param d the new dungeon
   */
  void resetDungeon(Dungeon d);
}
