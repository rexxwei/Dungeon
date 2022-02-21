package dungeon;

import static helper.HelperClass.randomID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;


/**
 * Class represent the player object.
 */
public class ThePlayer implements Player {
  int id;
  int position;
  int rowPos;
  int colPos;
  int arrow;
  int[] gemList;
  String pathRecord;
  HashSet<Integer> history;

  /**
   * The default constructor of Player instance.
   */
  public ThePlayer() {
    this.id = randomID();
    this.pathRecord = String.valueOf(position);
    this.history = new HashSet<>();
    this.gemList = new int[3]; //1-Diamond,2-Ruby,3-Sapphire
    this.arrow = 0;
  }

  @Override
  public void refreshPlayer() {
    this.history.clear();
    this.arrow = 0;
    this.gemList = new int[3];

  }

  @Override
  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public void setHistory(int location) {
    this.history.add(location);
  }

  @Override
  public boolean checkHistory(int location) {
    return this.history.contains(location);
  }

  @Override
  public int getLocation() {
    return position;
  }

  @Override
  public HashSet<Integer> getHistory() {
    return this.history;
  }

  @Override
  public void setPlayerCoor(int row, int col) {
    this.rowPos = row;
    this.colPos = col;
  }

  @Override
  public String toString() {

    return "Player @(" + rowPos + "," + colPos
            + ") \nArrow:" + arrow
            + " D: " + gemList[0]
            + " R: " + gemList[1]
            + " S: " + gemList[2];

  }

  @Override
  public void pickGem(Location location) {

    // if it's cave
    if (location instanceof Cave) {
      ArrayList<Treasure> gems = new ArrayList<Treasure>();
      gems = location.getGemList();
      if (gems != null) {
        for (Treasure t : gems) {
          if (Objects.equals(t.toString(), "Diamond")) {
            gemList[0] += 1;
          }
          if (Objects.equals(t.toString(), "Ruby")) {
            gemList[1] += 1;
          }
          if (Objects.equals(t.toString(), "Sapphire")) {
            gemList[2] += 1;
          }
        }
      }
      else {
        throw new IllegalArgumentException("No treasure here");
      }

      // wipe out the gem
      location.cleanGem();
    }
    else {
      throw new IllegalArgumentException("Not a cave");
    }
  }

  @Override
  public void pickArrow(Location id) {
    if (id.getArrow() > 0) {
      //increase player's arrow number
      this.arrow += 1;
    }
  }


  @Override
  public int inquireRowIndex() {
    return this.rowPos;
  }

  @Override
  public int inquireColIndex() {
    return this.colPos;
  }

  @Override
  public int getArrowQty() {
    return this.arrow;
  }

  @Override
  public int setArrowQty(int i) {
    this.arrow += i;
    return this.arrow;
  }

  @Override
  public int getGemQty() {
    System.out.println("Gem qty: " + Arrays.stream(gemList).sum());
    return Arrays.stream(gemList).sum();
  }

  @Override
  public void deductTreasure() {
    int cost = 4;
    while (cost > 0) {
      //deduct treasure
      for (int i = 0; i < 3; i++) {
        if (gemList[i] > cost) {
          gemList[i] -= cost;
          cost = 0;
        }
        else {
          cost -= gemList[i];
          gemList[i] = 0;
        }
      }
    }
  }

}
