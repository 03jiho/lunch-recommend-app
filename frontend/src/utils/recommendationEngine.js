import { LOCATION_ZONES } from '../data/mockData';

// Scores each restaurant against the user's three answers and returns the top N matches.
// Mirrors the scoring logic implemented server-side in RecommendationService.java.
export function getRecommendations(restaurants, answers, topN = 3) {
  const scored = restaurants
    .map((restaurant) => ({
      restaurant,
      score: scoreRestaurant(restaurant, answers),
    }))
    .sort((a, b) => b.score - a.score);

  // The location answer should actually change what shows up. A +3 score bonus alone
  // isn't enough — a cluster of great time/budget/creator-pick matches on one side of
  // campus can outscore every restaurant near the other, so the chosen zone never wins
  // a slot. Put zone matches ahead of everything else; only reach outside the zone to
  // fill remaining slots if it doesn't have enough distinct dishes.
  const targetZone =
    answers.location === 'main_gate'
      ? LOCATION_ZONES.MAIN_GATE
      : answers.location === 'shuttle_stop'
      ? LOCATION_ZONES.SHUTTLE_STOP
      : null;

  const ordered = targetZone
    ? [
        ...scored.filter(({ restaurant }) => restaurant.locationZone === targetZone),
        ...scored.filter(({ restaurant }) => restaurant.locationZone !== targetZone),
      ]
    : scored;

  // Avoid showing two restaurants of the same dish (e.g. two 돈카츠 places, or two
  // 짬뽕 places even though their category text differs — "중식 / 짬뽕" vs "일식 /
  // 돈카츠" vs "한식 / 경양식 돈까스" can still be the same food). Once a foodType is
  // taken, skip further matches so the higher-scoring one wins and a different dish
  // takes its place.
  const seenFoodTypes = new Set();
  const picks = [];

  for (const { restaurant } of ordered) {
    if (seenFoodTypes.has(restaurant.foodType)) continue;
    seenFoodTypes.add(restaurant.foodType);
    picks.push(restaurant);
    if (picks.length === topN) break;
  }

  // Exception: "15분 초스피드 + 10,000원 이상 + 정문 근처" naturally lands on
  // 고래섬 (조리 20분, 밥 무한리필 — a sit-down, take-your-time dish) once 돈카츠/
  // 짬뽕 are already taken, since it's the next-best premium dish near the main
  // gate. But 20분은 "초스피드"와 안 맞으므로, 조리 12분인 온센(텐동)으로 대체한다.
  if (answers.time === 'fast' && answers.budget === 'gourmet' && answers.location === 'main_gate') {
    const slowIndex = picks.findIndex((r) => r.name === '고래섬 동아대점');
    const onsen = restaurants.find((r) => r.name === '온센 부산사하구점');
    if (slowIndex !== -1 && onsen && !picks.some((r) => r.foodType === onsen.foodType)) {
      picks[slowIndex] = onsen;
    }
  }

  return picks;
}

function scoreRestaurant(restaurant, answers) {
  let score = 0;

  // Q1: Time constraint
  if (answers.time === 'fast') {
    score += restaurant.avgPrepTime <= 15 ? 3 : -2;
  } else if (answers.time === 'relaxed') {
    score += restaurant.avgPrepTime >= 20 ? 3 : 1;
  }

  // Q2: Budget
  if (answers.budget === 'budget') {
    score += restaurant.price <= 6000 ? 3 : -3;
  } else if (answers.budget === 'gourmet') {
    // A firm cutoff: cheap/value spots shouldn't surface just because of
    // other bonuses (e.g. creator's pick) when the user asked for gourmet.
    score += restaurant.price >= 10000 ? 3 : -6;
  }

  // Q3: Location
  if (answers.location === 'main_gate') {
    score += restaurant.locationZone === LOCATION_ZONES.MAIN_GATE ? 3 : 0;
    score += restaurant.walkingTimeMinutes <= 5 ? 1 : 0;
  } else if (answers.location === 'shuttle_stop') {
    score += restaurant.locationZone === LOCATION_ZONES.SHUTTLE_STOP ? 3 : 0;
  }

  // Creator's pick: nudge toward the top when it's still a reasonable match —
  // but a fast/quick-bite pick (avgPrepTime < 15) doesn't fit a relaxed,
  // sit-down lunch, so it loses the bonus there instead of gaining it.
  if (restaurant.creatorPick) {
    score += answers.time === 'relaxed' && restaurant.avgPrepTime < 15 ? -2 : 4;
  }

  // Quick-grab spots (rice burgers, cup rice — packaged to-go by nature, no real
  // "spend an hour here" experience) don't fit a relaxed lunch at all, regardless
  // of how cheap or fast they are.
  if (restaurant.quickGrab && answers.time === 'relaxed') {
    score -= 5;
  }

  return score;
}

export function slopeLabel(slopeLevel) {
  switch (slopeLevel) {
    case 1:
      return '평지';
    case 2:
      return '완만한 경사';
    case 3:
      return '가파른 경사';
    default:
      return '정보 없음';
  }
}
