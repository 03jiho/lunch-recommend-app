# 점심 뭐먹지? — 동아대학교 승학캠퍼스 점심 밸런스 게임

**[바로가기 (Vercel)](https://donga-lunch.vercel.app)** · [바로가기 (GitHub Pages)](https://03jiho.github.io/lunch-recommend-app/)

동아대학교 승학캠퍼스(하단) 주변에서 시간·예산·위치 조건에 맞춰 점심 맛집 Top 3를 추천해주는 밸런스 게임 스타일의 웹 앱입니다.

## 스크린샷

3개의 질문(점심시간, 예산, 위치)에 답하면 조건에 맞는 맛집 3곳을 추천 배지·도보시간·대표메뉴·지도 링크와 함께 보여줍니다. 제작자 추천 맛집이 포함되면 카드에 리본이 추가로 표시됩니다.

## 주요 기능

- **밸런스 게임 퀴즈**: 진행바가 있는 3단계 질문 (15분 초스피드 vs 1시간 여유, 가성비 메뉴 vs 1만원 이상, 정문 근처 vs 사하10 탑승장 근처)
- **추천 엔진**: 답변을 기반으로 각 맛집에 점수를 매겨 상위 3곳을 반환 (프론트엔드 클라이언트 로직과 백엔드 API가 동일한 규칙을 공유). 카테고리 표기가 달라도 같은 음식(예: "중식 / 짬뽕"과 "중식 / 퓨전짬뽕", "일식 / 돈카츠"와 "한식 / 경양식 돈까스")이면 실제 메뉴 기준(`foodType`)으로 묶어 Top 3에 중복 없이 서로 다른 음식이 나오도록 합니다. "정문 근처" 등 위치 조건은 해당 구역 맛집을 항상 먼저 채우도록 우선 적용해 위치 선택이 실제로 결과에 반영되게 하며, "15분 초스피드 + 1만원 이상 + 정문 근처" 조합처럼 조건상 점수가 가장 높은 곳(고래섬, 조리 20분)이 시간 조건과 안 맞는 경우엔 예외적으로 조리시간이 짧은 대안(온센)으로 대체합니다.
- **오프라인 폴백**: 백엔드가 꺼져 있어도 프론트엔드가 로컬 데이터로 자동 전환되어 항상 동작
- **제작자 추천**: 제작자가 직접 골라둔 맛집은 추천 점수에 가산점이 붙어 결과 상위에 노출되기 쉬워지고, 카드에는 "제작자 추천" 리본이 표시됩니다. 시간·예산·위치 조건과 완전히 동떨어진 곳까지 억지로 끌어올리지는 않고, 어느 정도 조건이 맞을 때 우선순위를 살짝 높여주는 정도입니다.
- **실제 맛집 데이터 36곳**: 상호명·주소·영업시간·가격은 다이닝코드·카카오맵 매장 메뉴판·식신 매거진을 직접 확인해 반영 ([상세 출처 및 한계는 아래 참고](#데이터-출처-및-한계))
- **식사 후 시간 보내기**: "1시간 이상 여유로운 식사"를 고르면 결과 하단에 근처 PC방·카페·아이스크림 가게를 추천합니다. 1위 식당 위치에 가장 가까운 곳을 골라 보여줍니다.

## 기술 스택

| 구분 | 기술 |
|---|---|
| Frontend | React 19, Vite, Tailwind CSS v4, lucide-react |
| Backend | Spring Boot 3.3 (Java 17), Spring Data JPA, H2 (embedded) |
| 배포 | GitHub Pages (프론트엔드, GitHub Actions로 자동 배포) |

## 프로젝트 구조

```
donga-lunch-balance-game/
├── frontend/                      React + Vite
│   └── src/
│       ├── data/mockData.js       맛집 데이터 (36곳)
│       ├── data/questions.js      퀴즈 질문 정의
│       ├── utils/recommendationEngine.js   추천 점수 계산 로직
│       ├── api/recommendationApi.js        백엔드 호출 + 폴백
│       └── components/            QuizContainer, ResultCard 등
└── backend/                       Spring Boot (Java 17)
    └── src/main/java/com/donga/lunchgame/
        ├── model/ · repository/ · dto/
        ├── service/                동일한 추천 점수 로직
        ├── controller/             REST API
        └── config/                 CORS, DataSeeder (H2 초기 데이터)
```

## 로컬 실행

### 프론트엔드 단독 실행 (백엔드 없이도 동작)

```bash
cd frontend
npm install
npm run dev
```

`http://localhost:5173` 에서 확인 (백엔드가 없으면 자동으로 로컬 데이터를 사용합니다).

### 백엔드 실행 (선택)

Maven 설치 없이 포함된 Maven Wrapper로 실행합니다. Java 17이 필요합니다.

```bash
cd backend
./mvnw spring-boot:run
```

`http://localhost:8080` 에서 API 서버가 실행되며, 프론트엔드 dev 서버(`5173`)가 `/api/*` 요청을 자동으로 프록시합니다. H2 콘솔은 `http://localhost:8080/h2-console` 에서 확인할 수 있습니다.

## 데이터 출처 및 한계

- **상호명 · 주소 · 카테고리 · 영업시간**: [DiningCode](https://www.diningcode.com), [Kakao Map](https://map.kakao.com), [식신(Siksin)](https://www.siksinhot.com) 매거진 기사를 직접 확인해 반영했습니다.
- **가격**: 해당 지점의 실제 카카오맵 메뉴판이나 방문자 후기의 실측 주문 내역을 기준으로 합니다.
- **도보시간 · 경사도(slopeLevel) · 조리시간**: 공개된 데이터가 없어 추정치입니다. 모든 매장이 정문 밖 평지 상권(하단 상업지구)에 위치해 `slopeLevel`은 전부 1(평지)로 설정했으며, 도보시간은 정문 실주소(낙동대로550번길 37)와의 지번 근접도로 순서만 매겼습니다.
- 실제 방문 전에는 최신 영업시간·가격을 다시 확인하시길 권장합니다.

## 라이선스

[MIT](LICENSE)
