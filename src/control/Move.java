package control;

import model.Model;

/**
 * A class implementing the Controller interface to make a move.
 *
 */
public class Move implements CommandController {
  private final String dir;

  /**
   * Constructor of Move class.
   *
   * @param dir the direction to move.
   */
  public Move(String dir) {
    this.dir = dir;
  }

  @Override
  public void execute(Model m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    //execute operation in GameModel
    m.move(this.dir);
  }

}
