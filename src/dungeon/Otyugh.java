package dungeon;

import static helper.HelperClass.randomID;

/**
 * The class implement Monster interface.
 *
 */
public class Otyugh implements Monster {

  int id;
  MonsterStatus status;

  /**
   * The default constructor of an Otyugh object.
   *
   */
  public Otyugh() {
    this.id = randomID();
    this.status = MonsterStatus.Normal;

  }

  @Override
  public MonsterStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "Otyugh {" + status + '}';
  }

  @Override
  public MonsterStatus getHit() {
    if (this.status == MonsterStatus.Normal) {
      this.status = MonsterStatus.Injured;
    } else if (this.status == MonsterStatus.Injured) {
      this.status = MonsterStatus.Dead;
    }
    return this.status;
  }

}
