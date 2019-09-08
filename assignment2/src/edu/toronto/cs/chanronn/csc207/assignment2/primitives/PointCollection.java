package edu.toronto.cs.chanronn.csc207.assignment2.primitives;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Represents an immutable, readonly collection of {@link Point} */
public final class PointCollection {

  /** The List that stores the {@link Point}s in this PointCollection. */
  private final List<Point> backingCollection;

  /**
   * Initialize the PointCollection with the specified points.
   *
   * <p>The Points provided will be indexed in order, starting at 1.
   *
   * <p>For example, if a and b are {@link Point} objects, then {@code PointCollection p = new
   * PointCollection(a, b); assert p.get(1) == a; assert p.get(2) == b; } will compile and execute
   * without problems.
   *
   * @param points The Points to store in this Point collection.
   */
  public PointCollection(Point... points) {
    this.backingCollection = new ArrayList<>(Arrays.asList(points));
  }

  /**
   * Gets the point at the given index. Note that the index is 1-indexed and not 0-indexed!
   *
   * @param index The point at the given index. This index starts at 1.
   * @return The point stored at the given index, the origin otherwise.
   */
  public Point get(int index) {
    try {
      return this.backingCollection.get(index - 1);
    } catch (IndexOutOfBoundsException e) {
      return Point.ORIGIN;
    }
  }
}
