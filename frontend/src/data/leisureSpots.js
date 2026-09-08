import { LOCATION_ZONES } from './mockData';

// Real nearby spots for killing time after a relaxed (1+ hour) lunch. Sourced from
// Kakao Map place data — same verification standard as mockData.js.
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
  {
    id: 6,
    name: '카페순덕',
    category: '카페',
    locationZone: LOCATION_ZONES.SHUTTLE_STOP,
    walkingTimeMinutes: 12,
    description: '동아대 인근 정원 감성 카페, 도보 12분',
    mapUrl: 'https://map.kakao.com/?q=카페순덕 부산 사하구 낙동남로1423번길 70-1',
  },
  {
    id: 7,
    name: '배스킨라빈스 부산하단점',
    category: '아이스크림',
    locationZone: LOCATION_ZONES.SHUTTLE_STOP,
    walkingTimeMinutes: 14,
    description: '하단역 2번 출구 앞, 도보 3분',
    mapUrl: 'https://map.kakao.com/?q=배스킨라빈스 부산하단점 부산 사하구 낙동남로 1388',
  },
];

// Picks one PC방·카페·아이스크림 per location answer. Each is a fixed pick keyed
// on location alone: 정문 근처를 골랐으면 레벨업 PC·아스트커피·배스킨라빈스
// 부산동대승학점, 사하10 탑승장 근처를 골랐으면 티티PC 하단점·카페순덕·배스킨
// 라빈스 부산하단점.
const LEISURE_PICKS_BY_LOCATION = {
  main_gate: ['레벨업 PC', '아스트커피', '배스킨라빈스 부산동대승학점'],
  shuttle_stop: ['티티PC 하단점', '카페순덕', '배스킨라빈스 부산하단점'],
};

export function pickLeisureSpots(answers) {
  const names = LEISURE_PICKS_BY_LOCATION[answers.location] ?? LEISURE_PICKS_BY_LOCATION.main_gate;
  return names.map((name) => leisureSpots.find((spot) => spot.name === name));
}
