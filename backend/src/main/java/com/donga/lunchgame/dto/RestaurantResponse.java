package com.donga.lunchgame.dto;

import com.donga.lunchgame.model.Restaurant;

import java.util.List;

public record RestaurantResponse(
        Long id,
        String name,
        String category,
        String priceRange,
        Integer price,
        Integer avgPrepTime,
        Integer slopeLevel,
        String locationZone,
        Integer walkingTimeMinutes,
        String signatureMenu,
        String mapUrl,
        List<String> badges,
        boolean creatorPick,
        String creatorNote,
        boolean quickGrab
) {

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCategory(),
                restaurant.getPriceRange(),
                restaurant.getPrice(),
                restaurant.getAvgPrepTime(),
                restaurant.getSlopeLevel(),
                restaurant.getLocationZone(),
                restaurant.getWalkingTimeMinutes(),
                restaurant.getSignatureMenu(),
                restaurant.getMapUrl(),
                restaurant.getBadges(),
                restaurant.isCreatorPick(),
                restaurant.getCreatorNote(),
                restaurant.isQuickGrab()
        );
    }
}
