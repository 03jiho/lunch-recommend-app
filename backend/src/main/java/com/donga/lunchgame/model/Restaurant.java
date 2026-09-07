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

    // Groups restaurants by actual dish rather than the display category string,
    // which can differ for the same food (e.g. "중식 / 짬뽕" vs "일식 / 돈카츠" vs
    // "한식 / 경양식 돈까스"). Used to keep the Top 3 from showing duplicate dishes.
    @Column(name = "food_type", nullable = false)
    private String foodType;

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

    @Column(name = "creator_pick", nullable = false)
    private boolean creatorPick = false;

    @Column(name = "creator_note")
    private String creatorNote;

    @Column(name = "quick_grab", nullable = false)
    private boolean quickGrab = false;

    protected Restaurant() {
        // required by JPA
    }

    public Restaurant(String name, String category, String foodType, String priceRange, Integer price,
                       Integer avgPrepTime, Integer slopeLevel, String locationZone,
                       Integer walkingTimeMinutes, String signatureMenu, String mapUrl,
                       List<String> badges) {
        this.name = name;
        this.category = category;
        this.foodType = foodType;
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

    public Restaurant withCreatorPick(String note) {
        this.creatorPick = true;
        this.creatorNote = note;
        return this;
    }

    public Restaurant withQuickGrab() {
        this.quickGrab = true;
        return this;
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

    public String getFoodType() {
        return foodType;
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

    public boolean isCreatorPick() {
        return creatorPick;
    }

    public String getCreatorNote() {
        return creatorNote;
    }

    public boolean isQuickGrab() {
        return quickGrab;
    }
}
