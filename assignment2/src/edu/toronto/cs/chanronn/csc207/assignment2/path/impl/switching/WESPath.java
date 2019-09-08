package edu.toronto.cs.chanronn.csc207.assignment2.path.impl.switching;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.SwitchPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/**
 * Represents a {@link SwitchPath} that goes either from West to East, or curves from West to South.
 */
public final class WESPath extends SwitchPath {

  /**
   * Initializes a WESPath.
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public WESPath(Map map) {
    super(Direction.WEST, Direction.SOUTH, 0, map);
    this.drawingPoints =
        new PointCollection(new Point(0.0, 0.5), new Point(1.0, 0.5), new Point(-0.5, 0.5));
  }

  public String toString() {
    return "WESPath";
  }
}
