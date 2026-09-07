package com.donga.lunchgame.service;

import com.donga.lunchgame.dto.QuizAnswerRequest;
import com.donga.lunchgame.dto.RestaurantResponse;
import com.donga.lunchgame.model.LocationZone;
import com.donga.lunchgame.model.Restaurant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<Restaurant> restaurants = restaurantService.findAllEntities().stream()
                .sorted(Comparator.comparingInt((Restaurant r) -> score(r, answers)).reversed())
                .toList();

        // Avoid showing two restaurants of the same dish (e.g. two 돈카츠 places, or
        // two 짬뽕 places even though their category text differs). Once a foodType
        // is taken, skip further matches so the higher-scoring one wins and a
        // different dish takes its place.
        Set<String> seenFoodTypes = new HashSet<>();
        List<RestaurantResponse> picks = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            if (!seenFoodTypes.add(restaurant.getFoodType())) {
                continue;
            }
            picks.add(RestaurantResponse.from(restaurant));
            if (picks.size() == DEFAULT_RESULT_COUNT) {
                break;
            }
        }

        return picks;
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
            // A firm cutoff: cheap/value spots shouldn't surface just because of
            // other bonuses (e.g. creator's pick) when the user asked for gourmet.
            score += restaurant.getPrice() >= 10000 ? 3 : -6;
        }

        // Q3: Location
        if ("main_gate".equals(answers.location())) {
            score += LocationZone.MAIN_GATE.equals(restaurant.getLocationZone()) ? 3 : 0;
            score += restaurant.getWalkingTimeMinutes() <= 5 ? 1 : 0;
        } else if ("shuttle_stop".equals(answers.location())) {
            score += LocationZone.SHUTTLE_STOP.equals(restaurant.getLocationZone()) ? 3 : 0;
        }

        // Creator's pick: nudge toward the top when it's still a reasonable match —
        // but a fast/quick-bite pick (avgPrepTime < 15) doesn't fit a relaxed,
        // sit-down lunch, so it loses the bonus there instead of gaining it.
        if (restaurant.isCreatorPick()) {
            boolean quickPickDuringRelaxed = "relaxed".equals(answers.time()) && restaurant.getAvgPrepTime() < 15;
            score += quickPickDuringRelaxed ? -2 : 4;
        }

        // Quick-grab spots (rice burgers, cup rice — packaged to-go by nature, no
        // real "spend an hour here" experience) don't fit a relaxed lunch at all,
        // regardless of how cheap or fast they are.
        if (restaurant.isQuickGrab() && "relaxed".equals(answers.time())) {
            score -= 5;
        }

        return score;
    }
}
