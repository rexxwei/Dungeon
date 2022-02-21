package model;

import static helper.HelperClass.randomOneZero;

import dungeon.Cave;
import dungeon.Dungeon;
import dungeon.Game;
import dungeon.Location;
import dungeon.MonsterStatus;
import dungeon.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * The class implement ViewOnlyModel interface.
 */
public class TheViewOnlyMode implements ViewOnlyModel {

  private Player player;
  private Dungeon dungeon;
  private Game game;

  /**
   * The default constructor of a model object.
   *
   * @param g the game which the model will work with.
   */
  public TheViewOnlyMode(Game g) {
    this.game = g;
    this.player = g.getPlayer();
    this.dungeon = g.getDungeon();
  }

  @Override
  public boolean checkSafety() {
    StringBuilder feedback = new StringBuilder();
    if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus().equals("Normal")) {
      System.out.println("\nChomp, chomp, you are eaten by an Otyugh!\nBetter luck next time");
      feedback.append("\nChomp, chomp, you are eaten by an Otyugh!\nBetter luck next time");

      return false;
    }
    else if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus().equals("Injured")) {
      if (randomOneZero() == 1) {
        System.out.println("\nRun! There is an Otyugh here.");
        feedback.append("\nRun! There is an Otyugh here.");
      }
      else {
        System.out.println("\nChomp, chomp, you are eaten by an Otyugh!\nBetter luck next time");
        feedback.append("\nChomp, chomp, you are eaten by an Otyugh!\nBetter luck next time");
        //end the game
        return false;
      }
    }
    else if (currentPosition() instanceof Cave
            && currentPosition().getSmell().equalsIgnoreCase("Stinky")
            && currentPosition().getMonsterStatus().equals("Dead")) {
      System.out.println("\nThere is a dead Otyugh here.");
      feedback.append("\nThere is a dead Otyugh here.");
    }

    return true;
  } //end of checkSafety()

  @Override
  public MonsterStatus checkMonsterState() {
    return null;
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
  public void updateByCoor(int row, int col) {
    this.player.setPosition(row * dungeon.numberOfCols() + col);
  }

  @Override
  public void updateByIndex(int pos) {
    this.player.setPlayerCoor(pos / dungeon.numberOfCols(), pos % dungeon.numberOfCols());
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
    if (this.currentPosition().getNorth() == 1) {
      dir += "N";
    }
    if (this.currentPosition().getSouth() == 1) {
      dir += "S";
    }
    if (this.currentPosition().getEast() == 1) {
      dir += "E";
    }
    if (this.currentPosition().getWest() == 1) {
      dir += "W";
    }

    return dir.trim();
  }

  @Override
  public boolean inCaveNow() {
    return currentPosition() instanceof Cave;
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
    return 0;
  }


}
