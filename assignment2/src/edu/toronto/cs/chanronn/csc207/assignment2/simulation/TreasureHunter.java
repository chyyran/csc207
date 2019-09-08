package edu.toronto.cs.chanronn.csc207.assignment2.simulation;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.Path;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/** Represents a TreasureHunter. This class keeps track of each players score. */
public class TreasureHunter {

  /** The color of this TreasureHunter when drawn */
  private final Color color = Color.blue;
  /** The numeric ID of this TreasureHunter */
  private final int id;
  /** The score, or number of treasures this TreasureHunter has collected. */
  private int score = 0;
  /** The {@link Path} this TreasureHunter is currently occupying */
  private Path currentPath;

  /** The {@link Direction} by which this TreasureHunter entered the current {@link Path} */
  private Direction direction;

  /**
   * Initializes a TreasureHunter with the given ID.
   *
   * @param id The ID of the TreasureHunter
   */
  public TreasureHunter(int id) {
    this.id = id;
  }

  /**
   * Set the direction this TreasureHunter is moving in.
   *
   * @param direction The new direction this TreasureHunter will move.
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  /**
   * Set the path this TreasureHunter will appear on.
   *
   * @param path The new Path this TreasureHunter will appear.
   */
  public void setPath(Path path) {
    currentPath = path;
  }

  /** Move the TreasureHunter to the next {@link Path} in its current direction. */
  public void move() {

    Direction exitDirection = currentPath.getExitDirection(direction);

    Direction nextDir = exitDirection.opposite();
    Path nextPath = currentPath.getNeighbouringPath(direction);
    nextPath.enter(this);
    direction = nextDir;

    currentPath.leave();
    currentPath = nextPath;

    // We have to call this here rather than within currentPath.enter()
    // because otherwise the wrong Path is used...
    currentPath.repaint();
  }

  /**
   * Draw the TreasureHunter in its current position on the given graphics context.
   *
   * @param g The context to draw this TreasureHunter on.
   */
  public void draw(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Rectangle b = currentPath.getBounds();

    double width = b.width;
    double height = b.height;

    Ellipse2D circle = new Ellipse2D.Double(width / 3, height / 3, width / 2, height / 2);

    g2.setColor(color);
    g2.fill(circle);
    g2.setStroke(new BasicStroke(2));
    g2.draw(circle);

    g2.setColor(Color.black);
    g2.setStroke(new BasicStroke(5));
    g2.setFont(new Font("default", Font.BOLD, 16));
    g2.drawString(Integer.toString(id), (int) width / 2, (int) height / 2);
  }

  public String toString() {
    return "TreasureHunter " + id;
  }

  /** @return The score of this {@link TreasureHunter}. */
  public int getScore() {
    return score;
  }

  /**
   * Sets the score of this {@link TreasureHunter}.
   *
   * @param score The new score of the treasure hunter.
   */
  public void setScore(int score) {
    this.score = score;
  }
}
