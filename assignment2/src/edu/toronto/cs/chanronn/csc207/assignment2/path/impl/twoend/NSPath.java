package edu.toronto.cs.chanronn.csc207.assignment2.path.impl.twoend;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.StraightPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/**
 * Represents a {@link StraightPath} in the vertical direction, that is, it goes from North to
 * South.
 */
public final class NSPath extends StraightPath {

  /** @param map The {@link Map} this Path is drawn on. */
  public NSPath(Map map) {
    super(Direction.NORTH, map);
    this.drawingPoints = new PointCollection(new Point(0.5, 0), new Point(0.5, 1));
  }

  public String toString() {
    return "NSPath";
  }
}
