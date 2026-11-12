package com.taskpro.util;

/** An additional level is unlocked for each 100 total XP. */
public final class ProgressUtil {
    private ProgressUtil() {}
    public static int levelFor(int xp) { return Math.max(0, xp) / 100 + 1; }
    public static int progressPercent(int xp) { return Math.max(0, xp) % 100; }
    public static int xpUntilNextLevel(int xp) { return 100 - progressPercent(xp); }
}
