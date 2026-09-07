package com.donga.lunchgame.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Answers submitted from the balance-game quiz.
 * time:     "fast" | "relaxed"
 * budget:   "budget" | "gourmet"
 * location: "main_gate" | "no_slope" | "shuttle_stop"
 */
public record QuizAnswerRequest(
        @NotBlank String time,
        @NotBlank String budget,
        @NotBlank String location
) {
}
