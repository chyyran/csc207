package edu.toronto.cs.chanronn.csc207.assignment2.primitives; // A direction; one of 'north',
                                                               // 'south', 'east' and 'west'.

/** Represents one of the four cardinal directions. */
public enum Direction {

  // Define the directions in an API-compatible way

  /** Represents the North or upwards direction. */
  NORTH("north"),
  /** Represents the South or downwards direction. */
  SOUTH("south"),
  /** Represents the East or rightwards direction. */
  EAST("east"),
  /** Represents the West or leftwards direction. */
  WEST("west");

  // We define the opposites in this static block.
  // We can not define opposites in the constructor due to circular reference,
  // but this is a common pattern that maintains encapsulation and
  // immutability.
  static {
    NORTH.opposite = Direction.SOUTH;
    SOUTH.opposite = Direction.NORTH;
    EAST.opposite = Direction.WEST;
    WEST.opposite = Direction.EAST;
  }

  /** The internal string representation of the direction. */
  private final String directionString;
  /** The Direction that represents the opposite of this Direction. */
  private Direction opposite;

  /**
   * Initialize a Direction enumeration with the given string representation of the direction.
   *
   * @param direction The string representation of the given direction.
   */
  Direction(String direction) {
    this.directionString = direction;
  }

  /**
   * Checks if this Direction and another represent the same cardinal direction.
   *
   * @param d The Direction to compare.
   * @return Whether or not this Direction and another represent the same cardinal direction.
   */
  public final boolean equals(Direction d) {
    return d.equals(this.directionString);
  }

  /**
   * Checks if the given string is equal to the string representation of this Direction. This is a
   * case-sensitive comparison, and the given string should be in all lowercase.
   *
   * @param s The string to compare with.
   * @return Whether or not the given string is equal to the string representation of this
   *     Direction.
   */
  private boolean equals(String s) {
    return s.equals(this.directionString);
  }

  @Override
  public final String toString() {
    return this.directionString;
  }

  /**
   * Returns the direction value of the opposite direction.
   *
   * @return The value representing the opposite of this direction.
   */
  public final Direction opposite() {
    return this.opposite;
  }
}
