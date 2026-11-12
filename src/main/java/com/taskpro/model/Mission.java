package com.taskpro.model;

/** Public mission model deliberately excludes the correct answer. */
public record Mission(long id, String title, String description, String category, String difficulty,
                      String optionA, String optionB, String optionC, String optionD, int xpReward,
                      boolean attempted) {}
