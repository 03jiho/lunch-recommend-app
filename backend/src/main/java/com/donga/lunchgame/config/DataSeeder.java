package com.donga.lunchgame.config;

import com.donga.lunchgame.model.LocationZone;
import com.donga.lunchgame.model.Restaurant;
import com.donga.lunchgame.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the embedded H2 database with sample restaurants around Dong-A University
 * Seunghak Campus on startup. Mirrors frontend/src/data/mockData.js.
 *
 * NOTE: This is illustrative sample data for prototyping. Replace with verified,
 * up-to-date restaurant information before production use.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;

    public DataSeeder(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void run(String... args) {
        if (restaurantRepository.count() > 0) {
            return;
        }

        restaurantRepository.saveAll(List.of(
                new Restaurant("정문 김밥천국", "분식 / 김밥", "4,000 ~ 6,000원", 5000,
                        10, 1, LocationZone.MAIN_GATE, 2,
                        "참치김밥 + 라면 세트", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 정문 김밥천국",
                        List.of("빠른 서비스", "가성비", "평지 접근")),

                new Restaurant("승학반점", "중식", "6,000 ~ 8,000원", 7000,
                        15, 1, LocationZone.MAIN_GATE, 3,
                        "짜장면 / 짬뽕", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 승학반점",
                        List.of("빠른 서비스", "평지 접근")),

                new Restaurant("하단족발보쌈", "한식 / 족발", "12,000 ~ 18,000원", 15000,
                        25, 2, LocationZone.HADAN_STATION, 12,
                        "한방족발 (소)", "https://map.kakao.com/?q=하단역 족발보쌈",
                        List.of("특별한 한 끼", "단체모임 추천")),

                new Restaurant("오르막 파스타공방", "양식 / 파스타", "11,000 ~ 15,000원", 13000,
                        30, 3, LocationZone.CAMPUS_INTERNAL, 15,
                        "크림 새우 파스타", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 파스타",
                        List.of("특별한 한 끼", "여유로운 식사")),

                new Restaurant("순환버스정류장 커리하우스", "카레 / 일식", "7,000 ~ 9,000원", 8000,
                        12, 1, LocationZone.SHUTTLE_STOP, 5,
                        "치즈돈카츠 카레", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 순환버스 정류장 카레",
                        List.of("빠른 서비스", "평지 접근")),

                new Restaurant("승학 국밥집", "한식 / 국밥", "7,000 ~ 9,000원", 8000,
                        10, 2, LocationZone.CAMPUS_INTERNAL, 8,
                        "돼지국밥", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 국밥",
                        List.of("빠른 서비스", "가성비")),

                new Restaurant("정문 도시락&샐러드", "도시락 / 샐러드", "5,500 ~ 7,500원", 6000,
                        5, 1, LocationZone.MAIN_GATE, 1,
                        "제육 도시락", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 정문 도시락",
                        List.of("빠른 서비스", "가성비", "평지 접근")),

                new Restaurant("하단 스테이크하우스", "양식 / 스테이크", "18,000 ~ 25,000원", 20000,
                        35, 2, LocationZone.HADAN_STATION, 14,
                        "안심 스테이크 세트", "https://map.kakao.com/?q=하단역 스테이크",
                        List.of("특별한 한 끼", "여유로운 식사")),

                new Restaurant("순환버스정류장 왕만두집", "분식 / 만두", "4,000 ~ 6,000원", 5000,
                        8, 1, LocationZone.SHUTTLE_STOP, 4,
                        "고기왕만두 + 잔치국수", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 순환버스 정류장 만두",
                        List.of("빠른 서비스", "가성비", "평지 접근")),

                new Restaurant("승학 이자카야", "일식 / 이자카야", "13,000 ~ 20,000원", 16000,
                        25, 3, LocationZone.CAMPUS_INTERNAL, 16,
                        "규동 + 가라아게 정식", "https://map.kakao.com/?q=동아대학교 승학캠퍼스 이자카야",
                        List.of("특별한 한 끼", "여유로운 식사"))
        ));
    }
}
