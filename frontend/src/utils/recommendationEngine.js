import { LOCATION_ZONES } from '../data/mockData';

// Scores each restaurant against the user's three answers and returns the top N matches.
// Mirrors the scoring logic implemented server-side in RecommendationService.java.
export function getRecommendations(restaurants, answers, topN = 3) {
  const scored = restaurants.map((restaurant) => ({
    restaurant,
    score: scoreRestaurant(restaurant, answers),
  }));

  return scored
    .sort((a, b) => b.score - a.score)
    .slice(0, topN)
    .map(({ restaurant }) => restaurant);
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
    score += restaurant.price >= 10000 ? 3 : -1;
  }

  // Q3: Location
  if (answers.location === 'main_gate') {
    score += restaurant.locationZone === LOCATION_ZONES.MAIN_GATE ? 3 : 0;
    score += restaurant.walkingTimeMinutes <= 5 ? 1 : 0;
  } else if (answers.location === 'shuttle_stop') {
    score += restaurant.locationZone === LOCATION_ZONES.SHUTTLE_STOP ? 3 : 0;
  }

  // Creator's pick: nudge toward the top when it's still a reasonable match.
  if (restaurant.creatorPick) {
    score += 4;
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
