package control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Game;
import dungeon.TheDungeon;
import dungeon.TheGame;
import dungeon.ThePlayer;
import model.Model;
import model.TheModel;
import org.junit.Before;
import org.junit.Test;
import view.TheGameView;


/**
 * Class to test the game controller.
 */
public class GameControllerTest {

  String[] args = "10 10 30 0.50f 0.25f false".split(" ");
  int rows = Integer.parseInt(args[0]);
  int cols = Integer.parseInt(args[1]);
  int ic = Integer.parseInt(args[2]);
  float p = Float.parseFloat(args[3]);
  float d = Float.parseFloat(args[4]);
  boolean w = Boolean.parseBoolean(args[5]);

  Game game = new TheGame(new ThePlayer(), new TheDungeon(rows, cols, ic, p, d, w));
  Model m;
  TheGameView v;
  Controller c;

  /**
   * Set up for all tests.
   */
  @Before
  public void setUp() {
    m = new TheModel(game);
    v = new TheGameView(m);
    c = new GameController(m, v);
  }

  /**
   * Test the function of playTheGame().
   */
  @Test
  public void playTheGame() {
    c.playTheGame();
    assertEquals(true, m.isGameOn());
  }


  /**
   * Test use controller to make a shooting.
   */
  @Test
  public void handleShoot() {
    c.handleShoot(1,"w");
    c.playTheGame();
    c.handleShoot(1,"n");
    c.handleShoot(1,"s");
    c.handleShoot(1,"e");
    assertTrue(c.handleShoot(1,"w").toString().toLowerCase().contains("out"));
  }


  /**
   * Test pick up an illegal stuff. Expecting exception.
   */
  @Test (expected = IllegalStateException.class)
  public void illegalPickup() {
    c.handlePickup("A Test");
  }

  /**
   * Test pick an arrow. Expecting Argument Exception.
   */
  @Test (expected = IllegalArgumentException.class)
  public void legalPickup() {
    c.handlePickup("arrow");
  }

  /**
   * Test start a new game.
   */
  @Test
  public void startNewGame() {
    c.startNewGame();
    assertEquals(true, m.isGameOn());
    assertEquals(3, m.getArrowQty());
  }

}