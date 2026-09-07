import { restaurants as mockRestaurants } from '../data/mockData';
import { getRecommendations as getLocalRecommendations } from '../utils/recommendationEngine';

// Calls the Spring Boot backend for recommendations. If the backend is not running
// (e.g. during pure frontend development), falls back to local mock data + client-side
// scoring so the UI remains fully usable.
export async function fetchRecommendations(answers) {
  try {
    const response = await fetch('/api/recommendations', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(answers),
    });

    if (!response.ok) {
      throw new Error(`Backend responded with status ${response.status}`);
    }

    return await response.json();
  } catch (error) {
    console.warn('[recommendationApi] Falling back to local mock data:', error.message);
    return getLocalRecommendations(mockRestaurants, answers, 3);
  }
}
