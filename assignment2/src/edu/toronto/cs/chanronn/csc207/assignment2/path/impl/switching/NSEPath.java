package edu.toronto.cs.chanronn.csc207.assignment2.path.impl.switching;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.SwitchPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/**
 * Represents a {@link SwitchPath} that goes from North to South or curves from North to the East.
 */
public final class NSEPath extends SwitchPath {

  /**
   * Initializes an NSEPath.
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public NSEPath(Map map) {
    super(Direction.NORTH, Direction.EAST, 180, map);
    this.drawingPoints =
        new PointCollection(new Point(0.5, 0), new Point(0.5, 1.0), new Point(0.5, -0.5));
  }

  public String toString() {
    return "NSEPath";
  }
}
