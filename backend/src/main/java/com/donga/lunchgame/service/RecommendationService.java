package com.donga.lunchgame.service;

import com.donga.lunchgame.dto.QuizAnswerRequest;
import com.donga.lunchgame.dto.RestaurantResponse;
import com.donga.lunchgame.model.LocationZone;
import com.donga.lunchgame.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Scores each restaurant against the user's quiz answers and returns the top matches.
 * Mirrors the client-side fallback logic in recommendationEngine.js so both paths agree.
 */
@Service
public class RecommendationService {

    private static final int DEFAULT_RESULT_COUNT = 3;

    private final RestaurantService restaurantService;

    public RecommendationService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public List<RestaurantResponse> recommend(QuizAnswerRequest answers) {
        List<Restaurant> restaurants = restaurantService.findAllEntities();

        return restaurants.stream()
                .sorted(Comparator.comparingInt((Restaurant r) -> score(r, answers)).reversed())
                .limit(DEFAULT_RESULT_COUNT)
                .map(RestaurantResponse::from)
                .toList();
    }

    private int score(Restaurant restaurant, QuizAnswerRequest answers) {
        int score = 0;

        // Q1: Time constraint
        if ("fast".equals(answers.time())) {
            score += restaurant.getAvgPrepTime() <= 15 ? 3 : -2;
        } else if ("relaxed".equals(answers.time())) {
            score += restaurant.getAvgPrepTime() >= 20 ? 3 : 1;
        }

        // Q2: Budget
        if ("budget".equals(answers.budget())) {
            score += restaurant.getPrice() <= 6000 ? 3 : -3;
        } else if ("gourmet".equals(answers.budget())) {
            score += restaurant.getPrice() >= 10000 ? 3 : -1;
        }

        // Q3: Location
        if ("main_gate".equals(answers.location())) {
            score += LocationZone.MAIN_GATE.equals(restaurant.getLocationZone()) ? 3 : 0;
            score += restaurant.getWalkingTimeMinutes() <= 5 ? 1 : 0;
        } else if ("shuttle_stop".equals(answers.location())) {
            score += LocationZone.SHUTTLE_STOP.equals(restaurant.getLocationZone()) ? 3 : 0;
        }

        return score;
    }
}
