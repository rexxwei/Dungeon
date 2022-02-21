package dungeon;

/**
 * The class implements Game interface.
 */
public class TheGame implements Game {

  private final ThePlayer player;
  private Dungeon dungeon;
  int startPoint;
  int endPoint;
  boolean gameIsOn;

  /**
   * The default constructor of a game object.
   *
   * @param player  the user to play the game.
   * @param dungeon the maze to conduct the game.
   */
  public TheGame(ThePlayer player, Dungeon dungeon) {
    this.player = player;
    this.dungeon = dungeon;
    //the start position for a game, -1 means no start point
    this.startPoint = -1;
    //the end position for a game, -1 means no end point
    this.endPoint = -1;
    this.gameIsOn = true;

  }

  @Override
  public String toString() {
    return player.toString();
  }

  @Override
  public void setStartPoint(int startPoint) {
    this.startPoint = startPoint;
  }

  @Override
  public void setEndPoint(int endPoint) {
    this.endPoint = endPoint;
  }

  @Override
  public int getStartPoint() {
    return this.startPoint;
  }

  @Override
  public int getEndPoint() {
    return this.endPoint;
  }

  @Override
  public Dungeon getDungeon() {
    return this.dungeon;
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  @Override
  public void turnGameOff() {
    this.gameIsOn = false;
  }

  @Override
  public boolean checkGameOn() {
    return this.gameIsOn;
  }

  @Override
  public void turnGameOn() {
    this.gameIsOn = true;
  }

  @Override
  public void resetDungeon(Dungeon d) {
    this.dungeon = d;
  }


}
