package com.taskpro.model;

public record SubmissionResult(boolean correct, boolean duplicate, String missionTitle, String selectedAnswer,
                               String correctAnswer, int awardedXp, int totalXp, int level, String message) {
    public static SubmissionResult duplicateResult() {
        return new SubmissionResult(false, true, "", "", "", 0, 0, 0,
            "You already attempted this mission. Only one attempt is allowed.");
    }
}
