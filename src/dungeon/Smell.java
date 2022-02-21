package dungeon;

/**
 * Enum to represent 4 status of smell in a place.
 *
 */
public enum Smell {
  // A monster in the place
  Stinky,
  // A monster one position to current place or multiple nearby
  Pungent,
  // A monster two position to current place
  LessPungent,
  // No monster is nearby
  Fresh
}
