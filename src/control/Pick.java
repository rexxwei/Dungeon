package control;

import model.Model;

/**
 * A class implementing the Controller interface to pick a stuff.
 *
 */
public class Pick implements CommandController {
  private final String obj;

  /**
   * Constructor of Pick class.
   *
   * @param obj the object ot pick.
   */
  public Pick(String obj) {
    this.obj = obj;
  }

  @Override
  public void execute(Model m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    //execute operation in GameModel
    String res = m.pick(this.obj);
    System.out.println(res);
  }

}

