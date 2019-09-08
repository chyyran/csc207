package edu.toronto.cs.chanronn.csc207.assignment2.path.impl;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.Path;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

import java.awt.*;

/** Represents a Path that can be entered and exited from all four directions. */
public final class CrossPath extends Path {

  /**
   * Initialize a CrossPath
   *
   * @param map The {@link Map} this Path is drawn on.
   */
  public CrossPath(Map map) {
    super(map);
    color = Color.orange;
    this.drawingPoints =
        new PointCollection(
            new Point(0, 0.5), new Point(1.0, 0.5), new Point(0.5, 0), new Point(0.5, 1));
  }

  public boolean exitOK(Direction direction) {
    return true;
  }

  public void register(Path neighbour, Direction direction) {
    if (exitOK(direction)) {
      neighbouringPaths.put(direction, neighbour);
    }
  }

  public void unRegister(Direction direction) {
    if (exitOK(direction)) {
      neighbouringPaths.put(direction, null);
    }
  }

  // Given that direction is the Direction from which a TreasureHunter entered,
  // report where the TreasureHunter will getExitDirection.
  public Direction getExitDirection(Direction entranceDirection) {
    if (exitOK(entranceDirection)) {
      return entranceDirection.opposite();
    }
    return null;
  }

  // direction is the direction that I entered from, and must be one of Direction.NORTH and
  // Direction.SOUTH.
  // Return the Path at the other end.
  public Path getNeighbouringPath(Direction adjacentDirection) {
    if (exitOK(adjacentDirection)) {
      return neighbouringPaths.get(adjacentDirection.opposite());
    }

    return null;
  }

  // Redraw myself.
  public void draw(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point point1 = this.drawingPoints.get(1);
    edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point point2 = this.drawingPoints.get(2);
    edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point point3 = this.drawingPoints.get(3);
    Point point4 = this.drawingPoints.get(4);

    g2.setStroke(new BasicStroke(12));

    g.setColor(color);
    Rectangle b = getBounds();
    g.drawLine(
        (int) (point1.getX() * b.width), (int) (point1.getY() * b.height),
        (int) (point2.getX() * b.width), (int) (point2.getY() * b.height));
    g.drawLine(
        (int) (point3.getX() * b.width), (int) (point3.getY() * b.height),
        (int) (point4.getX() * b.width), (int) (point4.getY() * b.height));

    super.draw(g);
  }

  public String toString() {
    return "CrossPath";
  }
}
