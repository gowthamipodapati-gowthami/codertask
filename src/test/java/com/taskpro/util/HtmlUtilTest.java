package com.taskpro.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HtmlUtilTest {
    @Test void escapesUntrustedText() {
        assertEquals("&lt;script&gt;&amp;&quot;&#39;",HtmlUtil.escape("<script>&\"'"));
    }
}
