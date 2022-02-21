package control;

import model.Model;

/**
 * The interface specified the method for a game controller object.
 *
 */
public interface Controller {

  /**
   * The method to play a game.
   *
   * @param m the model to achieve various functionality for a game.
   */
  void playTheGame();

  /**
   * Handle pickup when view calling for the function.
   *
   * @param obj the stuff to pickup
   */
  void handlePickup(String obj);

  /**
   * Achieve shooting when view call the method.
   * @param dis distance to shoot
   * @param dir direction to shoot
   * @return the result of shooting
   */
  StringBuilder handleShoot(int dis, String dir);

  /**
   * Make a move when view pass in the call.
   * @param dir the direction to move
   */
  void handleMove(String dir);

  /**
   * Start a new game.
   */
  void startNewGame();

  /**
   * Resume the same previous game state.
   */
  void resumeSameGame();

  /**
   * Call model to end a game.
   */
  void terminateGame();

  /**
   * Exchange 4 treasure for a weapon to kill the monster.
   */
  void exchangeWeapon();
}
