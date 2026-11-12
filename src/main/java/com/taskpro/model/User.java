package com.taskpro.model;

public record User(long id, String username, String email, int xp, int level, int completedMissions) {}
