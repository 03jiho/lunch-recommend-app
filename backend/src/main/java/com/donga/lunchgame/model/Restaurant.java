package com.donga.lunchgame.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(name = "price_range", nullable = false)
    private String priceRange;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "avg_prep_time", nullable = false)
    private Integer avgPrepTime;

    @Column(name = "slope_level", nullable = false)
    private Integer slopeLevel;

    @Column(name = "location_zone", nullable = false)
    private String locationZone;

    @Column(name = "walking_time_minutes", nullable = false)
    private Integer walkingTimeMinutes;

    @Column(name = "signature_menu", nullable = false)
    private String signatureMenu;

    @Column(name = "map_url", nullable = false, length = 500)
    private String mapUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "restaurant_badges", joinColumns = @jakarta.persistence.JoinColumn(name = "restaurant_id"))
    @Column(name = "badge")
    private List<String> badges;

    protected Restaurant() {
        // required by JPA
    }

    public Restaurant(String name, String category, String priceRange, Integer price,
                       Integer avgPrepTime, Integer slopeLevel, String locationZone,
                       Integer walkingTimeMinutes, String signatureMenu, String mapUrl,
                       List<String> badges) {
        this.name = name;
        this.category = category;
        this.priceRange = priceRange;
        this.price = price;
        this.avgPrepTime = avgPrepTime;
        this.slopeLevel = slopeLevel;
        this.locationZone = locationZone;
        this.walkingTimeMinutes = walkingTimeMinutes;
        this.signatureMenu = signatureMenu;
        this.mapUrl = mapUrl;
        this.badges = badges;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAvgPrepTime() {
        return avgPrepTime;
    }

    public Integer getSlopeLevel() {
        return slopeLevel;
    }

    public String getLocationZone() {
        return locationZone;
    }

    public Integer getWalkingTimeMinutes() {
        return walkingTimeMinutes;
    }

    public String getSignatureMenu() {
        return signatureMenu;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public List<String> getBadges() {
        return badges;
    }
}
