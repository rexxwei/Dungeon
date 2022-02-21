package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeon.Game;
import dungeon.TheDungeon;
import dungeon.TheGame;
import dungeon.ThePlayer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * The class to test a ViewOnlyModel.
 */
public class ViewOnlyModelTest {

  String[] args = "10 10 30 0.50f 0.00f false".split(" ");
  int rows = Integer.parseInt(args[0]);
  int cols = Integer.parseInt(args[1]);
  int ic = Integer.parseInt(args[2]);
  float p = Float.parseFloat(args[3]);
  float d = Float.parseFloat(args[4]);
  boolean w = Boolean.parseBoolean(args[5]);

  Game game = new TheGame(new ThePlayer(), new TheDungeon(rows, cols, ic, p, d, w));

  Model m;
  ViewOnlyModel vom;

  /**
   * Set up for all tests.
   */
  @Before
  public void setUp() {
    m = new TheModel(game);
    m.startAGame();
    vom = (ViewOnlyModel) m;
  }


  /**
   * Test the model can read arrow quantity.
   */
  @org.junit.Test
  public void getArrowQty() {
    System.out.println(vom.getArrowQty());
    assertEquals(3, vom.getArrowQty());

  }

  /**
   * Test the model can read player position.
   */
  @Test
  public void getPlayerPos() {
    assertEquals(Arrays.toString(Arrays.stream(m.getPlayerPos()).toArray()),
            Arrays.toString(Arrays.stream(vom.getPlayerPos()).toArray()));
  }

  /**
   * Test get the type of current position.
   */
  @Test
  public void inCaveNow() {
    assertEquals(m.inCaveNow(), vom.inCaveNow());
  }

  /**
   * Test get the state of a game.
   */
  @Test
  public void isGameOver() {
    assertTrue(vom.isGameOn());
    assertEquals(m.isGameOn(), vom.isGameOn());
  }

  /**
   * Test get the correct Dungeon Size of a game.
   */
  @Test
  public void getDungeonSize() {
    assertEquals(m.getDungeonSize(), vom.getDungeonSize());
  }

  /**
   * Test get the correct InterConnectivity of a game.
   */
  @Test
  public void getInterConnectivity() {
    assertEquals(m.getInterConnectivity(), vom.getInterConnectivity());
  }

  /**
   * Test get the correct Treasure Percentage of a game.
   */
  @Test
  public void getTreasurePercentage() {
    assertTrue(m.getTreasurePercentage() == vom.getTreasurePercentage());
  }

  /**
   * Test get the correct Monster Quantity of a game.
   */
  @Test
  public void getMonsterQuantity() {
    assertEquals(m.getMonsterQuantity(), vom.getMonsterQuantity());
  }

  /**
   * Test get the wrapping setting of a game.
   */
  @Test
  public void getWrappingState() {
    assertEquals(m.getWrappingState(), vom.getWrappingState());
  }

  /**
   * Test get the same history of a player.
   */
  @Test
  public void getPlayerHistory() {
    assertEquals(m.getInterConnectivity(), vom.getInterConnectivity());
  }

  /**
   * Test get the same info of a player.
   */
  @Test
  public void getThePlayerState() {
    assertEquals(m.getThePlayerState(), vom.getThePlayerState());
  }

  /**
   * Test get the same endPoint of a game.
   */
  @Test
  public void getGameEndPoint() {
    assertEquals(m.getGameEndPoint(), vom.getGameEndPoint());
  }

}