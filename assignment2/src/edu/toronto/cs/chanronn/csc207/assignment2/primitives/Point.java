package edu.toronto.cs.chanronn.csc207.assignment2.primitives;

/** Represents a point to draw on the canvas. */
public final class Point {
  /** Represents the origin Point (0, 0) */
  public static final Point ORIGIN = new Point(0, 0);
  /** The x value, or horizontal coordinate, of the Point. */
  private final double x;
  /** The y value, or vertical coordinate, of the Point. */
  private final double y;

  /**
   * Initialize a Point with and y value.
   *
   * @param x The x value, or horizontal coordinate, of the Point.
   * @param y The y value, or vertical coordinate, of the Point.
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /** @return The x value, or horizontal coordinate, of the Point. */
  public double getX() {
    return this.x;
  }

  /** @return The y value, or vertical coordinate, of the Point. */
  public double getY() {
    return this.y;
  }
}
