package com.taskpro.util;

public final class ValidationUtil {
    private ValidationUtil() {}
    public static String trim(String value) { return value == null ? "" : value.trim(); }
    public static boolean username(String s) { return s != null && s.matches("[A-Za-z0-9_]{3,30}"); }
    public static boolean email(String s) { return s != null && s.length() <= 190 && s.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"); }
    public static boolean password(String s) { return s != null && s.length() >= 8 && s.length() <= 128; }
    public static boolean category(String s) { return "Java".equals(s) || "SQL".equals(s) || "DSA".equals(s); }
    public static boolean difficulty(String s) { return "Easy".equals(s) || "Medium".equals(s) || "Hard".equals(s); }
    public static boolean answer(String s) { return "A".equals(s) || "B".equals(s) || "C".equals(s) || "D".equals(s); }
}
