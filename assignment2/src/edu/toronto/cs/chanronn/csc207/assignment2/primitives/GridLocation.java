package edu.toronto.cs.chanronn.csc207.assignment2.primitives;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.Path;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

/** An immutable representation an x, y location on the {@link Path[][]} array for a {@link Map}. */
public final class GridLocation {

  /** The y value, or row. */
  private final int row;

  /** The x value, or column. */
  private final int col;

  /**
   * Initializes a GridLocation with the given row and column coordinates.
   *
   * @param row The row, or y value of the location.
   * @param col The column, or x value of the location.
   */
  public GridLocation(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public String toString() {
    return (row + " " + col);
  }

  /** @return The y value, or row of this GridLocation */
  public int getRow() {
    return row;
  }

  /** @return The x value, or column of this GridLocation */
  public int getCol() {
    return col;
  }
}
