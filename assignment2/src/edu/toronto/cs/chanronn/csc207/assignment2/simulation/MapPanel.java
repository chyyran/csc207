package edu.toronto.cs.chanronn.csc207.assignment2.simulation;

import edu.toronto.cs.chanronn.csc207.assignment2.path.abstracts.Path;

import java.awt.*;

/** The Panel where the {@link Path}s and simulation grid is drawn. */
final class MapPanel extends Panel {

  /** The insets of the panel to draw the paths in. */
  private final Insets insets = new Insets(10, 10, 10, 10);

  /**
   * Add the given {@link Path[][]} array to the drawing Panel
   *
   * @param paths The array to draw.
   */
  public void addToPanel(Path[][] paths) {
    setLayout(new GridLayout(paths.length, paths[0].length, 0, 0));
    for (Path[] pathRow : paths) {
      for (Path path : pathRow) {
        path.setBackground(this.getBackground());
        add("", path);
      }
    }
  }

  public void paint(Graphics g) {
    update(g);
  }

  public Insets getInsets() {
    return insets;
  }

  public void update(Graphics g) {

    // Get my width and height.
    int w = getBounds().width;
    int h = getBounds().height;

    g.setColor(Color.white);
    g.fillRect(0, 0, w, h);
  }
}
