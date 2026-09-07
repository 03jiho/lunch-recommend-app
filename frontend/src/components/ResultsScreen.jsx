import { useEffect, useState } from 'react';
import { RotateCcw, Loader2, ExternalLink, Clock3 } from 'lucide-react';
import { fetchRecommendations } from '../api/recommendationApi';
import { pickLeisureSpots } from '../data/leisureSpots';
import ResultCard from './ResultCard';

export default function ResultsScreen({ answers, onRestart }) {
  const [results, setResults] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    let isMounted = true;
    setIsLoading(true);

    fetchRecommendations(answers).then((data) => {
      if (isMounted) {
        setResults(data);
        setIsLoading(false);
      }
    });

    return () => {
      isMounted = false;
    };
  }, [answers]);

  if (isLoading) {
    return (
      <div className="flex flex-col items-center justify-center gap-3 py-24 text-slate-500">
        <Loader2 className="animate-spin" size={28} />
        <p className="text-sm font-medium">최적의 점심 메뉴를 계산하는 중입니다...</p>
      </div>
    );
  }

  return (
    <div className="w-full max-w-5xl mx-auto">
      <div className="text-center mb-10">
        <p className="text-sm font-medium text-slate-400 mb-2">결과 확인</p>
        <h2 className="text-2xl sm:text-3xl font-semibold text-slate-900">
          추천 맛집 TOP 3
        </h2>
        <p className="text-slate-500 mt-2">
          승학캠퍼스 주변, 선택하신 시간 · 예산 · 위치 조건에 맞춘 추천입니다.
        </p>
      </div>

      {results && results.length > 0 ? (
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 pt-3">
          {results.map((restaurant, index) => (
            <ResultCard key={restaurant.id} rank={index + 1} restaurant={restaurant} />
          ))}
        </div>
      ) : (
        <p className="text-center text-slate-500">조건에 맞는 식당을 찾지 못했습니다.</p>
      )}

      {answers.time === 'relaxed' && results && results.length > 0 && (
        <div className="mt-10 rounded-2xl border border-slate-200 bg-white p-6">
          <div className="flex items-center gap-2 mb-4">
            <Clock3 size={18} className="text-slate-400" />
            <h3 className="text-base font-semibold text-slate-900">
              시간 여유 있으시네요 — 식사 후 들르기 좋은 곳
            </h3>
          </div>
          <div className="grid gap-3 sm:grid-cols-3">
            {pickLeisureSpots(answers, results[0]).map((spot) => (
              <a
                key={spot.id}
                href={spot.mapUrl}
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center justify-between gap-2 rounded-xl border border-slate-200
                           px-4 py-3 hover:border-slate-900 transition-colors"
              >
                <div>
                  <p className="text-xs font-medium text-slate-400">{spot.category}</p>
                  <p className="text-sm font-semibold text-slate-900">{spot.name}</p>
                  <p className="text-xs text-slate-500 mt-0.5">{spot.description}</p>
                </div>
                <ExternalLink size={15} className="text-slate-400 shrink-0" />
              </a>
            ))}
          </div>
        </div>
      )}

      <div className="flex justify-center mt-10">
        <button
          type="button"
          onClick={onRestart}
          className="inline-flex items-center gap-2 rounded-xl border border-slate-300
                     bg-white px-5 py-2.5 text-sm font-medium text-slate-700
                     hover:border-slate-900 hover:text-slate-900 transition-colors"
        >
          <RotateCcw size={16} />
          다시 시작하기
        </button>
      </div>
    </div>
  );
}
