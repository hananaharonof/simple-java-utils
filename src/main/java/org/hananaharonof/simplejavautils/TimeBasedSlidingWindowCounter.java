package org.hananaharonof.simplejavautils;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A utility that acts as a counter based on a time window. The user of this utility
 * sets in the constructor thw window size in seconds. Each call to add() adds one to
 * the counter. A call to count() return the counting made in the time window.
 *
 * @author haharonof (on 23/11/2018).
 */
public class TimeBasedSlidingWindowCounter {

  private int windowSizeInMs;
  private Deque<Long> counter;

  public TimeBasedSlidingWindowCounter(int windowSizeInSeconds) {
    this.windowSizeInMs = windowSizeInSeconds * 1000;
    this.counter = new ConcurrentLinkedDeque<>();
  }

  /**
   * Adds one to this counter.
   *
   * @return counting made in the time window.
   */
  public int add() {
    long now = System.currentTimeMillis();
    counter.addLast(now);
    counter.removeIf(current -> now - current > windowSizeInMs);
    return counter.size();
  }

  /**
   * Returns the counting made in the time window.
   *
   * @return the counting made in the time window.
   */
  public int count() {
    if (counter.isEmpty()) {
      return 0;
    }

    long now = System.currentTimeMillis();
    counter.removeIf(current ->  now - current > windowSizeInMs);
    return counter.size();
  }

  /**
   * Resets the counter to 0.
   */
  public void clear() {
    counter.clear();
  }
}