package edu.toronto.cs.chanronn.csc207.assignment2.simulation;

import edu.toronto.cs.chanronn.csc207.assignment2.path.PathFactory;
import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.Path;
import edu.toronto.cs.chanronn.csc207.assignment2.path.impl.EmptyPath;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.Direction;
import edu.toronto.cs.chanronn.csc207.assignment2.primitives.GridLocation;

import java.awt.*;
import java.util.ArrayList;

/** The main frame that the control UI and the {@link MapPanel} are drawn on. */
public final class Map extends Frame {

  /** The default size of the Path grid for a Map. */
  private static final int DEFAULT_GRID_SIZE = 10;
  /**
   * The list of {@link TreasureHunterRunnable} objects managing one {@link TreasureHunter} each on
   * the map.
   */
  private final ArrayList<TreasureHunterRunnable> treasureHunterRunnableList;
  /**
   * The array of {@link Path[][]} objects to draw on the Map that form the grid where the {@link
   * TreasureHunterRunnable} objects can collect treasure.
   */
  private final Path[][] paths;
  /** The Panel where the {@link Path} objects are drawn on the UI. */
  private MapPanel mapPanel;
  /** The following label is used to display the scores of the TreasureHunters. */
  private Label statusLabel;

  /**
   * Initialize a new instance of the Map panel with the default grid size of 10.
   *
   * @param pathFactory The {@link PathFactory} already set up that represents the Paths on this
   *     map.
   * @see PathFactory#declarePath(Class, int, int)
   */
  public Map(PathFactory pathFactory) {
    this(pathFactory, DEFAULT_GRID_SIZE);
  }

  /**
   * Initialize a new instance of the Map panel.
   *
   * @param pathFactory The {@link PathFactory} already set up that represents the Paths on this
   *     map.
   * @param pathSize The size of the grid that the Paths will be rendered on.
   * @see PathFactory#declarePath(Class, int, int)
   */
  public Map(PathFactory pathFactory, int pathSize) {
    buildUserInterface();
    paths = pathFactory.toPathArray(this, pathSize);

    // These are just put there to see what they look like.
    //        new EWNPath(new GridLocation(6, 0), this);
    //        new EWSPath(new GridLocation(6, 1), this);
    //        new WENPath(new GridLocation(6, 2), this);
    //        new WESPath(new GridLocation(6, 3), this);
    //        new NSEPath(new GridLocation(6, 4), this);
    //        new NSWPath(new GridLocation(6, 5), this);
    //        new SNEPath(new GridLocation(6, 6), this);
    //        new SNWPath(new GridLocation(6, 7), this);
    // ------------------------------------------------------------------------------

    spawnTreasure(5, 3);
    mapPanel.setBackground(new Color(152, 251, 152));
    mapPanel.addToPanel(paths);
    treasureHunterRunnableList = new ArrayList<>();
  }

  /** Set up and initialize the AWT user interface controls for the window */
  private void buildUserInterface() {
    mapPanel = new MapPanel();
    add("Center", mapPanel);

    // Create the buttons
    Button runStopButton = new Button(RunButtonStates.RUN.toString());
    Button quitButton = new Button("Quit");
    Button accelButton = new Button("Accelerate");
    Button decelButton = new Button("Decelerate");
    Button accelLotsButton = new Button("Accelerate A Lot");
    Button decelLotsButton = new Button("Decelerate A Lot");

    // Register action listeners for each button
    accelButton.addActionListener(
        e -> treasureHunterRunnableList.forEach(TreasureHunterRunnable::accelerate));
    accelLotsButton.addActionListener(
        e -> treasureHunterRunnableList.forEach(TreasureHunterRunnable::accelerateALot));
    decelButton.addActionListener(
        e -> treasureHunterRunnableList.forEach(TreasureHunterRunnable::decelerate));
    decelLotsButton.addActionListener(
        e -> treasureHunterRunnableList.forEach(TreasureHunterRunnable::decelerateALot));

    quitButton.addActionListener(e -> System.exit(0));

    // We need to switch on valid states of the runStopButton.
    runStopButton.addActionListener(
        e -> {
          switch (RunButtonStates.valueOf(runStopButton.getLabel().toUpperCase())) {
            case RUN:
              for (TreasureHunterRunnable p : treasureHunterRunnableList) {
                new Thread(p).start();
              }
              runStopButton.setLabel(RunButtonStates.SUSPEND.toString());
              this.updateStatusBar();
              break;
            case SUSPEND:
              treasureHunterRunnableList.forEach(TreasureHunterRunnable::suspend);
              runStopButton.setLabel(RunButtonStates.RESUME.toString());
              break;
            case RESUME:
              treasureHunterRunnableList.forEach(TreasureHunterRunnable::resume);
              runStopButton.setLabel(RunButtonStates.SUSPEND.toString());
              break;
          }
        });

    // Pack the buttons into a panel.
    Panel panel2 = new Panel();
    panel2.setLayout(new GridLayout(0, 1));
    panel2.add(runStopButton);
    panel2.add(accelLotsButton);
    panel2.add(decelLotsButton);
    panel2.add(accelButton);
    panel2.add(decelButton);
    panel2.add(quitButton);
    add("East", panel2);

    statusLabel = new Label("Press 'Run' to start the simulation.");

    Panel panel3 = new Panel();
    panel3.add(statusLabel);
    add("South", panel3);

    pack();
  }

  public void paint(Graphics g) {
    update(g);
  }

  public void update(Graphics g) {
    // Repaint the map panel.
    mapPanel.repaint();
  }

  /**
   * Add a TreasureHunterRunnable to the {@link #treasureHunterRunnableList} in anticipation of
   * running the TreasureHunterRunnable during the simulation at the given location in the initial
   * direction given.
   *
   * @param treasureHunter The TreasureHunterRunnable to add.
   * @param direction The direction this TreasureHunterRunnable will begin moving in.
   * @param location The initial location this TreasureHunterRunnable will appear on the map.
   */
  public void addTreasureHunterRunnable(
      Direction direction, GridLocation location, TreasureHunterRunnable treasureHunter) {
    treasureHunterRunnableList.add(treasureHunter);
    treasureHunter.getTreasureHunter().setDirection(direction);
    treasureHunter.getTreasureHunter().setPath(paths[location.getRow()][location.getCol()]);
    paths[location.getRow()][location.getCol()].enter(treasureHunter.getTreasureHunter());
  }

  /**
   * Updates the status bar with the current score of the {@link TreasureHunterRunnable} objects
   * running on this map.
   */
  public void updateStatusBar() {
    StringBuilder statusLabelSb = new StringBuilder();
    for (int i = 0; i < treasureHunterRunnableList.size(); i++) {
      statusLabelSb
          .append("Player ")
          .append(i + 1)
          .append(": ")
          .append(treasureHunterRunnableList.get(i).getTreasureHunter().getScore());
      if (i < treasureHunterRunnableList.size() - 1) {
        statusLabelSb.append(" --- ");
      }
    }
    statusLabel.setText(statusLabelSb.toString());
    statusLabel.repaint();
  }

  /** Spawn treasure on a random connected {@link Path} on the paths this Map is drawing */
  public void spawnTreasure() {
    int row, col;
    // find suitable place for treasure to respawn
    do {
      row = (int) (Math.random() * 10);
      col = (int) (Math.random() * 10);

    } while ((paths[row][col] instanceof EmptyPath));

    spawnTreasure(row, col);
  }

  /**
   * Spawn treasure in the Path on the specified location.
   *
   * @param row The row of the Path.
   * @param col The column of the Path.
   */
  private void spawnTreasure(int row, int col) {
    paths[row][col].hasTreasure = true;
    paths[row][col].repaint();
  }

  /**
   * Represents the possible states of the button that starts, suspends, and resumes the simulation.
   */
  private enum RunButtonStates {

    /** If the button is in this state, pressing it will start the simulation. */
    RUN("Run"),

    /** If the button is in this state, pressing it will suspend the simulation. */
    SUSPEND("Suspend"),

    /** If the button is in this state, pressing it will resume the simulation. */
    RESUME("Resume");

    /** The label of the button when it is in the given state. */
    private final String label;

    /**
     * Initializes a RunButtonStats enumeration.
     *
     * @param label The label of the button when it is in the given state.
     */
    RunButtonStates(String label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return label;
    }

    /**
     * Checks for equality between the given string and the label of the enum.
     *
     * @param state The string to compare with the label.
     * @return A {@link String} and a {@link RunButtonStates} value are considered equal if the
     *     String equals the value returned by {@link RunButtonStates#toString()}
     */
    public boolean equals(String state) {
      return this.label.equals(state);
    }
  }
}
