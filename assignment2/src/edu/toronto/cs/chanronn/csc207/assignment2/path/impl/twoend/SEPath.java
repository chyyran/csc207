package edu.toronto.cs.chanronn.csc207.assignment2.path.impl.twoend;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.CornerPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/** Represents a {@link CornerPath} that curves from South to East. */
public final class SEPath extends CornerPath {

  /**
   * Initializes an SEPath.
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public SEPath(Map map) {
    super(Direction.SOUTH, Direction.EAST, 90, map);
    this.drawingPoints = new PointCollection(new Point(0.5, 0.5));
  }

  public String toString() {
    return "SEPath";
  }
}
