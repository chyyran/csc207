package edu.toronto.cs.chanronn.csc207.assignment2.path.impl.switching;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.SwitchPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/**
 * Represents a {@link SwitchPath} that goes from South to North, or curves from the South to the
 * East.
 */
public final class SNEPath extends SwitchPath {

  /**
   * Initializes an SNEPath.
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public SNEPath(Map map) {
    super(Direction.SOUTH, Direction.EAST, 90, map);
    this.drawingPoints =
        new PointCollection(new Point(0.5, 0), new Point(0.5, 1.0), new Point(0.5, 0.5));
  }

  public String toString() {
    return "SNEPath";
  }
}
