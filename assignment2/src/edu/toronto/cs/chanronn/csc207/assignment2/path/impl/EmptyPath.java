package edu.toronto.cs.chanronn.csc207.assignment2.path.impl;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.Path;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/** Represents the lack of a Path on the Map. */
public final class EmptyPath extends Path {

  /**
   * Initializes an EmptyPath.
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public EmptyPath(Map map) {
    super(map);
  }

  public boolean exitOK(Direction direction) {
    return false;
  }

  public void register(Path neighbour, Direction direction) {}

  public void unRegister(Direction direction) {}

  public Direction getExitDirection(Direction entranceDirection) {
    return null;
  }

  public Path getNeighbouringPath(Direction adjacentDirection) {
    return null;
  }
}
