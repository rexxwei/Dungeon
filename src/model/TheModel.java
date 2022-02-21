package model;

import static helper.HelperClass.randomNumber;

import dungeon.Cave;
import dungeon.Dungeon;
import dungeon.Game;
import dungeon.Location;
import dungeon.MonsterStatus;
import dungeon.Player;
import dungeon.TheDungeon;
import dungeon.Tunnel;

import java.util.HashSet;
import java.util.Set;

/**
 * The class implements Model interface.
 *
 */
public class TheModel implements Model {
  private Player player;
  private Dungeon dungeon;
  private Game game;
  private TheDungeon backupDungeon;
  private int preStartPoint;
  private int preEndPoint;

  /**
   * The default constructor of a model object.
   *
   * @param g the game which the model will work with.
   */
  public TheModel(Game g) {
    this.game = g;
    this.player = g.getPlayer();
    this.dungeon = g.getDungeon();
    this.backupDungeon = ((TheDungeon)(g.getDungeon())).clone();
    startAGame();
  }

  @Override
  public TheDungeon getBackupDungeon() {
    return this.backupDungeon;
  }

  @Override
  public void move(String dir) {
    //make a move
    switch (dir.toLowerCase()) {
      case "e":
        try {
          toEast();
          addCurrToHistory();
        }
        catch (Exception ex) {
          throw new IllegalArgumentException("not a legal move");
        }
        break;
      case "s":
        try {
          toSouth();
          addCurrToHistory();
        }
        catch (Exception ex) {
          throw new IllegalArgumentException("not a legal move");
        }
        break;
      case "w":
        try {
          toWest();
          addCurrToHistory();
        }
        catch (Exception ex) {
          throw new IllegalArgumentException("not a legal move");
        }
        break;
      default:
        try {
          toNorth();
          addCurrToHistory();
        }
        catch (Exception ex) {
          throw new IllegalArgumentException("not a legal move");
        }
        break;
    }

    //check the safety
//    if (checkMonsterState() == MonsterStatus.Normal) {
//      //System.out.println("\nPlayer die, game over!");
//      //this.game.turnGameOff();
//    }
//    else if (checkMonsterState() == MonsterStatus.Injured) {
//
//    }
//    else {
//
//    }

    //check reach destination
    if (checkArriveEnd()) {
      System.out.println("Game is over");
      this.game.turnGameOff();
    }

  }

  @Override
  public void addCurrToHistory() {
    System.out.println(currentPosition().getIndex());
    this.player.setHistory(currentPosition().getIndex());
  }

  @Override
  public boolean checkSafety() {
    StringBuilder feedback = new StringBuilder();
    if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus() == MonsterStatus.Normal) {
      return false;
    }
    else if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus() == MonsterStatus.Injured) {
      return false;
    }
    else if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus() == MonsterStatus.Dead) {
      System.out.println("\nThere is a dead Otyugh here.");
      feedback.append("\nThere is a dead Otyugh here.");
    }

    return true;
  } //end of checkSafety()


  /**
   * Check the state of monster in current cave.
   * @return true if it's alive
   */
  @Override
  public MonsterStatus checkMonsterState() {
    StringBuilder feedback = new StringBuilder();
    //if monster alive
    if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus() == MonsterStatus.Normal) {
      System.out.println("\n[monster]there is an Otyugh here");
      feedback.append("\n[monster]there is an Otyugh here");
      return MonsterStatus.Normal;
    }
    //if monster is injured
    else if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus() == MonsterStatus.Injured) {
      System.out.println("\n[injured]The Otyugh is injured");
      feedback.append("\n[injured]The Otyugh is injured");
      return MonsterStatus.Injured;
    }
    // monster is dead
    else if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus() == MonsterStatus.Dead) {
      System.out.println("\n[dead]There is a dead Otyugh here.");
      feedback.append("\n[dead]There is a dead Otyugh here.");
    }
    return MonsterStatus.Dead;
  }

  @Override
  public boolean checkArriveEnd() {
    //call the function in Player class to start game
    if (this.player.getLocation() == this.game.getEndPoint()
            && this.game.getEndPoint() != -1) {
      System.out.println("\n[Arrive destination, player win]");
      return true;
    }
    return false;
  }


  @Override
  public String startAGame() {

    //this.player.setDungeon(this.dungeon);
    this.dungeon.print();
    int rows = this.dungeon.numberOfRows();
    int cols = this.dungeon.numberOfCols();
    //Scanner uInput = new Scanner(System.in);
    int start = randomNumber(1, this.dungeon.getNumberOfVertices());
    this.game.setStartPoint(start);
    //make sure no monster in cave
    while (this.dungeon.getTheSpot(start) instanceof Tunnel
            || this.dungeon.getTheSpot(start).getSmell().equalsIgnoreCase("Stinky")) {
      start = randomNumber(1, this.dungeon.getNumberOfVertices());

    }
    this.player.setPosition(start);
    this.player.setHistory(start);
    this.preStartPoint = start;
    this.player.setArrowQty(3);
    int[] coor = indexToCoor(start);
    this.player.setPlayerCoor(coor[0], coor[1]);

    StringBuilder feedback = new StringBuilder();
    feedback.append("\nPlayer start @ (").append(this.player.inquireRowIndex());
    feedback.append(", ").append(this.player.inquireColIndex()).append(")");

    //get destination
    int end = randomNumber(1, this.dungeon.getNumberOfVertices());
    this.preEndPoint = end;
    this.game.setEndPoint(end);
    coor = indexToCoor(end);

    feedback.append("\nThe destination is (");
    feedback.append(coor[0]).append(", ").append(coor[1]).append(")\n");

    game.turnGameOn();
    feedback.append(displayLocationDetail());
    return feedback.toString();

  } //end of startGame()


  /**
   * Force to end a game.
   *
   */
  @Override
  public void freshNewGame() {
    //flush previous record
    this.player.refreshPlayer();
    restoreDungeon();
    startAGame();
  }

  @Override
  public String startPreGame() {

    this.player.refreshPlayer();
    int start = this.preStartPoint;
    this.player.setPosition(start);
    this.player.setHistory(start);
    this.player.setArrowQty(3);
    int[] coor = indexToCoor(start);
    this.player.setPlayerCoor(coor[0], coor[1]);

    StringBuilder feedback = new StringBuilder();
    feedback.append("\nPlayer start @ (").append(this.player.inquireRowIndex());
    feedback.append(", ").append(this.player.inquireColIndex()).append(")");

    //get destination
    int end = preEndPoint;
    this.game.setEndPoint(end);
    coor = indexToCoor(end);
    feedback.append("\nThe destination is (");
    feedback.append(coor[0]).append(", ").append(coor[1]).append(")\n");

    game.turnGameOn();
    feedback.append(displayLocationDetail());
    return feedback.toString();

  } //end of startGame()

  @Override
  public void relocatePlayer() {
    player.setPosition(game.getStartPoint());
  }


  @Override
  public void restoreDungeon() {
    this.dungeon = this.backupDungeon;
    this.game.resetDungeon(this.backupDungeon);
  }


  @Override
  public String pick(String obj) {
    StringBuilder feedback = new StringBuilder();
    String theObj = "";
    //pickup arrow
    if (obj.equalsIgnoreCase("Arrow")) {
      try {
        // wipe out the arrow from location
        this.player.pickArrow(currentPosition());
        currentPosition().setArrow(-1);
        feedback.append("You pick up an arrow");
      }
      catch (IllegalStateException ex) {
        throw new IllegalArgumentException("No arrow to pick up");
      }
    }
    //pickup treasure
    else if (obj.equalsIgnoreCase("Diamond")
        || obj.equalsIgnoreCase("Ruby")
        || obj.equalsIgnoreCase("Sapphire")) {
      try {
        // wipe out the arrow from location
        theObj = currentPosition().getGemList().toString();
        this.player.pickGem(currentPosition());
        feedback.append("You pick up a treasure");
      }
      catch (IllegalArgumentException ex) {
        throw new IllegalArgumentException("No Treasure found");
      }

      feedback.append("You pick up ");
      feedback.append(theObj);
    }
    else { // no stuff to pickup
      throw new IllegalStateException("No such stuff to pick up");
    }
    // display the object

    //System.out.println(this.player.toString());
    return feedback.toString();
  }

  @Override
  public String toString() {
    return "Game {" + game + '}';
  }

  @Override
  public int[] getPlayerPos() {
    int[] pos = new int[2];
    pos[0] = this.player.inquireRowIndex();
    pos[1] = this.player.inquireColIndex();
    return pos;
  }

  @Override
  public int[] indexToCoor(int index) {
    int[] pos = new int[2];
    pos[0] = index / this.dungeon.numberOfCols();
    pos[1] = index % this.dungeon.numberOfCols();
    return pos;
  }

  @Override
  public int coorToIndex(int row, int col) {
    return row * this.dungeon.numberOfCols() + col;
  }

  @Override
  public String displayLocationDetail() {
    StringBuilder positionInfo = new StringBuilder();
    Location theLocation = currentPosition();

    if (theLocation.getSmell().equalsIgnoreCase("LessPungent")) {
      positionInfo.append("\nYou smell sth odd nearby");
    }

    if (theLocation.getSmell().equalsIgnoreCase("Pungent")) {
      positionInfo.append("\nYou smell sth terrible nearby");
    }

    if (theLocation instanceof Cave) {
      if (theLocation.getGemList() != null
              && theLocation.getGemList().size() > 0) {
        positionInfo.append("\nYou find ");
        positionInfo.append(theLocation.getGemList());
        positionInfo.append(" here");
      }
      if (theLocation.getArrow() > 0) {
        positionInfo.append("\nYou find ");
        positionInfo.append(theLocation.getArrow());
        positionInfo.append(" arrow here");
      }

      positionInfo.append("\nYou are in a cave\nDoors lead to ");
    } else {
      positionInfo.append("\nYou are in a tunnel\nthat continues to ");
    }

    positionInfo.append(getAllExit());

    //get current position
    positionInfo.append("\nyou @ ");
    positionInfo.append(currentPosition().toString());

    return positionInfo.toString();
  }

  @Override
  public String getAllExit() {
    Set<String> allExit = this.dungeon.findAllExit(this.player.getLocation());
    return allExit.toString().toUpperCase();
  }

  @Override
  public int getArrowQty() {
    return this.player.getArrowQty();
  }

  @Override
  public Dungeon getTheDungeon() {
    return this.dungeon;
  }

  @Override
  public Player getThePlayer() {
    return this.player;
  }

  @Override
  public int[] toNorth() {
    //check direction is available
    if (this.dungeon.getTheSpot(this.player.getLocation()).getNorth() != 1) {
      throw new IllegalArgumentException("Direction not available");
    }

    //check on north side edge
    if (this.player.inquireRowIndex() == 0) {
      if (this.dungeon.getWrapping()) {
        // move to bottom
        this.player.setPlayerCoor(this.dungeon.numberOfRows() - 1, this.player.inquireColIndex());
        updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
      } else { // dungeon not wrapping
        throw new IllegalArgumentException("Direction not available");
      }
    }
    else { //not on side edge
      this.player.setPlayerCoor(this.player.inquireRowIndex() - 1, this.player.inquireColIndex());
      updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
    }
    int[] res = new int[2];
    res[0] = this.player.inquireRowIndex();
    res[1] = this.player.inquireColIndex();
    return res;
  }

  @Override
  public int[] toSouth() {
    if (this.dungeon.getTheSpot(this.player.getLocation()).getSouth() != 1) {
      throw new IllegalArgumentException("Direction not available");
    }
    //check on south side edge
    if (this.player.inquireRowIndex() == this.dungeon.numberOfRows() - 1) {
      if (this.dungeon.getWrapping()) { //the dungeon is wrapping
        // move to upside
        this.player.setPlayerCoor(0, this.player.inquireColIndex());
        updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
      } else { // dungeon not wrapping
        throw new IllegalArgumentException("Direction not available");
      }
    } else { //not on side edge
      this.player.setPlayerCoor(this.player.inquireRowIndex() + 1, this.player.inquireColIndex());
      updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
    }
    int[] res = new int[2];
    res[0] = this.player.inquireRowIndex();
    res[1] = this.player.inquireColIndex();
    return res;
  }

  @Override
  public int[] toWest() {
    if (this.dungeon.getTheSpot(this.player.getLocation()).getWest() != 1) {
      throw new IllegalArgumentException("Direction not available");
    }
    //check on west side edge
    if (this.player.inquireColIndex() == 0) {
      if (this.dungeon.getWrapping()) { //the dungeon is wrapping
        // move to east side
        this.player.setPlayerCoor(this.player.inquireRowIndex(), this.dungeon.numberOfCols() - 1);
        updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
      } else { // dungeon not wrapping
        throw new IllegalArgumentException("Direction not available");
      }
    } else { //not on side edge
      this.player.setPlayerCoor(this.player.inquireRowIndex(), this.player.inquireColIndex() - 1);
      updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
    }
    int[] res = new int[2];
    res[0] = this.player.inquireRowIndex();
    res[1] = this.player.inquireColIndex();
    return res;
  }

  @Override
  public int[] toEast() {
    if (this.dungeon.getTheSpot(this.player.getLocation()).getEast() != 1) {
      throw new IllegalArgumentException("Direction not available");
    }
    //check on west side edge
    if (this.player.inquireColIndex() == this.dungeon.numberOfCols() - 1) {
      if (this.dungeon.getWrapping()) { //the dungeon is wrapping
        // move to east side
        this.player.setPlayerCoor(this.player.inquireRowIndex(), 0);
        updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
      } else { // dungeon not wrapping
        throw new IllegalArgumentException("Direction not available");
      }
    } else { //not on side edge
      this.player.setPlayerCoor(this.player.inquireRowIndex(), this.player.inquireColIndex() + 1);
      updateByCoor(this.player.inquireRowIndex(), this.player.inquireColIndex());
    }
    int[] res = new int[2];
    res[0] = this.player.inquireRowIndex();
    res[1] = this.player.inquireColIndex();
    return res;
  }

  @Override
  public void updateByCoor(int row, int col) {
    this.player.setPosition(row * dungeon.numberOfCols() + col);
  }

  @Override
  public void updateByIndex(int pos) {
    this.player.setPlayerCoor(pos / dungeon.numberOfCols(), pos % dungeon.numberOfCols());
  }

  @Override
  public StringBuilder shoot(int dis, String dir) {
    StringBuilder feedback = new StringBuilder();
    int currLocation = this.player.getLocation();
    int currRow = this.dungeon.inquireCoorRow(currLocation);
    int currCol = this.dungeon.inquireCoorCol(currLocation);
    //boolean wrapping = this.dungeon.getWrapping();
    int[] newCoor;

    if (this.player.getArrowQty() > 0) {
      //launch shoot
      if (!this.dungeon.getTheSpot(currLocation).checkDir(dir)) {
        System.out.println("\nInvalid direction");
        feedback.append("\nInvalid direction");
        return feedback;
      }
      else {
        newCoor = moveToNext(dir, currRow, currCol);
      }
      currRow = newCoor[0];
      currCol = newCoor[1];
      currLocation = this.dungeon.inquireIndex(currRow, currCol);
      if (this.dungeon.getTheSpot(currLocation) instanceof Cave) {
        dis -= 1;
      }

      while (dis > 0) { //distance > 0
        //--if location is a cave
        if (this.dungeon.getTheSpot(currLocation) instanceof Cave) {
          if (!this.dungeon.getTheSpot(currLocation).checkDir(dir)) {
            System.out.println("\n[ERROR]: direction not available");
            feedback.append("\n[ERROR]: direction not available");
            return feedback;
          }
          else {
            newCoor = moveToNext(dir, currRow, currCol);
          }
          currRow = newCoor[0];
          currCol = newCoor[1];
          currLocation = this.dungeon.inquireIndex(currRow, currCol);
          if (this.dungeon.getTheSpot(currLocation) instanceof Cave) {
            dis -= 1;
          }
        }  // end of cave
        else {  //-- location is a tunnel
          //search another exit in tunnel
          String enterDir = sourceDir(dir);
          String exitDir = exitOfTunnel(currLocation, enterDir);
          newCoor = moveToNext(exitDir, currRow, currCol);
          dir = exitDir;
          currRow = newCoor[0];
          currCol = newCoor[1];
          currLocation = this.dungeon.inquireIndex(currRow, currCol);
          if (this.dungeon.getTheSpot(currLocation) instanceof Cave) {
            dis -= 1;
          }
        } //end of tunnel
      } //  while (dis > 0)

      //store the result of shooting
      if (this.dungeon.getTheSpot(currLocation) instanceof Cave
              && this.dungeon.getTheSpot(currLocation).checkMonster()) {
        System.out.println("\n[hit]You hear a great howl in the distance");
        feedback.append("\n[hit]You hear a great howl in the distance");
        //update status of monster
        this.dungeon.getTheSpot(currLocation).hitMonster();

      } else {
        System.out.println("\n[miss]You shoot an arrow into the darkness");
        feedback.append("\n[miss]You shoot an arrow into the darkness");
      }
      this.player.setArrowQty(-1);
    } // end of while has arrows
    else {
      System.out.println("\n[out]you are out of arrow");
      feedback.append("\n[out]you are out of arrow");
    }

    System.out.println(String.format("\narrow land @ (%d,%d)", currRow, currCol));
    feedback.append(String.format("\narrow land @ (%d,%d)", currRow, currCol));
    return feedback;
  }

  /**
   * Let the arrow fly along the path.
   *
   * @param dir the direction to go
   * @param currRow row number
   * @param currCol column number
   * @return new coordinate
   */
  public int[] moveToNext(String dir, int currRow, int currCol) {

    if (dir.equalsIgnoreCase("n")) {
      if (currRow == 0) { //on the board edge
        currRow = this.dungeon.numberOfRows() - 1;
      }
      else { //not on the board edge
        currRow -= 1;
      }
    }
    if (dir.equalsIgnoreCase("e")) {
      if (currCol == this.dungeon.numberOfCols() - 1) { //on the board edge
        currCol = 0;
      }
      else { //not on the board edge
        currCol += 1;
      }
    }
    if (dir.equalsIgnoreCase("s")) {
      if (currRow == this.dungeon.numberOfRows() - 1) { //on the board edge
        currRow = 0;
      }
      else { //not on the board edge
        currRow += 1;
      }
    }
    if (dir.equalsIgnoreCase("w")) {
      if (currCol == 0) { //on the board edge
        currCol = this.dungeon.numberOfCols() - 1;
      }
      else { //not on the board edge
        currCol -= 1;
      }
    }
    int[] res = new int[2];
    res[0] = currRow;
    res[1] = currCol;
    return res;
  }

  @Override
  public String sourceDir(String dir) {
    switch (dir) {
      case "n":
        return "s";
      case "e":
        return "w";
      case "s":
        return "n";
      default:
        return "e";
    }
  }

  @Override
  public String exitOfTunnel(int location, String dir) {

    //check available exit
    Set<String> tunnelExit;
    tunnelExit = this.dungeon.findAllExit(location);
    tunnelExit.remove(dir);
    int n = tunnelExit.size();
    String[] arr = new String[n];

    int i = 0;
    for (String x : tunnelExit) {
      arr[i++] = x;
    }
    return arr[0];
  }

  @Override
  public Location currentPosition() {
    return this.dungeon.getTheSpot(this.player.getLocation());
  }

  @Override
  public String curAvailableDir() {

    String dir = " ";
    if (this.getTheDungeon().getTheSpot(getThePlayer().getLocation()).getNorth() == 1) {
      dir += "N";
    }
    if (this.getTheDungeon().getTheSpot(getThePlayer().getLocation()).getSouth() == 1) {
      dir += "S";
    }
    if (this.getTheDungeon().getTheSpot(getThePlayer().getLocation()).getEast() == 1) {
      dir += "E";
    }
    if (this.getTheDungeon().getTheSpot(getThePlayer().getLocation()).getWest() == 1) {
      dir += "W";
    }

    return dir.trim();
  }

  @Override
  public boolean inCaveNow() {
    return currentPosition() instanceof Cave;
  }


  @Override
  public Game getTheGame() {
    return this.game;
  }

  @Override
  public boolean isGameOn() {
    return this.game.checkGameOn();
  }

  @Override
  public String getDungeonSize() {
    return dungeon.getSize();
  }

  @Override
  public int getInterConnectivity() {
    return dungeon.getInterConnectivity();
  }

  @Override
  public float getDungeonPercentage() {
    return dungeon.getInterConnectivity();
  }

  @Override
  public float getTreasurePercentage() {
    return dungeon.getPercentage();
  }

  @Override
  public int getMonsterQuantity() {
    return (int) (dungeon.getDifficulty()
            * dungeon.getNumberOfVertices());
  }

  @Override
  public boolean getWrappingState() {
    return dungeon.getWrapping();
  }

  @Override
  public int getDungeonNumberOfRows() {
    return dungeon.numberOfRows();
  }

  @Override
  public int getDungeonNumberOfCols() {
    return dungeon.numberOfCols();
  }

  @Override
  public HashSet<Integer> getPlayerHistory() {
    return player.getHistory();
  }

  @Override
  public Location getDungeonSpot(int id) {
    return dungeon.getTheSpot(id);
  }

  @Override
  public String getThePlayerState() {
    return player.toString();
  }

  @Override
  public int getGameEndPoint() {
    return game.getEndPoint();
  }

  @Override
  public int getGemNumber() {
    return player.getGemQty();
  }

  @Override
  public void terminateCurGame() {
    this.game.turnGameOff();
  }

  @Override
  public void exchangeForWeapon() {
    if (getGemNumber() >= 4) {
      System.out.println("Gem is enough");
      //buy gems to kill monster
      this.killTheMonster(currentPosition());
      //update the player gem qty
      player.deductTreasure();
    }
    else {
      throw new IllegalStateException("No enough treasure");
    }
  }

  @Override
  public void killTheMonster(Location l) {
    this.getTheDungeon().killMonsterAt(l);
  }

}
