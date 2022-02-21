package dungeon;

import static helper.Grid.grid;
import static helper.HelperClass.getRandomSet;
import static helper.HelperClass.randomNumber;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The class to represent a Dungeon object.
 *
 */
public class TheDungeon implements Dungeon, Cloneable {
  int numberOfRows;
  int numberOfCols;
  int numberOfVertices;
  String size;
  int interConnectivity;
  boolean wrapping;
  float percentage;
  float difficulty;
  HashSet<String> edges;
  ArrayList<Location> location;
  int totalCave;
  int totalTunnel;

  /**
   * Default constructor to build a new Dungeon instance.
   *
   * @param rows number of rows for the new dungeon.
   * @param cols number of columns for the dungeon.
   * @param percentage the percentage of treasure in the Dungeon.
   * @param wrapping indicate the dungeon is wrapping or not.
   * @param interConnectivity indicate the interConnectivity of the dungeon.
   *
   */
  public TheDungeon(int rows, int cols, int interConnectivity,
                    float percentage, float difficulty, boolean wrapping) {
    this.numberOfRows = rows;
    this.numberOfCols = cols;
    this.numberOfVertices = getNumberOfVertices();
    this.interConnectivity = interConnectivity;
    this.wrapping = wrapping;
    this.percentage = percentage;
    this.difficulty = difficulty;
    this.size = getSize();
    this.edges = generateEdge();
    this.location = genLocations();
    //assign treasure & arrow into dungeon
    assignGems();
    assignArrow();
    //add monster into dungeon
    addMonster();

  }

  /**
   * Return the total number of vertices in the dungeon.
   *
   * @return total number of vertices in the dungeon as integer.
   */
  @Override
  public int getNumberOfVertices() {
    return this.numberOfRows * this.numberOfCols;
  }

  /**
   * Return a description of size about the dungeon.
   *
   * @return string that telling size of the dungeon.
   */
  @Override
  public String getSize() {
    return String.format("%d by %d", this.numberOfRows, this.numberOfCols);
  }


  @Override
  public void refreshDungeon() {
    //assign treasure & arrow into dungeon
    assignGems();
    assignArrow();
    //add monster into dungeon
    addMonster();
  }


  /**
   * Return number of rows in dungeon.
   *
   * @return number of rows in integer.
   */
  @Override
  public int numberOfRows() {
    return numberOfRows;
  }

  /**
   * Return number of columns in dungeon.
   *
   * @return number of columns in integer.
   */
  @Override
  public int numberOfCols() {
    return numberOfCols;
  }


  /**
   * Generate edges for the dungeon by calling other method.
   *
   * @return a set contains the edges
   */
  public HashSet<String> generateEdge() {
    return grid(numberOfRows, numberOfCols, interConnectivity, wrapping);
  }

  /**
   * display edges of the dungeon.
   *
   * @return the info as string
   */
  public String displayEdges() {
    return this.edges.toString();
  }

  /**
   * return edges of the dungeon to caller.
   *
   * @return a set contains the edges.
   */
  public HashSet<String> getEdges() {
    return this.edges;
  }

  /**
   * Generate the detail of each location.
   *
   * @return list contain the location info.
   */
  public ArrayList<Location> genLocations() {
    ArrayList<Location> locList = new ArrayList<>();
    int caveTotal = 0;
    int tunnelTotal = 0;

    for (int i = 0; i < numberOfVertices; i++) {
      // update direction for location
      int north = i - numberOfCols;
      int south = i + numberOfCols;
      // check the north edge
      int n = 0;
      if (edges.contains(String.format("%d,%d", north, i))
              || edges.contains(String.format("%d,%d", i, north))) {
        n = 1;
      }

      // check the west edge
      int w = 0;
      int west = i - 1;
      if (edges.contains(String.format("%d,%d", west, i))
              || edges.contains(String.format("%d,%d", i, west))) {
        w = 1;
      }
      else if (i % numberOfCols == 0) { // add wrapping edge for west
        west = i + numberOfCols - 1;
        //System.out.println("east: " + i + "-" + east);
        if (edges.contains(String.format("%d,%d", west, i))
                || edges.contains(String.format("%d,%d", i, west))) {
          w = 1;
        }
      }


      // check the south edge
      int s = 0;
      if (edges.contains(String.format("%d,%d", south, i))
              || edges.contains(String.format("%d,%d", i, south))) {
        s = 1;
      }
      else if ( (numberOfRows - 1) * numberOfCols <= i
              && i < numberOfRows * numberOfCols) { // add wrapping edge
        south = i % numberOfCols;
        //System.out.println("South: " + i + "-" + south);
        if (edges.contains(String.format("%d,%d", south, i))
                || edges.contains(String.format("%d,%d", i, south))) {
          s = 1;
        }
      }

      // check the east edge
      int east = i + 1;
      int e = 0;
      if (edges.contains(String.format("%d,%d", east, i))
              || edges.contains(String.format("%d,%d", i, east))) {
        e = 1;
      }
      else if (i % numberOfCols == numberOfCols - 1) { // add wrapping edge for east
        east = i - numberOfCols + 1;
        //System.out.println("east: " + i + "-" + east);
        if (edges.contains(String.format("%d,%d", east, i))
                || edges.contains(String.format("%d,%d", i, east))) {
          e = 1;
        }
      }

      // check location type
      if (n + s + e + w == 2) {
        locList.add(new Tunnel(i, i / numberOfCols, i % numberOfCols, n, s, e, w));
        tunnelTotal += 1;
      }
      else {
        locList.add(new Cave(i, i / numberOfCols, i % numberOfCols, n, s, e, w));
        caveTotal += 1;
      }
      this.totalCave = caveTotal;
      this.totalTunnel = tunnelTotal;
    }
    return locList;
  }

  /**
   * Display info of each spot in the dungeon.
   *
   * @return ArrayList contain info of all locations
   */
  public ArrayList<Location> listAllLocation() {
    //System.out.println(this.location.get(i).toString());
    return new ArrayList<>(this.location);
  }

  /**
   * Return the information of a spot identify by ID.
   *
   * @param locID the order of the location.
   * @return an object of location.
   */
  @Override
  public Location getTheSpot(int locID) {
    return this.location.get(locID);

  }

  /**
   * Assign gems into caves of the dungeon.
   */
  public void assignGems() {
    int numberOfGem = (int) (totalCave * percentage);
    int rNumber;

    //search all the cave location
    Set<Integer> caveSet = new HashSet<>();
    for (int i = 0; i < this.location.size(); i++) {
      if (this.location.get(i) instanceof Cave) {
        caveSet.add(i);
      }
    }

    //pick the caves to add monster
    Set<Integer> selectSet;
    selectSet = getRandomSet(caveSet, numberOfGem);
    //System.out.println("cave for monster: " + selectSet);

    //add monster into caves
    Integer[] caveNumber = selectSet.toArray(new Integer[selectSet.size()]);
    for (int j = 0; j < caveNumber.length; j++) {
      if (this.location.get(caveNumber[j]) instanceof Cave) {
        rNumber = randomNumber(1,4);
        switch (rNumber) {
          case 2:
            ((Cave) this.location.get(caveNumber[j])).addGem(Treasure.Diamond);
            break;
          case 3:
            ((Cave) this.location.get(caveNumber[j])).addGem(Treasure.Sapphire);
            break;
          default:
            ((Cave) this.location.get(caveNumber[j])).addGem(Treasure.Ruby);
            break;
        }
      }
    }
  }

  /**
   * Assign arrow into caves of the dungeon.
   */
  public void assignArrow() {
    int numberOfArrow = (int) (totalCave * percentage);

    //search all the location
    Set<Integer> locationSet = new HashSet<>();
    for (int i = 0; i < this.location.size(); i++) {
      //if (this.location.get(i) instanceof Cave) {
      locationSet.add(i);
      //}
    }

    //pick the location to add monster
    Set<Integer> selectSet;
    selectSet = getRandomSet(locationSet, numberOfArrow);
    //System.out.println("cave for monster: " + selectSet);

    //add arrow into location(both caves & tunnels)
    Integer[] locNumber = selectSet.toArray(new Integer[selectSet.size()]);
    for (Integer integer : locNumber) {
      this.location.get(integer).setArrow(1);

    }
  }

  /**
   * Assign gems into caves of the dungeon.
   *
   */
  @Override
  public void addMoreGems(int number) {
    int numberOfGem = number;
    int rNumber;
    int counter = 0;
    //System.out.println(numberOfGem);
    while (numberOfGem > 0) {
      //System.out.println(this.location.size());
      for (int i = 0; i < this.location.size(); i++) {
        if (numberOfGem <= 0) {
          break;
        }
        if (this.location.get(i) instanceof Cave) {
          counter += 1;
          rNumber = randomNumber(1,4);
          //System.out.println("Gem = "+rNumber);
          switch (rNumber) {
            case 2:
              ((Cave) this.location.get(i)).addGem(Treasure.Diamond);
              break;

            case 3:
              // code block
              ((Cave) this.location.get(i)).addGem(Treasure.Sapphire);
              break;

            default:
              // code block
              ((Cave) this.location.get(i)).addGem(Treasure.Ruby);
              break;
          }
          numberOfGem -= 1;
        }
      }
    }
  }

  /**
   * Return the height of the tree.
   *
   *
   */
  public void searchGems() {

    for (Location value : this.location) {
      //System.out.println(this.location.get(i).toString());
      if ((value instanceof Cave)
              && ((Cave) value).gemList() != null) {
        System.out.println(((Cave) value).gemList());
      }
    }
  }

  /**
   * Return a description of the dungeon.
   *
   * @return string description about the dungeon.
   */
  @Override
  public String toString() {
    return "Dungeon info { V: " + numberOfVertices + ", size: " + size
            + ", IC: " + interConnectivity + ", Wrapping: " + wrapping
            + ", Percentage: " + percentage + ", Caves: " + totalCave
            + ", Tunnel: " + totalTunnel + '}';
  }

  /**
   * Return a description of the dungeon.
   *
   * @return string description about the dungeon.
   */
  @Override
  public int getInterConnectivity() {
    return this.interConnectivity;
  }

  /**
   * Return a description of the dungeon.
   *
   * @return string description about the dungeon.
   */
  @Override
  public float getPercentage() {
    return this.percentage;
  }

  /**
   * Update the percentage of treasure.
   *
   */
  @Override
  public void setPercentage(float newPercentage) {
    this.percentage = this.percentage + newPercentage;

  }

  /**
   * Return the quantity of total Cave object.
   *
   * @return the number in float
   */
  @Override
  public float getCaveQty() {
    return this.totalCave;
  }

  /**
   * Return the quantity of total Tunnel object.
   *
   * @return the number in float
   */
  @Override
  public float getTunnelQty() {
    return this.totalTunnel;
  }

  /**
   * Function to print the grid on console.
   */
  public void print() {
    int rows = this.numberOfRows();
    int cols = this.numberOfCols();
    String res = "";
    String type = "";
    String vertical = "";

    for (int r = 0; r < rows; r++) {
      vertical = "";
      for (int c = 0; c < cols; c++) {

        if (this.location.get(r * rows + c) instanceof Cave) {
          type = "C";
          //type = String.valueOf(r * rows + c);
        }
        else {
          type = "T";
          //type = String.valueOf(r * rows + c);
        }

        if (this.location.get(r * rows + c).getSouth() == 1) {
          vertical = vertical + " | ";
        }
        else {
          vertical = vertical + "   ";
        }

        if (this.location.get(r * rows + c).getWest() == 1) {
          res = res + "-";
        }
        else {
          res = res + " ";
        }

        res = res + type;

        if (this.location.get(r * rows + c).getEast() == 1) {
          res = res + "-";
        }
        else {
          res = res + " ";
        }
      } // end of a line

      res = res + "\n";
      res = res + vertical + "\n";
    } // end of square
    System.out.println(res);
  } //end of print()

  /**
   * Return the status of wrapping or not.
   *
   * @return the boolean value
   */
  @Override
  public boolean getWrapping() {
    return this.wrapping;
  }

  /**
   * Set the value of wrapping.
   */
  @Override
  public void setWrapping(boolean newWrapping) {
    this.wrapping = newWrapping;
  }

  /**
   * Assign the position that have monster.
   */
  @Override
  public void addMonster() {
    int numberOfMonster = (int) (totalCave * difficulty); //test 10%

    //search all the cave location
    Set<Integer> caveSet = new HashSet<>();
    for (int i = 0; i < this.location.size(); i++) {
      if (this.location.get(i) instanceof Cave) {
        caveSet.add(i);
      }
    }

    //pick the caves to add monster
    Set<Integer> selectSet;
    selectSet = getRandomSet(caveSet, numberOfMonster);

    //add monster into caves
    Integer[] caveNumber = selectSet.toArray(new Integer[selectSet.size()]);
    for (int j = 0; j < caveNumber.length; j++) {
      if (this.location.get(caveNumber[j]) instanceof Cave) {
        ((Cave) this.location.get(caveNumber[j])).setMonster();

        //update the smell in nearby positions
        updateNearbySmell(this.location.get(caveNumber[j]).getIndex(), 2);

      }
    }

  }

  @Override
  public void updateNearbySmell(int location, int distance) {
    //get current coordinate
    int index = location;

    //search for spot within 1 position
    HashSet<Integer> onePosition = new HashSet<Integer>();
    onePosition = distanceSearch(onePosition, location, 1);
    //System.out.println(onePosition);

    //search for spot within 2 positions
    HashSet<Integer> twoPosition = new HashSet<Integer>();
    twoPosition = distanceSearch(twoPosition, location, 2);
    twoPosition.removeAll(onePosition);
    twoPosition.remove(location);
    //System.out.println(twoPosition);

    //increase smell for one position away
    for (Integer i : onePosition) {
      getTheSpot(i).increaseSmell();
      getTheSpot(i).increaseSmell();
    }

    //increase smell for two position away
    for (Integer t : twoPosition) {
      getTheSpot(t).increaseSmell();
    }

  } //end of updateNearbySmell

  @Override
  public void searchDistanceTwo(int location, int distance) {
    //get current coordinate
    int index = location;
    int co_row = inquireCoorRow(index);
    int co_col = inquireCoorCol(index);

  }

  /**
   * The method to search specified distance from a location.
   *
   * @param searchSet store the result of recursion
   * @param location the start point
   * @param distance how far from start point.
   * @return the result set contain all locations.
   */
  public HashSet<Integer> distanceSearch(HashSet<Integer> searchSet,int location, int distance) {
    //get current coordinate
    int index = location;
    int co_row = inquireCoorRow(index);
    int co_col = inquireCoorCol(index);

    //increase north
    if (getTheSpot(index).getNorth() == 1) {
      //handle wrapping and non-wrapping dungeon
      if (co_row > 0) {
        co_row -= 1;
      }
      else if (co_row == 0) {
        co_row = numberOfRows - 1;
      }
      index = this.inquireIndex(co_row,co_col);
      searchSet.add(index);
      if (distance > 1) {
        searchSet = distanceSearch(searchSet, index, distance - 1);
      }
    }
    //increase east
    index = location;
    co_row = inquireCoorRow(index);
    co_col = inquireCoorCol(index);
    if (getTheSpot(index).getEast() == 1) {
      //handle wrapping and non-wrapping dungeon
      if (co_col < numberOfCols - 1) {
        co_col += 1;
      }
      else if (co_row == numberOfCols - 1) {
        co_col = 0;
      }
      index = this.inquireIndex(co_row,co_col);
      searchSet.add(index);
      if (distance > 1) {
        searchSet = distanceSearch(searchSet, index, distance - 1);
      }
    }
    //increase south
    index = location;
    co_row = inquireCoorRow(index);
    co_col = inquireCoorCol(index);
    if (getTheSpot(index).getSouth() == 1) {
      //handle wrapping and non-wrapping dungeon
      if (co_row < numberOfRows - 1) {
        co_row += 1;
      }
      else if (co_row == numberOfRows - 1) {
        co_row = 0;
      }
      index = this.inquireIndex(co_row,co_col);
      searchSet.add(index);
      if (distance > 1) {
        searchSet = distanceSearch(searchSet, index, distance - 1);
      }
    }
    //increase west
    index = location;
    co_row = inquireCoorRow(index);
    co_col = inquireCoorCol(index);
    if (getTheSpot(index).getWest() == 1) {
      //handle wrapping and non-wrapping dungeon
      if (co_col > 0) {
        co_col -= 1;
      }
      else if (co_col == 0) {
        co_col = numberOfCols - 1;
      }
      index = this.inquireIndex(co_row,co_col);
      searchSet.add(index);
      if (distance > 1) {
        searchSet = distanceSearch(searchSet, index, distance - 1);
      }
    }
    return searchSet;
  }

  @Override
  public int inquireIndex(int coRow, int coCol) {
    return coRow * numberOfCols + coCol;
  }

  @Override
  public int[] inquireCoor(int index) {
    int[] res = new int[2];
    res[0] = index / numberOfCols;
    res[1] = index % numberOfCols;
    return res;
  }

  @Override
  public int inquireCoorRow(int index) {
    return (int) index / numberOfCols;
  }

  @Override
  public int inquireCoorCol(int index) {
    return (int) index % numberOfCols;
  }

  @Override
  public Set<String> findAllExit(int location) {
    Set<String> allExit = new HashSet<>();
    if (this.getTheSpot(location).checkDir("n")) {
      allExit.add("n");
    }
    if (this.getTheSpot(location).checkDir("e")) {
      allExit.add("e");
    }
    if (this.getTheSpot(location).checkDir("s")) {
      allExit.add("s");
    }
    if (this.getTheSpot(location).checkDir("w")) {
      allExit.add("w");
    }

    return allExit;
  }


  @Override
  public TheDungeon clone() {
    try {
      // TODO: copy mutable state here, so the clone can't change the internals of the original
      return (TheDungeon) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  @Override
  public float getDifficulty() {
    return difficulty;
  }

  @Override
  public void killMonsterAt(Location l) {
    if (l instanceof Cave) {
      l.hitMonster();
      l.hitMonster();
    }
    else {
      throw new IllegalStateException("Monster not found");
    }
  }

}
