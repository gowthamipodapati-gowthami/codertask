package com.taskpro.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {
    @Test void hashesAreSaltedAndVerifiable() {
        String a = PasswordUtil.hash("Example123!");
        String b = PasswordUtil.hash("Example123!");
        assertNotEquals(a,b);
        assertTrue(PasswordUtil.verify("Example123!",a));
        assertFalse(PasswordUtil.verify("BadPassword",a));
        assertFalse(PasswordUtil.verify("Example123!","not a valid hash"));
    }
}
