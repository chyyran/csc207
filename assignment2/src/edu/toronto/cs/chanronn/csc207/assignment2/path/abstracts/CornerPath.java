package edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts;

import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

import java.awt.*;

/**
 * Represents a Path which forms a corner, or curves in a 90 degree angle from the given direction.
 * The two ends of the CornerPath must not be opposite each other.
 */
public abstract class CornerPath extends TwoEndPath {

  /**
   * Drawing parameters for the angle to curve the arc at relative to {@link #startAngle} for the
   * curved Path section.
   */
  private static final int arcAngle = 90;
  /** Drawing parameters for the angle to begin drawing the arc for the curved Path section */
  private final int startAngle;

  /**
   * Initializes a CornerPath.
   *
   * @param end1 The from which to start the curved Path.
   * @param end2 The end to which the curved Path ends. This must not be opposite the endPath,
   *     otherwise the Path will have the behaviour of a {@link StraightPath} but be drawn
   *     inconsistently.
   * @param startAngle The angle from which to
   * @param map The map this Path is drawn in.
   */
  protected CornerPath(Direction end1, Direction end2, int startAngle, Map map) {
    super(end1, end2, map);
    assert !end1.equals(end2.opposite())
        : "end1 and end2 must not be opposite each other on a CornerPath.";
    this.startAngle = startAngle;
  }

  public void draw(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Point point = drawingPoints.get(1);

    g2.setStroke(new BasicStroke(12));

    g2.setColor(color);
    Rectangle b = getBounds();
    g2.drawArc(
        (int) (point.getX() * b.width),
        (int) (point.getY() * b.height),
        b.width,
        b.height,
        startAngle,
        arcAngle);

    super.draw(g);
  }

  public String toString() {
    return "CornerPath";
  }
}
