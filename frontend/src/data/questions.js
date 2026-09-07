import { Clock, Wallet, MapPin } from 'lucide-react';

export const questions = [
  {
    id: 'time',
    title: 'Q1. 점심시간이 얼마나 있으신가요?',
    subtitle: '시간 제한',
    icon: Clock,
    options: [
      {
        id: 'fast',
        label: '15분 초스피드 식사',
        description: '빠르게 먹고 수업 · 실험실로 바로 복귀',
      },
      {
        id: 'relaxed',
        label: '1시간 이상 여유로운 식사',
        description: '앉아서 천천히, 여유롭게 즐기는 한 끼',
      },
    ],
  },
  {
    id: 'budget',
    title: 'Q2. 예산은 어느 정도인가요?',
    subtitle: '예산',
    icon: Wallet,
    options: [
      {
        id: 'budget',
        label: '6,000원 이하',
        description: '가성비 좋은 실속형 한 끼',
      },
      {
        id: 'gourmet',
        label: '10,000원 이상',
        description: '나를 위한 특별한 한 끼',
      },
    ],
  },
  {
    id: 'location',
    title: 'Q3. 어디서 드시고 싶으신가요?',
    subtitle: '위치 & 경사도',
    icon: MapPin,
    options: [
      {
        id: 'main_gate',
        label: '정문 근처',
        description: '정문에서 가까운 곳으로',
      },
      {
        id: 'no_slope',
        label: '오르막길 절대 불가',
        description: '최소한의 걷기, 평지 위주로',
      },
      {
        id: 'shuttle_stop',
        label: '순환버스 탑승장 근처',
        description: '탑승 전후로 빠르게 식사',
      },
    ],
  },
];
