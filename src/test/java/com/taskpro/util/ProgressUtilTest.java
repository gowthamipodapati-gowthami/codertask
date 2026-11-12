package com.taskpro.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProgressUtilTest {
    @Test void initialProgress() {
        assertEquals(1, ProgressUtil.levelFor(0));
        assertEquals(0, ProgressUtil.progressPercent(0));
        assertEquals(100, ProgressUtil.xpUntilNextLevel(0));
    }
    @Test void levelsAtHundredXp() {
        assertEquals(1,ProgressUtil.levelFor(99));
        assertEquals(2,ProgressUtil.levelFor(100));
        assertEquals(3,ProgressUtil.levelFor(205));
        assertEquals(5,ProgressUtil.progressPercent(205));
        assertEquals(95,ProgressUtil.xpUntilNextLevel(205));
    }
}
