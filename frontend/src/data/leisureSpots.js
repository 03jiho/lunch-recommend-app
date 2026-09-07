import { LOCATION_ZONES } from './mockData';

// Real nearby spots for killing time after a relaxed (1+ hour) lunch. Sourced from
// Kakao Map place data — same verification standard as mockData.js. Multiple PC방
// candidates exist so the suggestion can match wherever the recommended restaurant
// actually is, instead of always pointing to the same one.
export const leisureSpots = [
  {
    id: 1,
    name: '오엑스 피시 동아대점',
    category: 'PC방',
    locationZone: LOCATION_ZONES.MAIN_GATE,
    walkingTimeMinutes: 2,
    description: '정문 바로 앞, 도보 2분',
    mapUrl: 'https://map.kakao.com/?q=오엑스 피시 동아대점 부산 사하구 낙동대로 551',
  },
  {
    id: 2,
    name: '레벨업 PC',
    category: 'PC방',
    locationZone: LOCATION_ZONES.SHUTTLE_STOP,
    walkingTimeMinutes: 3,
    description: '맥도날드·사하10 탑승장 건물, 도보 3분',
    mapUrl: 'https://map.kakao.com/?q=레벨업 PC 부산 사하구 낙동대로 548',
  },
  {
    id: 3,
    name: '티티PC 하단점',
    category: 'PC방',
    locationZone: LOCATION_ZONES.SHUTTLE_STOP,
    walkingTimeMinutes: 12,
    description: '하단역 방면, 도보 12분',
    mapUrl: 'https://map.kakao.com/?q=티티PC 하단점 부산 사하구 낙동대로 493',
  },
  {
    id: 4,
    name: '아스트커피',
    category: '카페',
    locationZone: LOCATION_ZONES.MAIN_GATE,
    walkingTimeMinutes: 2,
    description: '동아분식·리코리코 골목, 도보 2분',
    mapUrl: 'https://map.kakao.com/?q=아스트커피 부산 사하구 낙동대로516번길 6',
  },
  {
    id: 5,
    name: '배스킨라빈스 부산동대승학점',
    category: '아이스크림',
    locationZone: LOCATION_ZONES.MAIN_GATE,
    walkingTimeMinutes: 2,
    description: '정문 바로 앞, 도보 2분',
    mapUrl: 'https://map.kakao.com/?q=배스킨라빈스 부산동대승학점 부산 사하구 낙동대로550번길 1',
  },
];

// Picks one spot per category, preferring a match on location zone and then the
// closest walking time to the top-recommended restaurant (so e.g. the PC방 pick
// follows whichever end of the SHUTTLE_STOP strip the restaurant actually sits on).
//
// Exception: for the "1시간 이상 + 6,000원 이하" combo (a quick, budget-friendly
// meal like 동아분식/밉상짬뽕 followed by killing time before class), always
// suggest 레벨업 PC specifically rather than the nearest-match PC방.
export function pickLeisureSpots(answers, topRestaurant) {
  const categories = ['PC방', '카페', '아이스크림'];
  const targetZone =
    answers.location === 'main_gate' ? LOCATION_ZONES.MAIN_GATE : LOCATION_ZONES.SHUTTLE_STOP;
  const targetWalk = topRestaurant?.walkingTimeMinutes ?? 0;

  return categories.map((category) => {
    if (category === 'PC방' && answers.time === 'relaxed' && answers.budget === 'budget') {
      return leisureSpots.find((spot) => spot.name === '레벨업 PC');
    }

    const candidates = leisureSpots.filter((spot) => spot.category === category);
    const zoneMatches = candidates.filter((spot) => spot.locationZone === targetZone);
    const pool = zoneMatches.length > 0 ? zoneMatches : candidates;

    return pool.reduce((closest, spot) =>
      Math.abs(spot.walkingTimeMinutes - targetWalk) < Math.abs(closest.walkingTimeMinutes - targetWalk)
        ? spot
        : closest
    );
  });
}
