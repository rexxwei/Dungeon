package dungeon;


/**
 * The interface of location.
 *
 */
public interface Monster {

  /**
   * Return the state of the monster.
   *
   * @return The status of monster.
   */
  MonsterStatus getStatus();


  /**
   * The method to update status after being hit.
   *
   * @return updated status.
   */
  MonsterStatus getHit();

}

