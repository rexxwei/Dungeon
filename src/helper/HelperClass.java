package helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Returns the damage for different weapons.
 *
 */
public class HelperClass {

  /**
   * Returns the number between low and upper bound (including the boundary).
   *
   * @return the value of in the range
   */
  public static int randomNumber(int lowBound, int upBound) {
    // create Random object
    Random random = new Random();

    int ranNumber;

    do  {
      // generate random number from 0 to 100
      ranNumber = random.nextInt(10000);
    }
    while ((ranNumber < lowBound) || (ranNumber > upBound));
    return ranNumber;
  }

  /**
   * Returns the damage for different weapons.
   *
   * @return the value of the damage
   */
  public static float randomFloat() {
    // create Random object
    Random random = new Random();

    int randomInt;

    do  {
      // generate random number from 0 to 100
      randomInt = random.nextInt(80);
    }
    while ((randomInt < 20) || (randomInt > 70));

    float res = randomInt * 0.01f;

    return res;
  }

  /**
   * Returns the damage for different weapons.
   *
   * @return the value of the damage
   */
  public static int randomOne() {
    // create Random between +/-1
    Random random = new Random();

    int randomNumber;
    randomNumber = random.nextInt(50);
    if (randomNumber % 2 == 0) {
      return 1;

    }
    return -1;
  }


  /**
   * Returns 0 or 1 randomly.
   *
   * @return the 0 or 1
   */
  public static int randomOneZero() {
    // create Random between +/-1
    Random random = new Random();

    int randomNumber;
    randomNumber = random.nextInt(50);
    if (randomNumber % 2 == 0) {
      return 0;

    }
    return 1;
  }

  /**
   * Returns the damage for different weapons.
   *
   * @return the value of the damage
   */
  public static int randomID() {
    // create Random object
    Random random = new Random();

    int rNumber;
    do  {
      // generate random number from 1000 to 9999
      rNumber = random.nextInt(10000);
    }
    while ((rNumber < 1000) || (rNumber > 9999));
    return rNumber;
  }

  private static Integer getRandomElement(Set<Integer> set) {

    Random random = new Random();
    // Generate a random number using nextInt
    // method of the Random class.
    int randomNumber = random.nextInt(set.size());

    Iterator<Integer> iterator = set.iterator();
    int currentIndex = 0;
    Integer randomElement = null;

    // iterate the HashSet
    while (iterator.hasNext()) {
      randomElement = iterator.next();
      // if current index is equal to random number
      if (currentIndex == randomNumber) {
        return randomElement;
      }
      // increase the current index
      currentIndex++;
    }
    return randomElement;
  }

  /**
   * Method of getRandomSet.
   *
   * @param set the pass in set
   * @param qty number of elements
   * @return the set contains result
   */
  public static Set<Integer> getRandomSet(Set<Integer> set, int qty) {
    Set<Integer> newSet = new HashSet<>();
    while (newSet.size() < qty) {
      newSet.add(getRandomElement(set));
    }
    return newSet;
  }


}
