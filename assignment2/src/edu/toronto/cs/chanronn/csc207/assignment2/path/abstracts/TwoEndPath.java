package edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts;

import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

import java.awt.*;

/** Represents a Path which may be exited one of two {@link Direction}s. */
public abstract class TwoEndPath extends Path {

  /** One end of the TwoEndPath */
  private final Direction end1;

  /** The end corresponding to {@link #end1} of the TwoEndPath. */
  private final Direction end2;

  /**
   * Initialize a TwoEndPath.
   * @param end1 One end of the TwoEndPath.
   * @param end2 The other end of the TwoEndPath.
   * @param map The {@link Map} this Path is drawn on.
   */
  TwoEndPath(Direction end1, Direction end2, Map map) {
    super(map);
    color = Color.orange;
    this.end1 = end1;
    this.end2 = end2;
    neighbouringPaths.put(end1, null);
    neighbouringPaths.put(end2, null);
  }

  public void register(Path neighbour, Direction direction) {
    if (exitOK(direction)) {
      neighbouringPaths.put(direction, neighbour);
    }
  }

  public void unRegister(Direction direction) {
    if (exitOK(direction)) {
      neighbouringPaths.put(direction, null);
    }
  }

  public Direction getExitDirection(Direction entranceDirection) {
    if (exitOK(entranceDirection)) {
      // Simply return the direction of the end that is not the given direction.
      return entranceDirection.equals(end1) ? end2 : end1;
    }

    return null;
  }

  public Path getNeighbouringPath(Direction adjacentDirection) {
    if (exitOK(adjacentDirection)) {
      return adjacentDirection.equals(end1)
          ? neighbouringPaths.get(end2)
          : neighbouringPaths.get(end1);
    }

    return null;
  }

  public String toString() {
    return "TwoEndPath";
  }
}
