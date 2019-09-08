package edu.toronto.cs.chanronn.csc207.assignment2.path.impl.twoend;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.CornerPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/** Represents a {@link CornerPath} that curves from North to East. */
public final class NEPath extends CornerPath {

  /**
   * Initializes an NEPath.
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public NEPath(Map map) {
    super(Direction.NORTH, Direction.EAST, 180, map);
    this.drawingPoints = new PointCollection(new Point(0.5, -0.5));
  }

  public String toString() {
    return "NEPath";
  }
}
