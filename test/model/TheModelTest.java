package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Game;
import dungeon.TheDungeon;
import dungeon.TheGame;
import dungeon.ThePlayer;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test a model object.
 */
public class TheModelTest {

  String[] args = "10 10 30 0.50f 0.05f false".split(" ");
  int rows = Integer.parseInt(args[0]);
  int cols = Integer.parseInt(args[1]);
  int ic = Integer.parseInt(args[2]);
  float p = Float.parseFloat(args[3]);
  float d = Float.parseFloat(args[4]);
  boolean w = Boolean.parseBoolean(args[5]);

  Game game = new TheGame(new ThePlayer(), new TheDungeon(rows, cols, ic, p, d, w));
  Model m;

  /**
   * Set up for all tests.
   */
  @Before
  public void setUp() {
    m = new TheModel(game);
  }

  /**
   * Test make a move.
   */
  @Test
  public void move() {
    int before;
    int after;
    before = m.currentPosition().getIndex();
    try {
      m.move("s");
    }
    catch (Exception ex) {
      System.out.println("move failed");
    }
    after = m.currentPosition().getIndex();
    assertTrue(before < after);

    before = m.currentPosition().getIndex();
    try {
      m.move("n");
    }
    catch (Exception ex) {
      System.out.println("move failed");
    }
    after = m.currentPosition().getIndex();
    assertTrue(before > after);

  }

  /**
   * Test checking arrive destination.
   */
  @Test
  public void checkArriveEnd() {
    assertFalse(m.checkArriveEnd());
  }

  /**
   * Test start a game.
   */
  @Test
  public void startAGame() {
    m.terminateCurGame();
    assertFalse(m.isGameOn());

    m.startAGame();
    assertTrue(m.isGameOn());

  }

  /**
   * Test the function of making a fresh new game.
   */
  @Test
  public void freshNewGame() {
    try {
      m.move("s");
    }
    catch (Exception ex) {
      System.out.println("move failed");
    }
    try {
      m.move("n");
    }
    catch (Exception ex) {
      System.out.println("move failed");
    }
    //assert history greater than 1 in current game
    assertTrue(m.getPlayerHistory().size() > 1);

    m.freshNewGame();
    //assert the history is just 1 in the new game
    assertFalse(m.getPlayerHistory().size() > 1);

  }

  /**
   * Test the function of making a fresh new game.
   */
  @Test
  public void testTurnGameOff() {
    m.terminateCurGame();
    assertFalse(m.isGameOn());
  }

  /**
   * Test the backupDungeon is the same as original one.
   */
  @Test
  public void testBackupDungeon() {

    assertEquals(m.getTheDungeon().toString(), m.getBackupDungeon().toString());

  }

  /**
   * Test resume the same game works.
   */
  @Test
  public void testResumeSameGame() {
    m.startAGame();
    int preStart = m.getTheGame().getStartPoint();
    int preEnd = m.getTheGame().getEndPoint();
    m.startPreGame();

    int newStart = m.getTheGame().getStartPoint();
    int newEnd = m.getTheGame().getEndPoint();

    //Assert the Start and End are the same place
    assertEquals(preStart, newStart);
    assertEquals(preEnd, newEnd);
  }

  /**
   * Test Start a new game works.
   */
  @Test
  public void testStartNewGame() {
    m.startAGame();
    int preStart = m.getTheGame().getStartPoint();
    int preEnd = m.getTheGame().getEndPoint();
    m.freshNewGame();

    int newStart = m.getTheGame().getStartPoint();
    int newEnd = m.getTheGame().getEndPoint();

    //Assert the Start and End are the different place
    assertNotEquals(preStart, newStart);
    assertNotEquals(preEnd, newEnd);
  }

}