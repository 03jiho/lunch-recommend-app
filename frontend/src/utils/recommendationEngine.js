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

  // Avoid showing two restaurants of the same category (e.g. two 돈카츠 places) —
  // once a category is taken, skip further matches so the higher-scoring one wins.
  const seenCategories = new Set();
  const picks = [];

  for (const { restaurant } of scored) {
    if (seenCategories.has(restaurant.category)) continue;
    seenCategories.add(restaurant.category);
    picks.push(restaurant);
    if (picks.length === topN) break;
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
