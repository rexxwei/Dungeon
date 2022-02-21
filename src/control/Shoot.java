package control;

import model.Model;

/**
 * A class implementing the Controller interface to shoot an arrow.
 *
 */
public class Shoot implements CommandController {
  private final int dis;
  private final String dir;

  /**
   * Constructor of Shoot class.
   *
   * @param dis the number of caves to shoot.
   * @param dir the direction to shoot.
   */
  public Shoot(int dis, String dir) {
    this.dis = dis;
    this.dir = dir;
  }

  @Override
  public void execute(Model m) {
    if (m == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    //execute operation in GameModel
    StringBuilder res = m.shoot(this.dis, this.dir);
    //System.out.println(res.toString());
    String[] finalRes = res.toString().split("\n");
    //display feedback
    for (int i = 0; i < finalRes.length - 1; i++) {
      System.out.println(finalRes[i]);
    }
  }

}
