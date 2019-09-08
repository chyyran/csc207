package edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts;

import edu.toronto.cs.chanronn.csc207.assignment2.path.impl.EmptyPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.PointCollection;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.TreasureHunter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

/**
 * Represents a Path that can be drawn on a {@link Map} for a {@link TreasureHunter} to travel
 * through. The Path handles its own drawing routines and provides the Direction and neighbouring
 * Path for a {@link TreasureHunter} travelling through it.
 */
public abstract class Path extends Canvas {

  /** The Map frame this Path is drawn on. */
  private final Map theMap;
  /** Whether or not this Path has a treasure drawn on it. */
  public boolean hasTreasure;
  /** The collection of points that represents the lines used to draw the points. * */
  protected PointCollection drawingPoints;
  /** The mapping of a {@link Direction} to a Path that neighbours this Path in that direction. */
  protected final HashMap<Direction, Path> neighbouringPaths;
  /** The color this Path will be drawn in */
  protected Color color;
  /** Whether or not this Path is isOccupied with a TreasureHunter */
  private boolean isOccupied;
  /** If this Path is isOccupied, the TreasureHunter on this path. */
  private TreasureHunter currentTreasureHunter;

  /**
   * Initializes a Path.
   *
   * @param map The Map this Path is to be drawn on.
   */
  protected Path(Map map) {
    this.neighbouringPaths = new HashMap<>();
    isOccupied = false;
    theMap = map;
  }

  /** @return Whether or not this Path is isOccupied. */
  boolean isOccupied() {
    return isOccupied;
  }

  /**
   * Draw this Path on the given drawing context.
   *
   * @param g The drawing context to draw this Path on.
   */
  protected void draw(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    Rectangle b = getBounds();

    g2.setStroke(new BasicStroke(1));
    g2.setColor(Color.LIGHT_GRAY);
    g2.drawRect(0, 0, b.width - 1, b.height - 1);
    g2.setStroke(new BasicStroke(12));

    if (isOccupied) {
      currentTreasureHunter.draw(g2);
    }

    if (hasTreasure) {
      Ellipse2D circle = new Ellipse2D.Double(b.width / 3, b.height / 3, b.width / 2, b.height / 2);
      g2.setColor(Color.YELLOW);
      g2.fill(circle);
    }
  }

  /**
   * Register that the given TreasureHunter has arrived at this Path on the map, for drawing
   * purposes, and update the score and treasure if there was treasure on this Path.
   *
   * @param treasureHunter The TreasureHunter to update score and drawing properties for.
   */
  public void enter(TreasureHunter treasureHunter) {
    isOccupied = true;
    currentTreasureHunter = treasureHunter;
    if (hasTreasure) {
      currentTreasureHunter.setScore(currentTreasureHunter.getScore() + 1);
      theMap.updateStatusBar();
      hasTreasure = false;

      // Make sure we spawn the treasure after otherwise if the treasure lands on the same place
      // it will not redraw correctly.
      theMap.spawnTreasure();
    }
  }

  /** Mark that there are no TreasureHunters to be drawn on this path */
  public void leave() {
    isOccupied = false;
    repaint();
  }

  public void paint(Graphics g) {
    draw(g);
  }

  /**
   * Checks if this Path can be exited from the given direction. The default implementation relies
   * on Paths being added to the {@link #neighbouringPaths} HashMap by checking if the map contains
   * the key of the given direction.
   *
   * <p>This implementation should be good for most use cases, but can be overridden as an
   * implementation if the getExitDirection directions are always known at compile-time.
   *
   * @param direction The direction to be exited from.
   * @return Whether or not this Path can be exited from the given direction.
   * @see #neighbouringPaths
   * @see CornerPath
   * @see EmptyPath
   */
  public boolean exitOK(Direction direction) {
    return neighbouringPaths.containsKey(direction);
  }

  /**
   * Registers a neighbouring Path in the given direction. Used when determining the next Path in a
   * given direction for this Path.
   *
   * @param neighbour The neighbouring Path.
   * @param direction The direction relative to this Path the neighbour is located at.
   */
  public abstract void register(Path neighbour, Direction direction);

  /**
   * Removes the neighbouring Path in the given direction. If there are no existing neighbours, does
   * nothing.
   *
   * @param direction THe direction relative to this Path where the neighbour to be removed is
   *     located at.
   */
  public abstract void unRegister(Direction direction);

  /**
   * Given the {@link Direction} a TreasureHunter might have entered this Path through, returns the
   * Direction which the TreasureHunter will exit from.
   *
   * @param entranceDirection The direction from which the TreasureHunter entered this Path through.
   * @return The {@link Direction} through which a TreasureHunter will exit this Path from.
   */
  public abstract Direction getExitDirection(Direction entranceDirection);

  /**
   * Given a {@link Direction} relative to this Path, gets the Path that neighbours this Path in the
   * direction. Returns null if no paths have been registered as neighbouring to this Path in the
   * given direction.
   *
   * @param adjacentDirection The {@link Direction} from which to search for a neighbouring Path.
   * @return The Path that neighbours this Path in the given {@link Direction}, or null if no such
   *     Path is registered.
   * @see #register(Path, Direction)
   */
  public abstract Path getNeighbouringPath(Direction adjacentDirection);

  public String toString() {
    return "Path";
  }
}
