package edu.toronto.cs.chanronn.csc207.assignment2.path.impl.twoend;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.StraightPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/**
 * Represents a {@link StraightPath} in the horizontal direction, that is, it goes from East to
 * West.
 */
public final class EWPath extends StraightPath {

  /**
   * Initializes an EWPath.
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public EWPath(Map map) {
    super(Direction.EAST, map);
    this.drawingPoints = new PointCollection(new Point(0, 0.5), new Point(1, 0.5));
  }

  public String toString() {
    return "EWPath";
  }
}
