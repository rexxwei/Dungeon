package control;

import model.Model;
import view.TheGameView;
import view.View;

/**
 * Class of controller.
 */
public class GameController implements Controller  {

  private final Model model;
  private final View view;

  /**
   * Class of controller.
   */
  public GameController(Model m, TheGameView v) {

    this.model = m;
    this.view = v;
    v.addGameListener(this);
  }

  @Override
  public void playTheGame() {
    //model.startAGame();
    view.turnOnGameView();
  }

  @Override
  public void handlePickup(String obj) {
    model.pick(obj);
  }

  @Override
  public StringBuilder handleShoot(int dis, String dir) {
    return model.shoot(dis, dir);
  }

  @Override
  public void handleMove(String dir) {
    model.move(dir);
  }

  @Override
  public void startNewGame() {
    model.terminateCurGame();
    view.blackTheBoard();
    model.freshNewGame();
  }

  @Override
  public void resumeSameGame() {
    model.startPreGame();
  }

  @Override
  public void terminateGame() {
    model.terminateCurGame();
  }

  @Override
  public void exchangeWeapon() {
    model.exchangeForWeapon();
  }

}

