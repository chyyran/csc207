package edu.toronto.cs.chanronn.csc207.assignment2.path;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.Path;
import edu.toronto.cs.chanronn.csc207.assignment2.path.impl.EmptyPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.simulation.Map;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/** A Factory that creates Path objects dynamically at runtime. */
public class PathFactory {

  /**
   * The argument types for the {@link Path} creation constructor.
   *
   * @see #initializePath(Class, int, int, Map)
   */
  private static final Class[] locatedPathArgs = new Class[] {Map.class};

  /** The list of {@link Path}s to add to the final resulting Path[][] array. */
  private final List<PathDeclaration> declaredPaths;

  /** Initializes a PathFactory. */
  public PathFactory() {
    this.declaredPaths = new ArrayList<>();
  }

  /**
   * Initializes a {@link Path} instance dynamically.
   *
   * <p>If the requested pathType does not have a constructor of type (GridLocation, Map), then will
   * return an instance of EmptyPath instead.
   *
   * @param pathType The Class of the Path to create.
   * @param row The row coordinate of the Path to create.
   * @param col The column coordinate of the Path to create.
   * @param map The Map the Path will be created for.
   * @return A new instance of the specified Path, or an EmptyPath if the pathType is invalid.
   * @see EmptyPath
   */
  private static Path initializePath(Class<? extends Path> pathType, int row, int col, Map map) {
    try {
      // Attempt to acquire the constructor Path(GridLocation, Map)
      Constructor<? extends Path> pathConstructor =
          pathType.getConstructor(PathFactory.locatedPathArgs);

      // Initialize the instance of Path with the Path(GridLocation, Map) constructor.
      return pathConstructor.newInstance(map);
    } catch (ReflectiveOperationException e) {
      // If something went wrong, such as the Path is abstract or doesn't have
      // a constructor, we'll just return a new EmptyPath.
      return new EmptyPath(map);
    }
  }

  /**
   * Creates a new {@link Path[][]} array relative to the specified map with the specified size.
   *
   * @param map The Map that the resulting {@link Path[][]} array will be for.
   * @param size The size of the {@link Path[][]} array. The same size will be used for both
   *     dimensions of the 2D array.
   * @return The new {@link Path[][]} array filled with EmptyPath instances.
   */
  private static Path[][] createEmptyPathArray(Map map, int size) {
    Path[][] paths = new Path[size][size];
    for (int row = 0; row < paths.length; row++) {
      for (int col = 0; col < paths[0].length; col++) {
        paths[row][col] = new EmptyPath(map);
      }
    }
    return paths;
  }

  /**
   * Connect two paths together in the given direction. path2 will be in the given direction from
   * path1.
   *
   * @param path1 The first Path to connect.
   * @param path2 The second Path to connect.
   * @param direction The direction that path2 is from path1.
   */
  private static void connectPaths(Path path1, Path path2, Direction direction) {
    path1.register(path2, direction);
    path2.register(path1, direction.opposite());
  }

  /**
   * If specified to test neighbouring paths, then attempts to connect path1 with path2 in the given
   * direction. Otherwise, unregister and disconnect path1 from the given direction.
   *
   * @param testNeighbouringPaths Whether or not to test neighbouring paths.
   * @param path1 The first Path to attempt to connect.
   * @param path2 The second Path to connect.
   * @param direction The direction that path2 is from path1
   * @see #connectPaths(Path, Path, Direction)
   */
  private static void registerOrUnRegister(
      boolean testNeighbouringPaths, Path path1, Path path2, Direction direction) {
    if (testNeighbouringPaths && path1 != null && path1.exitOK(direction)) {
      if (path2.exitOK(direction.opposite())) {
        connectPaths(path1, path2, direction);
      } else {
        path1.unRegister(direction);
      }
    }
  }

  /**
   * Connect the Path at the given row and column to all its neighbouring non empty paths
   *
   * @param paths The Path[][] array that the Path is contained in.
   * @param row The row of the target Path to connect to its neighbours.
   * @param col The column of the target Path to connect to its neighbours.
   */
  private static void connectPath(Path[][] paths, int row, int col) {
    Path r = paths[row][col];

    if (r != null) {
      Path rN = row > 0 ? paths[row - 1][col] : null;
      Path rS = row < paths.length - 1 ? paths[row + 1][col] : null;
      Path rE = col < paths[0].length - 1 ? paths[row][col + 1] : null;
      Path rW = col > 0 ? paths[row][col - 1] : null;

      registerOrUnRegister(row > 0, rN, r, Direction.SOUTH);
      registerOrUnRegister(row < paths.length - 1, rS, r, Direction.NORTH);
      registerOrUnRegister(col > 0, rW, r, Direction.EAST);
      registerOrUnRegister(col < paths[0].length - 1, rE, r, Direction.WEST);
    }
  }

  /**
   * Connects all the Paths in a given array to all its non Empty neighbours
   *
   * @param paths The Path[][] array to examine
   * @see #connectPath(Path[][], int, int)
   */
  private static void connectAllPaths(Path[][] paths) {
    for (int row = 0; row < paths.length; row++) {
      for (int col = 0; col < paths[0].length; col++) {
        connectPath(paths, row, col);
      }
    }
  }

  /**
   * Declare that a Path is to be created at the specified row and column. Once declared, a Path can
   * not be 'undeclared' for the current instance of PathFactory.
   *
   * @param pathType The type of the Path to create.
   * @param row The row the Path will be located at.
   * @param col The column the Path will be located at.
   */
  public void declarePath(Class<? extends Path> pathType, int row, int col) {
    this.declaredPaths.add(new PathDeclaration(pathType, row, col));
  }

  /**
   * Creates a Path[][] array using previously declared Path locations for the given map and size.
   * Each call of
   *
   * @param map The map to create this Path[][] array for.
   * @param size The size of the Path[][] array to create.
   * @return A new Path[][] array using the previous declared Path locations.
   */
  public Path[][] toPathArray(Map map, int size) {
    Path[][] paths = createEmptyPathArray(map, size);
    for (PathDeclaration declaration : this.declaredPaths) {
      try {
        paths[declaration.row][declaration.col] =
            initializePath(declaration.pathType, declaration.row, declaration.col, map);
      } catch (ArrayIndexOutOfBoundsException ignored) {

      }
    }
    connectAllPaths(paths);
    return paths;
  }

  /** Internal representation of a {@link Path} to be created. */
  private class PathDeclaration {

    /** The type of the Path to be created. */
    final Class<? extends Path> pathType;

    /** The row of the cell this Path will be placed on. */
    final int row;

    /** The column of the cell this Path will be placed on. */
    final int col;

    /**
     * Initializes a PathDeclaration
     *
     * @param pathType The type of the Path to be created.
     * @param row The row of the cell this Path will be placed on.
     * @param col The column of the cell this Path will be placed on.
     */
    PathDeclaration(Class<? extends Path> pathType, int row, int col) {
      this.pathType = pathType;
      this.row = row;
      this.col = col;
    }
  }
}
