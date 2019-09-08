package edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts;

import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.TreasureHunter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Represents a Path that can switch between a straight path or a curved corner section of a path
 * when it is clicked on the {@link Map} it is drawn on. See {@link #getExitDirection(Direction)}
 * for how a {@link TreasureHunter} moves through this {@link Path} depending on which "mode" the
 * Path is currently on.
 *
 * @see #getExitDirection(Direction)
 */
public abstract class SwitchPath extends Path {

  /**
   * Drawing parameters for the angle to curve the arc at relative to {@link #startAngle} for the
   * curved Path section.
   */
  private static final int arcAngle = 90;
  /** Drawing parameters for the angle to begin drawing the arc for the curved Path section */
  private final int startAngle;
  /**
   * One of the straight ends of the SwitchPath. The straight portion of the Path is formed by this
   * end, and the end in the opposite direction.
   */
  private final Direction endStraight;

  /**
   * The Direction of the end that forms the curved, corner section of this SwitchPath along with
   * {@link #endStraight}.
   */
  private final Direction endCorner;

  /** Whether or not the straight portion of the SwitchPath is enabled. */
  private boolean goingStraight;

  /**
   * Initializes a SwitchPath.
   *
   * @param endStraight One of the straight ends of the SwitchPath. This end forms the curved
   *     section of the SwitchPath along with {@link #endCorner}.
   * @param endCorner The Direction in which the curved, corner section of this SwitchPath is
   *     created along with {@link #endStraight}. The corner wil be formed from {@link #endStraight}
   *     curving to {@link #endCorner}.
   * @param startAngle Drawing parameter for drawing the arc of the corner, the angle which to begin
   *     drawing the corner path, forming a 90 degree angle with the other end of the drawn arc.
   * @param map The {@link Map} this Path is drawn on.
   */
  protected SwitchPath(Direction endStraight, Direction endCorner, int startAngle, Map map) {
    super(map);
    color = Color.magenta;
    this.endStraight = endStraight;
    this.endCorner = endCorner;
    this.startAngle = startAngle;
    neighbouringPaths.put(endStraight, null);
    neighbouringPaths.put(endStraight.opposite(), null);
    neighbouringPaths.put(endCorner, null);
    this.registerClickListener();
  }

  /**
   * Registers a {@link MouseListener} for this AWT {@link Canvas} to handle switching between
   * curved and straight modes.
   */
  private void registerClickListener() {
    this.addMouseListener(
        new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (!isOccupied()) {
              goingStraight = !goingStraight;
              repaint();
            }
          }

          @Override
          public void mousePressed(MouseEvent e) {}

          @Override
          public void mouseReleased(MouseEvent e) {}

          @Override
          public void mouseEntered(MouseEvent e) {}

          @Override
          public void mouseExited(MouseEvent e) {}
        });
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

  /**
   * {@inheritDoc}
   *
   * <p>SwitchPath acts like a switched section of train tracks where if the entrance direction is
   * not equal to {@link #endStraight}, then the TreasureHunter must exit through the direction
   * {@link #endStraight}. Otherwise, it may exit through the direction opposite {@link
   * #endStraight} if the path is going straight, or the {@link #endCorner} direction.
   *
   * @param entranceDirection The direction from which the TreasureHunter entered this Path through.
   * @return The {@link Direction} through which a TreasureHunter will exit this Path from.
   * @see Direction#opposite()
   * @see #goingStraight
   */
  public Direction getExitDirection(Direction entranceDirection) {
    if (exitOK(entranceDirection)) {
      if (!entranceDirection.equals(endStraight)) {
        return endStraight;
      }
      if (goingStraight) {
        return endStraight.opposite();
      } else {
        return endCorner;
      }
    }
    return null;
  }

  public Path getNeighbouringPath(Direction adjacentDirection) {
    if (exitOK(adjacentDirection)) {
      if (!adjacentDirection.equals(endStraight)) {
        return neighbouringPaths.get(endStraight);
      }
      if (goingStraight) {
        return neighbouringPaths.get(endStraight.opposite());
      } else {
        return neighbouringPaths.get(endCorner);
      }
    }
    return null;
  }

  public final void draw(Graphics g) {
    Rectangle b = getBounds();

    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new BasicStroke(12));

    edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point point1 = drawingPoints.get(1);
    edu.toronto.cs.chanronn.csc207.assignment2.primitives.Point point2 = drawingPoints.get(2);
    Point point3 = drawingPoints.get(3);

    // Draw current direction of the switch darker.
    if (goingStraight) {
      g2.setColor(Color.lightGray);
      g2.drawArc(
          (int) (point3.getX() * b.width),
          (int) (point3.getY() * b.height),
          b.width,
          b.height,
          startAngle,
          arcAngle);
      g2.setColor(color);
      g2.drawLine(
          (int) (point1.getX() * b.width),
          (int) (point1.getY() * b.height),
          (int) (point2.getX() * b.width),
          (int) (point2.getY() * b.height));
    } else {
      g2.setColor(Color.lightGray);
      g2.drawLine(
          (int) (point1.getX() * b.width),
          (int) (point1.getY() * b.height),
          (int) (point2.getX() * b.width),
          (int) (point2.getY() * b.height));
      g2.setColor(color);
      g2.drawArc(
          (int) (point3.getX() * b.width),
          (int) (point3.getY() * b.height),
          b.width,
          b.height,
          startAngle,
          arcAngle);
    }

    super.draw(g);
  }

  public String toString() {
    return "SwitchPath";
  }
}
