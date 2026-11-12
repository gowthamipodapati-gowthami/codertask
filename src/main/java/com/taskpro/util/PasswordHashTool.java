package com.taskpro.util;

import java.io.Console;

/** Optional one-time CLI helper to change the demo admin password. Run as a Java Application. */
public final class PasswordHashTool {
    private PasswordHashTool() {}
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("Run this tool from a real terminal so the password can be entered without echo.");
            return;
        }
        char[] password = console.readPassword("New admin password (at least 8 chars): ");
        try {
            if (password == null || password.length < 8) {
                System.err.println("Password must contain at least 8 characters."); return;
            }
            String newHash = PasswordUtil.hash(new String(password));
            System.out.println("Run this SQL in MySQL Workbench (replace only the password hash):");
            System.out.println("USE codequest_db;");
            System.out.println("UPDATE admins SET password_hash='" + newHash + "' WHERE username='admin';");
        } finally {
            if (password != null) java.util.Arrays.fill(password,'\0');
        }
    }
}
