package edu.toronto.cs.chanronn.csc207.assignment2.simulation; // Simulate people running around a
                                                               // path.

import edu.toronto.cs.chanronn.csc207.assignment2.path.PathFactory;
import edu.toronto.cs.chanronn.csc207.assignment2.path.impl.CrossPath;
import edu.toronto.cs.chanronn.csc207.assignment2.path.impl.switching.*;
import edu.toronto.cs.chanronn.csc207.assignment2.path.impl.twoend.*;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.GridLocation;

import java.awt.*;

/**
 * The entry point of the simulation. Contains all the methods and instance variable necessary to
 * keep track of and run the island simulation.
 */
class IslandSimulation extends Frame {

  /**
   * The amount of {@link TreasureHunterRunnable} created using {@link #createTreasureHunter(int)}
   */
  private int treasureHunterCount = 0;

  /**
   * The main entry point of the applet.
   *
   * @param args The commandline arguments passed to the applet.
   */
  public static void main(String[] args) {

    IslandSimulation island = new IslandSimulation();
    island.beginSimulation();
  }

  /**
   * Creates and sets the default parameters for a {@link Map} UI Frame.
   *
   * @return A {@link Map} UI frame set up with default parameters.
   */
  private static Map buildMapComponent() {
    Map map = new Map(defaultPathLayout());
    map.setSize(540, 400);
    map.setLocation(0, 0);
    map.setBackground(Color.white);
    map.setVisible(true);
    return map;
  }

  /**
   * Declares and returns a Path layout for a {@link Map}.
   *
   * @return A Path layout for the {@link Map} in the form of a {@link PathFactory} with declared
   *     paths.
   */
  private static PathFactory defaultPathLayout() {
    PathFactory pathFactory = new PathFactory();
    pathFactory.declarePath(SEPath.class, 0, 0);
    pathFactory.declarePath(EWPath.class, 0, 1);
    pathFactory.declarePath(SWPath.class, 0, 2);

    pathFactory.declarePath(NSPath.class, 1, 0);
    pathFactory.declarePath(NSPath.class, 1, 2);
    pathFactory.declarePath(SEPath.class, 1, 5);
    pathFactory.declarePath(EWPath.class, 1, 6);
    pathFactory.declarePath(SWPath.class, 1, 7);

    pathFactory.declarePath(NEPath.class, 2, 0);
    pathFactory.declarePath(EWPath.class, 2, 1);
    pathFactory.declarePath(CrossPath.class, 2, 2);
    pathFactory.declarePath(EWPath.class, 2, 3);
    pathFactory.declarePath(EWPath.class, 2, 4);
    pathFactory.declarePath(CrossPath.class, 2, 5);
    pathFactory.declarePath(SWPath.class, 2, 6);
    pathFactory.declarePath(NSPath.class, 2, 7);

    pathFactory.declarePath(SEPath.class, 3, 1);
    pathFactory.declarePath(CrossPath.class, 3, 2);
    pathFactory.declarePath(EWSPath.class, 3, 3);
    pathFactory.declarePath(SWPath.class, 3, 4);
    pathFactory.declarePath(NEPath.class, 3, 5);
    pathFactory.declarePath(NWPath.class, 3, 6);
    pathFactory.declarePath(NSPath.class, 3, 7);

    pathFactory.declarePath(NEPath.class, 4, 1);
    pathFactory.declarePath(WENPath.class, 4, 2);
    pathFactory.declarePath(SNWPath.class, 4, 3);
    pathFactory.declarePath(NEPath.class, 4, 4);
    pathFactory.declarePath(WESPath.class, 4, 5);
    pathFactory.declarePath(EWPath.class, 4, 6);
    pathFactory.declarePath(NWPath.class, 4, 7);

    pathFactory.declarePath(NWPath.class, 5, 5);
    pathFactory.declarePath(EWPath.class, 5, 4);


    /* Begin Additional Path Squares (Step N) */
    pathFactory.declarePath(NSEPath.class, 5, 3);
    pathFactory.declarePath(SNWPath.class, 5, 5);

    pathFactory.declarePath(NSPath.class, 6, 3);
    pathFactory.declarePath(NSPath.class, 6, 5);

    pathFactory.declarePath(SEPath.class, 7, 2);
    pathFactory.declarePath(CrossPath.class, 7, 3);
    pathFactory.declarePath(SWPath.class, 7, 4);
    pathFactory.declarePath(NSPath.class, 7, 5);

    pathFactory.declarePath(NSPath.class, 8, 2);
    pathFactory.declarePath(NEPath.class, 8, 3);
    pathFactory.declarePath(NWPath.class, 8, 4);
    pathFactory.declarePath(NSPath.class, 8, 5);

    pathFactory.declarePath(NEPath.class, 9, 2);
    pathFactory.declarePath(EWPath.class, 9, 3);
    pathFactory.declarePath(EWPath.class, 9, 4);
    pathFactory.declarePath(NWPath.class, 9, 5);
    pathFactory.declarePath(NWPath.class, 9, 5);
    /* End Additional Path Squares */
    return pathFactory;
  }

  /** Begin the simulation by building the UI and TreasureHunter runnables. */
  private void beginSimulation() {
    Map map = IslandSimulation.buildMapComponent();

    TreasureHunterRunnable treasureHunter1 = createTreasureHunter(620);
    TreasureHunterRunnable treasureHunter2 = createTreasureHunter(350);

    map.addTreasureHunterRunnable(Direction.EAST, new GridLocation(2, 2), treasureHunter1);
    map.addTreasureHunterRunnable(Direction.SOUTH, new GridLocation(1, 5), treasureHunter2);
  }

  /**
   * Creates a {@link TreasureHunterRunnable}
   *
   * @param initialDelay The initial delay between updates of the {@link TreasureHunterRunnable}.
   * @return A new {@link TreasureHunterRunnable} indexed by the amount created previously.
   * @see #treasureHunterCount
   */
  private TreasureHunterRunnable createTreasureHunter(int initialDelay) {
    // prefix notation automatically increments the counter.
    TreasureHunterRunnable treasureHunter = new TreasureHunterRunnable(++treasureHunterCount);
    treasureHunter.setDelay(initialDelay);
    return treasureHunter;
  }
}
