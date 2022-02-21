package dungeon;

import java.util.ArrayList;

/**
 * The interface of location.
 *
 */
public interface Location {

  /**
   * Return the size of the tree (i.e., the number of elements in this tree).
   *
   * @return The number of elements in this tree
   */
  int getIndex();

  /**
   * Return the height of the tree.
   *
   * @return the height of the tree
   */
  int getRowID();

  /**
   * Return the height of the tree.
   *
   * @return the height of the tree
   */
  int getColID();

  /**
   * Return the treasure list of this location if it's cave. Return null if it's not.
   *
   * @return List of gems.
   */
  ArrayList<Treasure> getGemList();

  /**
   * Function to wipe out the info of gem for current location.
   *
   */
  void cleanGem();

  /**
   * Get the value of the direction.
   *
   * @return 1 if open, 0 otherwise
   */
  int getNorth();

  /**
   * Get the value of the direction.
   *
   * @return 1 if open, 0 otherwise
   */
  int getWest();

  /**
   * Get the value of the direction.
   *
   * @return 1 if open, 0 otherwise
   */
  int getSouth();

  /**
   * Get the value of the direction.
   *
   * @return 1 if open, 0 otherwise
   */
  int getEast();

  /**
   * Increase the smell of place after adding monster to dungeon.
   *
   */
  void increaseSmell();

  /**
   * Return the existence of monster.
   *
   * @return true if there is monster, false otherwise.
   */
  boolean checkMonster();

  /**
   * Check the direction of a location.
   *
   * @return true if the direction is available, false otherwise.
   */
  boolean checkDir(String dir);

  /**
   * Adjust the status of monster in the location.
   *
   */
  void hitMonster();

  /**
   * Display the status of monster.
   *
   * @return A string contain the info
   */
  String displayMonster();

  /**
   * Get the smell of a location.
   *
   * @return A string contain the info
   */
  String getSmell();

  /**
   * Set the number of arrow for a location.
   *
   */
  void setArrow(int qty);

  /**
   * Get the number of arrow in a location.
   *
   * @return the number of arrow
   */
  int getArrow();

  /**
   * Get the status of monster in the location.
   *
   * @return a string contains the info.
   */
  MonsterStatus getMonsterStatus();
}
