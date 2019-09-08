package edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts; /*

                                                                   The StraightPath class.  A StraightPath object has two ends,
                                                                   which must be opposite each other.

                                                                   */

import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

import java.awt.*;

/** Represents a Path that goes straight. */
public abstract class StraightPath extends TwoEndPath {

  /**
   * Represents a Path that goes straight.
   *
   * @param end One end of the Path. The other end of the StraightPath is inferred.
   * @param map The map this Path is drawn on.
   */
  protected StraightPath(Direction end, Map map) {
    super(end, end.opposite(), map);
    color = Color.orange;
  }

  public void draw(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    Point point1 = this.drawingPoints.get(1);
    Point point2 = this.drawingPoints.get(2);

    g2.setStroke(new BasicStroke(12));
    g2.setColor(color);
    Rectangle b = getBounds();
    g2.drawLine(
        (int) (point1.getX() * b.width),
        (int) (point1.getY() * b.height),
        (int) (point2.getX() * b.width),
        (int) (point2.getY() * b.height));

    super.draw(g2);
  }

  public String toString() {
    return "StraightPath";
  }
}
