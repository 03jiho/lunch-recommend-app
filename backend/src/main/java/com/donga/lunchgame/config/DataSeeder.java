package com.donga.lunchgame.config;

import com.donga.lunchgame.model.LocationZone;
import com.donga.lunchgame.model.Restaurant;
import com.donga.lunchgame.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds the embedded H2 database on startup with real restaurants around Dong-A
 * University Seunghak Campus (Hadan), Busan Saha-gu. Mirrors frontend/src/data/mockData.js.
 *
 * Name, address, category, and operating hours are sourced from DiningCode
 * listings/reviews, Siksin magazine roundups, Hotple, and Kakao Map place data. Menu
 * prices are sourced per-item, confirmed directly from each restaurant's own in-app
 * menu board where possible; franchise items use the chain's official/national menu
 * price.
 *
 * `foodType` groups restaurants by actual dish rather than the display category
 * string, which can differ for the same food (e.g. "중식 / 짬뽕" vs "중식 / 퓨전짬뽕" vs
 * "일식 / 돈카츠" vs "한식 / 경양식 돈까스"). The recommendation service uses this to
 * avoid showing two of the same dish in the Top 3.
 *
 * Prep time, slope level, and walking time are not published anywhere and are
 * estimated: all spots are street-level shops in the flat Hadan commercial belt
 * outside the campus gate (the campus itself sits on Seunghak-san and is reached via
 * a steep "108 steps" stairway past the gate, per Namuwiki), so slopeLevel is 1 for
 * all of them. Walking time is ordered by how close each address's lot number is to
 * the verified main-gate address (Nakdong-daero 550beon-gil 37); the McDonald's at
 * Nakdong-daero 548 is confirmed by Namuwiki to sit directly across from the campus
 * shuttle-bus stop (route 사하10), which anchors the SHUTTLE_STOP zone for the
 * Nakdong-daero (main-road) cluster.
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
                new Restaurant("고래섬 동아대점", "한식 / 낙곱새·볶음요리", "낙곱새", "1인분 약 10,000 ~ 12,000원", 10500,
                        20, 1, LocationZone.MAIN_GATE, 3,
                        "낙곱새 (낙지+곱창+새우, 밥 무한리필)",
                        "https://map.kakao.com/?q=고래섬 동아대점 부산 사하구 낙동대로550번길 24",
                        List.of("특별한 한 끼", "단체모임 추천", "평지 접근")),

                new Restaurant("동아분식", "분식 / 라면·볶음밥", "분식", "3,500 ~ 6,000원", 6000,
                        10, 1, LocationZone.MAIN_GATE, 2,
                        "햄볶음밥 (라면 국물·기본 반찬 포함)",
                        "https://map.kakao.com/?q=동아분식 부산 사하구 낙동대로516번길 47",
                        List.of("빠른 서비스", "가성비", "평지 접근"))
                        .withCreatorPick(null),

                new Restaurant("하단만족 족발&보쌈 하단오거리본점", "한식 / 족발·보쌈", "족발보쌈", "실속보쌈·실속족발 31,000원(2인, 매장 메뉴판 확인), 1인 환산 약 15,500원", 15500,
                        25, 1, LocationZone.MAIN_GATE, 3,
                        "쭈꾸미보쌈",
                        "https://map.kakao.com/?q=하단만족 부산 사하구 승학로10번길 3",
                        List.of("특별한 한 끼", "단체모임 추천", "평지 접근")),

                new Restaurant("이모식당", "한식 / 백반·전골", "백반", "7,000 ~ 8,000원", 7000,
                        12, 1, LocationZone.MAIN_GATE, 4,
                        "두루치기정식 (밥·밑반찬 리필 가능)",
                        "https://map.kakao.com/?q=이모식당 부산 사하구 낙동대로550번길 16",
                        List.of("가성비", "평지 접근")),

                new Restaurant("계월당", "한식 / 닭곰탕", "국밥탕", "9,000원", 9000,
                        15, 1, LocationZone.MAIN_GATE, 4,
                        "닭곰탕 (들깨 베이스, 밥 무한리필)",
                        "https://map.kakao.com/?q=계월당 부산 사하구 낙동대로550번길 14",
                        List.of("가성비", "평지 접근")),

                new Restaurant("청계옥 하단", "한식 / 철판 닭갈비", "닭갈비", "13,000 ~ 16,000원", 13000,
                        20, 1, LocationZone.MAIN_GATE, 5,
                        "철판 닭갈비 + 볶음밥",
                        "https://map.kakao.com/?q=청계옥 하단 부산 사하구 낙동대로549번길 29",
                        List.of("특별한 한 끼", "평지 접근")),

                new Restaurant("리코리코 동아대점", "멕시칸 / 부리또", "부리또", "5,000 ~ 8,000원 (세트 기준)", 6000,
                        8, 1, LocationZone.MAIN_GATE, 5,
                        "부리또 세트 (맵기·토핑 커스터마이즈 가능)",
                        "https://map.kakao.com/?q=리코리코 동아대점 부산 사하구 낙동대로516번길 33",
                        List.of("빠른 서비스", "가성비", "평지 접근"))
                        .withCreatorPick(null),

                new Restaurant("봉대박 스파게티 동아대점", "양식 / 파스타", "파스타", "7,900원~", 7900,
                        12, 1, LocationZone.MAIN_GATE, 6,
                        "봉골레 스파게티 (식전 빵·마시멜로 제공)",
                        "https://map.kakao.com/?q=봉대박 스파게티 부산 사하구 낙동대로536번길 13",
                        List.of("가성비", "평지 접근"))
                        .withCreatorPick(null),

                new Restaurant("라마마", "중식 / 마라탕·마라샹궈", "마라탕", "100g당 2,200원 (중량 주문)", 8000,
                        15, 1, LocationZone.SHUTTLE_STOP, 9,
                        "마라샹궈 (공깃밥 무한리필)",
                        "https://map.kakao.com/?q=라마마 부산 사하구 낙동대로 515",
                        List.of("가성비", "평지 접근")),

                new Restaurant("호랭이밀냉면", "한식 / 밀냉면", "냉면", "9,000 ~ 9,500원", 9000,
                        10, 1, LocationZone.SHUTTLE_STOP, 10,
                        "물밀냉면 (사골·토종닭 육수)",
                        "https://map.kakao.com/?q=호랭이밀냉면 부산 사하구 승학로 5-1",
                        List.of("빠른 서비스", "가성비", "평지 접근")),

                new Restaurant("용이초밥 부산하단점", "일식 / 회전초밥", "초밥", "런치세트 9,500 ~ 10,900원 (11:00~15:00)", 9500,
                        12, 1, LocationZone.SHUTTLE_STOP, 12,
                        "런치세트 (미니우동·모밀 선택)",
                        "https://map.kakao.com/?q=용이초밥 부산 사하구 낙동남로 1419",
                        List.of("가성비", "평지 접근")),

                new Restaurant("서가앤쿡 부산하단점", "양식 / 스테이크 전문", "스테이크", "한상세트 3만 ~ 6만원대 (2~4인)", 30000,
                        25, 1, LocationZone.SHUTTLE_STOP, 13,
                        "1kg 스테이크 한상 (3~4인)",
                        "https://map.kakao.com/?q=서가앤쿡 부산하단점 부산 사하구 낙동대로 491",
                        List.of("특별한 한 끼", "단체모임 추천")),

                new Restaurant("화반 하단점", "한식 / 비빔밥·두루치기", "비빔밥", "9,900 ~ 11,900원", 9900,
                        15, 1, LocationZone.MAIN_GATE, 2,
                        "푸짐한비빔밥+된장찌개",
                        "https://map.kakao.com/?q=화반 하단점 부산 사하구 낙동대로516번길 55",
                        List.of("가성비", "평지 접근")),

                new Restaurant("맘스터치 동아대점", "패스트푸드 / 버거", "버거", "싸이버거 세트 7,300원", 7300,
                        8, 1, LocationZone.MAIN_GATE, 2,
                        "싸이버거 세트",
                        "https://map.kakao.com/?q=맘스터치 동아대점 부산 사하구 낙동대로516번길 47",
                        List.of("빠른 서비스", "가성비", "평지 접근")),

                new Restaurant("동대식당", "한식 / 두루치기·곱창전골", "두루치기전골", "6,000 ~ 8,000원", 8000,
                        15, 1, LocationZone.MAIN_GATE, 4,
                        "두루치기 / 곱창전골",
                        "https://map.kakao.com/?q=동대식당 부산 사하구 낙동대로550번길 16",
                        List.of("가성비", "평지 접근")),

                new Restaurant("카무이", "일식 / 돈카츠", "돈카츠", "13,000 ~ 14,000원", 14000,
                        15, 1, LocationZone.MAIN_GATE, 4,
                        "크림파스타돈카츠",
                        "https://map.kakao.com/?q=카무이 부산 사하구 낙동대로550번길 16",
                        List.of("특별한 한 끼", "평지 접근")),

                new Restaurant("돌담식당", "한식 / 백반·찌개", "백반", "5,000 ~ 8,000원", 7000,
                        12, 1, LocationZone.MAIN_GATE, 4,
                        "김치찌개 (찌개·비빔밥·볶음밥류 다양)",
                        "https://map.kakao.com/?q=돌담식당 부산 사하구 낙동대로550번길 16-1",
                        List.of("가성비", "평지 접근")),

                new Restaurant("롯데리아·맥도날드 동아대점", "패스트푸드 / 버거", "버거", "세트 7,300 ~ 7,600원", 7300,
                        6, 1, LocationZone.SHUTTLE_STOP, 2,
                        "불고기버거 세트 / 빅맥 세트",
                        "https://map.kakao.com/?q=맥도날드 동아대점 부산 사하구 낙동대로 548",
                        List.of("빠른 서비스", "가성비", "평지 접근")),

                new Restaurant("써브웨이 부산동아대점", "패스트푸드 / 샌드위치", "샌드위치", "에그마요 15cm세트 10,900원 (매장 메뉴판 확인)", 10900,
                        5, 1, LocationZone.SHUTTLE_STOP, 4,
                        "15cm 샌드위치 세트",
                        "https://map.kakao.com/?q=써브웨이 부산동아대점 부산 사하구 낙동대로 542",
                        List.of("빠른 서비스", "평지 접근")),

                new Restaurant("온센 부산사하구점", "일식 / 텐동·튀김덮밥", "텐동", "온센텐동 9,500원 (매장 메뉴판 확인)", 9500,
                        12, 1, LocationZone.MAIN_GATE, 4,
                        "온센텐동",
                        "https://map.kakao.com/?q=온센 부산사하구점 부산 사하구 낙동대로516번길 43",
                        List.of("가성비", "평지 접근")),

                new Restaurant("다맛", "한식 / 경양식 돈까스", "돈카츠", "8,500 ~ 9,000원", 8500,
                        15, 1, LocationZone.MAIN_GATE, 4,
                        "등심돈까스 (식전 스프 포함)",
                        "https://map.kakao.com/?q=다맛 부산 사하구 낙동대로516번길 39",
                        List.of("가성비", "평지 접근"))
                        .withCreatorPick(null),

                new Restaurant("고수 숯불고기주는 냉면&밀면 하단점", "한식 / 밀면·냉면", "냉면", "7,500 ~ 8,000원 (숯불고기 포함)", 7500,
                        10, 1, LocationZone.MAIN_GATE, 5,
                        "밀면 (숯불고기 맛보기 포함)",
                        "https://map.kakao.com/?q=고수 부산 사하구 낙동대로516번길 24",
                        List.of("빠른 서비스", "가성비", "평지 접근"))
                        .withCreatorPick(null),

                new Restaurant("올바로갈비", "한식 / 돼지갈비", "돼지갈비", "생 100g 3,900원+양념 100g 3,500원+계란공기밥 1,500원 = 8,900원 (실제 방문 후기 주문 사례)", 8900,
                        20, 1, LocationZone.SHUTTLE_STOP, 10,
                        "생돼지갈비 / 양념돼지갈비",
                        "https://map.kakao.com/?q=올바로갈비 부산 사하구 낙동대로519번길 25",
                        List.of("특별한 한 끼", "평지 접근")),

                new Restaurant("짬뽕관 부산하단점", "중식 / 짬뽕", "짬뽕", "10,000 ~ 12,000원", 10000,
                        12, 1, LocationZone.SHUTTLE_STOP, 6,
                        "짬뽕 (200도 고온 불맛)",
                        "https://map.kakao.com/?q=짬뽕관 부산하단점 부산 사하구 낙동대로 528",
                        List.of("가성비"))
                        .withCreatorPick("홀에서 먹으면 밥·청포도에이드 무한리필"),

                new Restaurant("홍주방 하단본점", "중식 / 마라탕·마라샹궈", "마라탕", "마라탕 100g 1,800원, 1인당 약 15,000원 (실제 방문 후기 기준)", 15000,
                        15, 1, LocationZone.SHUTTLE_STOP, 5,
                        "마라탕 / 꿔바로우",
                        "https://map.kakao.com/?q=홍주방 하단본점 부산 사하구 낙동대로 532",
                        List.of("특별한 한 끼")),

                new Restaurant("제일돌곱창", "한식 / 양곱창·돌곱창전골", "곱창전골", "돌곱창전골(소) 29,000원", 29000,
                        25, 1, LocationZone.SHUTTLE_STOP, 7,
                        "돌곱창전골",
                        "https://map.kakao.com/?q=제일돌곱창 부산 사하구 낙동대로535번길 4",
                        List.of("특별한 한 끼", "단체모임 추천")),

                new Restaurant("핏제리아곳간", "양식 / 화덕피자·파스타", "피자", "알리오올리오 11,900원 / 트러플크림뇨끼 15,500원 (매장 메뉴판 확인)", 11900,
                        15, 1, LocationZone.SHUTTLE_STOP, 4,
                        "마르게리따 피자",
                        "https://map.kakao.com/?q=핏제리아곳간 부산 사하구 낙동대로 543",
                        List.of("가성비")),

                new Restaurant("명륜진사갈비 부산하단점", "한식 / 돼지갈비 무한리필", "돼지갈비", "1인 13,500원 (무한리필 정상가)", 13500,
                        20, 1, LocationZone.SHUTTLE_STOP, 5,
                        "무한리필 돼지갈비",
                        "https://map.kakao.com/?q=명륜진사갈비 부산하단점 부산 사하구 낙동대로 533",
                        List.of("특별한 한 끼", "단체모임 추천")),

                new Restaurant("신전떡볶이 동아대점", "분식 / 떡볶이", "떡볶이", "오리지널 떡볶이 4,500원 (1인 세트 9,500원)", 4500,
                        8, 1, LocationZone.MAIN_GATE, 6,
                        "오리지널 떡볶이",
                        "https://map.kakao.com/?q=신전떡볶이 동아대점 부산 사하구 낙동대로536번길 17",
                        List.of("빠른 서비스", "가성비", "평지 접근")),

                new Restaurant("경대컵밥 동아대점", "분식 / 컵밥", "컵밥", "5,800 ~ 6,800원", 5800,
                        5, 1, LocationZone.MAIN_GATE, 6,
                        "제육컵밥",
                        "https://map.kakao.com/?q=경대컵밥 동아대점 부산 사하구 낙동대로536번길 17",
                        List.of("빠른 서비스", "가성비", "평지 접근"))
                        .withQuickGrab(),

                new Restaurant("봉구스밥버거 부산동아대점", "분식 / 밥버거", "밥버거", "2,300 ~ 5,000원", 3300,
                        6, 1, LocationZone.MAIN_GATE, 2,
                        "햄치즈 밥버거",
                        "https://map.kakao.com/?q=봉구스밥버거 부산동아대점 부산 사하구 낙동대로516번길 55",
                        List.of("빠른 서비스", "가성비", "평지 접근"))
                        .withQuickGrab(),

                new Restaurant("은화수식당 동아대점", "한식 / 경양식 돈까스", "돈카츠", "10,000 ~ 12,500원", 10000,
                        15, 1, LocationZone.MAIN_GATE, 6,
                        "경양식돈가스",
                        "https://map.kakao.com/?q=은화수식당 동아대점 부산 사하구 낙동대로536번길 21",
                        List.of("특별한 한 끼", "평지 접근")),

                new Restaurant("하이뽕 동아대점", "중식 / 퓨전짬뽕", "짬뽕", "레드뽕 9,000원 / 크림뽕 11,000원 / 로제뽕 11,500원 (매장 메뉴판 확인)", 11000,
                        15, 1, LocationZone.MAIN_GATE, 6,
                        "크림뽕",
                        "https://map.kakao.com/?q=하이뽕 동아대점 부산 사하구 낙동대로516번길 16",
                        List.of("특별한 한 끼")),

                new Restaurant("삼국지짬뽕천하", "중식 / 짬뽕·탕수육", "짬뽕", "산동탕수육(미니) 13,000원 / 탕수육 세트 19,500 ~ 21,500원", 13000,
                        15, 1, LocationZone.MAIN_GATE, 2,
                        "차돌짬뽕 + 탕수육 세트",
                        "https://map.kakao.com/?q=삼국지짬뽕천하 부산 사하구 낙동대로550번길 1",
                        List.of("특별한 한 끼", "평지 접근")),

                new Restaurant("밉상짬뽕", "중식 / 짬뽕·짜장면", "짬뽕", "밉상짬뽕 6,000원 / 밉상유니짜장 5,000원 (매장 메뉴판 확인)", 6000,
                        10, 1, LocationZone.MAIN_GATE, 6,
                        "밉상짬뽕",
                        "https://map.kakao.com/?q=밉상짬뽕 부산 사하구 낙동대로536번길 9",
                        List.of("빠른 서비스", "가성비", "평지 접근"))
                        .withCreatorPick(null),

                new Restaurant("키타이", "일식 / 돈카츠", "돈카츠", "키타이카츠 10,000원 / 안심카츠 11,500원 (매장 메뉴판 확인)", 10000,
                        15, 1, LocationZone.MAIN_GATE, 4,
                        "안심카츠 (336시간 숙성)",
                        "https://map.kakao.com/?q=키타이 부산 사하구 낙동대로 552",
                        List.of("특별한 한 끼", "평지 접근"))
                        .withCreatorPick(null)
        ));
    }
}
