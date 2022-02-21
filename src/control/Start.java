package control;

import model.Model;

/**
 * A class implementing the Controller interface to start a game.
 *
 */
public class Start implements CommandController {

  @Override
  public void execute(Model m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    //execute operation in GameModel
    m.startAGame();
  }

}

