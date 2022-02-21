package dungeon;

import java.util.ArrayList;

/**
 * The class to represent a cave location.
 *
 */
public class Cave implements Location {
  int index;
  int rowIndex;
  int colIndex;
  int north;
  int south;
  int east;
  int west;
  ArrayList<Treasure> treasures;
  Smell smell;
  Monster monster;
  int arrow;

  /**
   * The default constructor to create an instance of Cave.
   *
   * @param index the order of cave on the grid (oder start from 0).
   * @param rowIndex the index of row (row number)
   * @param colIndex the index of column (column number)
   * @param north indicate north is available or not. 1 is available, 0 is not.
   * @param south indicate south is available or not. 1 is available, 0 is not.
   * @param east indicate east is available or not. 1 is available, 0 is not.
   * @param west indicate west is available or not. 1 is available, 0 is not.
   */
  public Cave(int index, int rowIndex, int colIndex, int north, int south, int east, int west) {
    this.index = index;
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
    this.treasures = new ArrayList<Treasure>();
    this.smell = Smell.Fresh;
    this.arrow = 0;

  }

  @Override
  public int getIndex() {
    return this.index;
  }

  @Override
  public int getRowID() {
    return this.rowIndex;
  }

  @Override
  public int getColID() {
    return this.colIndex;
  }

  @Override
  public ArrayList<Treasure> getGemList() {
    ArrayList<Treasure> res = new ArrayList<>();
    if (gemList() == null) {
      return res;
    }
    else {
      res = this.gemList();
    }
    return this.gemList();
  }

  @Override
  public String toString() {

    String arrowInfo = "";
    if (this.arrow > 0) {
      arrowInfo = " \nArrow:" + arrow;
    }

    String gem;
    if (gemList() == null || gemList().size() == 0) {
      gem = "";
    }
    else {
      gem = gemList().toString();
    }

    String monster;
    if (this.monster == null) {
      monster = "";
    }
    else {
      if (this.getMonsterStatus() == MonsterStatus.Dead) {
        monster = ", [Dead]";
      }
      else {
        monster = ", Otyugh";
      }
    }
    return "Cave @(" + rowIndex + "," + colIndex
            + ") \nN:" + north + ", E:" + east + ", S:" + south
            + ", W:" + west + ", \nSM:" + smell + ", \n" + arrowInfo
            + "\n" + gem + monster;
  }

  /**
   * Provide the treasure(gem) list in this place.
   *
   * @return ArrayLis contain the info.
   */
  public ArrayList<Treasure> gemList() {
    return this.treasures;
  }

  /**
   * Add a gem into the original gemList.
   *
   */
  public void addGem(Treasure gem) {
    this.treasures.add(gem);
  }

  /**
   * Wipe out the gemList after being collected.
   *
   */
  public void cleanGem() {
    this.treasures = null;
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


  /**
   * Add monster into a cave.
   */
  public void setMonster() {
    this.monster = new Otyugh();
    this.smell = Smell.Stinky;

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
    return (this.monster != null) ;
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
    this.monster.getHit();
  }

  @Override
  public String displayMonster() {
    return this.monster.toString();
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
    return this.monster.getStatus();
  }

}
