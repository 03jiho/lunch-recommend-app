package com.donga.lunchgame.service;

import com.donga.lunchgame.dto.RestaurantResponse;
import com.donga.lunchgame.model.Restaurant;
import com.donga.lunchgame.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantResponse::from)
                .toList();
    }

    public List<Restaurant> findAllEntities() {
        return restaurantRepository.findAll();
    }
}
