package edu.toronto.cs.chanronn.csc207.assignment2.simulation;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/** Manages updating a {@link TreasureHunter} every tick on the Map. */
class TreasureHunterRunnable implements Runnable {

  /** The {@link TreasureHunter} this runnable will manipulate on the board. */
  private final TreasureHunter treasureHunter;
  /** Lock object for thread synchronization purposes. */
  private final Object lock = new Object();
  /**
   * The scheduler used to repeat the action of moving the {@link #treasureHunter}
   *
   * @see #refreshMapTask()
   */
  private final ScheduledExecutorService runTimer;
  /** The delay between each movement of the {@link #treasureHunter}. */
  private int delay;
  /**
   * Whether or not the action of moving the {@link #treasureHunter} is paused or active.
   *
   * @see #suspend()
   * @see #resume()
   */
  private boolean isPaused;
  /**
   * Whether or not the action of moving the {@link #treasureHunter} is running. This is set once by
   * {@link #run()}, and once set to stop using {@link #stop()} can not be resumed.
   */
  private boolean isRunning;
  /**
   * The scheduled task that is repeating the action of moving the {@link #treasureHunter}.
   *
   * @see #refreshMapTask()
   */
  private ScheduledFuture<?> mapTask;

  /**
   * Initializes an instance of TreasureHunterRunnable.
   *
   * @param id The ID of the TreasureHunterRunnable
   */
  public TreasureHunterRunnable(int id) {
    treasureHunter = new TreasureHunter(id);
    runTimer = Executors.newScheduledThreadPool(1);
  }

  /** Halves the delay between each update of the TreasureHunter. */
  public void accelerateALot() {
    setDelay(getDelay() / 2);
  }

  /** Doubles the delay between each update of the TreasureHunter. */
  public void decelerateALot() {
    setDelay(getDelay() * 2);
  }

  /**
   * Subtracts 20 milliseconds from the delay between each update of the TreasureHunter. The delay
   * can not be less than 1 millisecond.
   */
  public void accelerate() {
    setDelay(getDelay() - 20);
  }

  /** Adds 20 milliseconds from to delay between each update of the TreasureHunter. */
  public void decelerate() {
    setDelay(getDelay() + 20);
  }

  /** Permanently stop updating the TreasureHunter. */
  public void stop() {
    this.isRunning = false;
  }

  /** Temporarily pause updates of the TreasureHunter */
  public void suspend() {
    this.isPaused = true;
  }

  /** Resume updates of the TreasureHunter if previously paused. */
  public void resume() {
    this.isPaused = false;
  }

  /**
   * Called once every {@link #delay} milliseconds if started, the runnable that updates the
   * treasureHunter.
   */
  private void mapTaskRunnable() {
    // We need to acquire a lock before modifying treasureHunter
    // as we can not predict the thread the scheduled task will run on.
    synchronized (lock) {
      if (!this.isPaused) {
        treasureHunter.move();
      }
    }
  }

  /**
   * Schedule or re-schedules the {@link #mapTask} with the current {@link #delay}, if the
   * TreasureHunterRunnable is marked as running.
   *
   * @see #isRunning
   */
  private void refreshMapTask() {
    if (this.isRunning) {
      if (this.mapTask != null) {
        this.mapTask.cancel(true);
      }
      this.mapTask =
          this.runTimer.scheduleAtFixedRate(this::mapTaskRunnable, 0, delay, TimeUnit.MILLISECONDS);
    }
  }

  /** Begin updating the TreasureHunter. */
  public void run() {
    this.isRunning = true;
    this.refreshMapTask();
  }

  /**
   * Private accessor to get the delay between updates of the treasure hunter.
   *
   * @return The delay between updates of the treasure hunter.
   */
  private int getDelay() {
    return delay;
  }

  /**
   * Change the delay between updates of the treasure hunter. Changing the delay will automatically
   * reschedule the {@link #mapTask} to run with the new delay.
   *
   * <p>The longer the delay, the less frequent the {@link TreasureHunter} will move on the Map, and
   * the slower it will move, and vice versa for a shorter delay.
   *
   * @param delay The new delay to set.
   */
  public void setDelay(int delay) {
    // The period can not be less than or equal to 0, so we take the minimum delay of 1 if delay is
    // less than 0.
    this.delay = Math.max(1, Math.min(delay, Integer.MAX_VALUE));
    this.refreshMapTask();
  }

  /** @return The {@link TreasureHunter} this runnable will manipulate on the board. */
  public TreasureHunter getTreasureHunter() {
    return treasureHunter;
  }
}
