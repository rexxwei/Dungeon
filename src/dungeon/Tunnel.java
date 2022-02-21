package dungeon;

import java.util.ArrayList;

/**
 * Class to represent the tunnel object. The object is similar to cave but just no treasure in it.
 *
 */
public class Tunnel implements Location {
  int index;
  int rowIndex;
  int colIndex;
  int north;
  int south;
  int east;
  int west;
  Smell smell;
  int arrow;

  /**
   * Constructor of tunnel instance.
   *
   * @param index of location (which is order in the grid).
   * @param rowIndex the number of rows
   * @param colIndex the number of columns
   * @param north the value of direction (1 means available, otherwise it's 0)
   * @param south the value of direction (1 means available, otherwise it's 0)
   * @param east the value of direction (1 means available, otherwise it's 0)
   * @param west the value of direction (1 means available, otherwise it's 0)
   */
  public Tunnel(int index, int rowIndex, int colIndex, int north, int south, int east, int west) {
    this.index = index;
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    this.smell = Smell.Fresh;
    this.arrow = 0;
  }

  @Override
  public int getIndex() {
    return this.index;
  }

  @Override
  public int getRowID() {
    return 0;
  }

  @Override
  public int getColID() {
    return 0;
  }

  @Override
  public ArrayList<Treasure> getGemList() {
    return null;
  }

  @Override
  public void cleanGem() {
    // default
  }

  @Override
  public String toString() {
    String arrowInfo = "";
    if (this.arrow > 0) {
      arrowInfo = " \nArrow:" + arrow;
    }

    return "Tunl @(" + rowIndex + "," + colIndex
            + ") \nN:" + north + ", E:" + east + ", S:" + south
            + ", W:" + west + ", \nSM:" + smell
            + arrowInfo;
  }

  @Override
  public int getNorth() {
    return this.north;
  }

  @Override
  public int getWest() {
    return this.west;
  }

  @Override
  public int getSouth() {
    return this.south;
  }

  @Override
  public int getEast() {
    return this.east;
  }

  @Override
  public void increaseSmell() {
    if (this.smell == Smell.Fresh) {
      this.smell = Smell.LessPungent;
    }
    else if (this.smell == Smell.LessPungent) {
      this.smell = Smell.Pungent;
    }
  }

  @Override
  public boolean checkMonster() {
    return false;
  }

  @Override
  public boolean checkDir(String dir) {

    if (dir.equalsIgnoreCase("n")) {
      return this.north == 1;
    }
    if (dir.equalsIgnoreCase("e")) {
      return this.east == 1;
    }
    if (dir.equalsIgnoreCase("s")) {
      return this.south == 1;
    }
    if (dir.equalsIgnoreCase("w")) {
      return this.west == 1;
    }

    return false;
  }

  @Override
  public void hitMonster() {
    //default
  }

  @Override
  public String displayMonster() {
    return "";
  }

  @Override
  public String getSmell() {
    return this.smell.toString();
  }

  @Override
  public void setArrow(int qty) {
    if (this.arrow <= 0 && qty < 0) {
      throw new IllegalStateException("No arrow here");
    }
    else {
      this.arrow += qty;
    }
  }

  @Override
  public int getArrow() {
    return this.arrow;
  }

  @Override
  public MonsterStatus getMonsterStatus() {
    return null;
  }

}
