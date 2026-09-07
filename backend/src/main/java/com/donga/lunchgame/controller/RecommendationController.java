package com.donga.lunchgame.controller;

import com.donga.lunchgame.dto.QuizAnswerRequest;
import com.donga.lunchgame.dto.RestaurantResponse;
import com.donga.lunchgame.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public List<RestaurantResponse> getRecommendations(@Valid @RequestBody QuizAnswerRequest answers) {
        return recommendationService.recommend(answers);
    }
}
