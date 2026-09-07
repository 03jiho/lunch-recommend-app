import { MapPin, Clock, Wallet, ExternalLink } from 'lucide-react';
import { slopeLabel } from '../utils/recommendationEngine';

const BADGE_STYLES = {
  '빠른 서비스': 'bg-blue-50 text-blue-700 border-blue-200',
  가성비: 'bg-emerald-50 text-emerald-700 border-emerald-200',
  '평지 접근': 'bg-slate-100 text-slate-700 border-slate-200',
  '특별한 한 끼': 'bg-amber-50 text-amber-700 border-amber-200',
  '여유로운 식사': 'bg-purple-50 text-purple-700 border-purple-200',
  '단체모임 추천': 'bg-rose-50 text-rose-700 border-rose-200',
};

export default function ResultCard({ rank, restaurant }) {
  return (
    <div className="relative bg-white rounded-2xl border border-slate-200 shadow-sm p-6 flex flex-col gap-4">
      <div className="absolute -top-3 -left-3 flex h-9 w-9 items-center justify-center rounded-full bg-slate-900 text-white text-sm font-bold shadow">
        {rank}
      </div>

      <div>
        <p className="text-xs font-medium uppercase tracking-wide text-slate-400">
          {restaurant.category}
        </p>
        <h3 className="text-lg font-semibold text-slate-900 mt-0.5">{restaurant.name}</h3>
      </div>

      <div className="flex flex-wrap gap-2">
        {restaurant.badges.map((badge) => (
          <span
            key={badge}
            className={`text-xs font-medium px-2.5 py-1 rounded-full border ${
              BADGE_STYLES[badge] ?? 'bg-slate-100 text-slate-700 border-slate-200'
            }`}
          >
            {badge}
          </span>
        ))}
      </div>

      <div className="grid grid-cols-2 gap-3 text-sm text-slate-600">
        <div className="flex items-center gap-2">
          <MapPin size={16} className="text-slate-400 shrink-0" />
          <span>
            도보 {restaurant.walkingTimeMinutes}분 &middot; {slopeLabel(restaurant.slopeLevel)}
          </span>
        </div>
        <div className="flex items-center gap-2">
          <Clock size={16} className="text-slate-400 shrink-0" />
          <span>조리 {restaurant.avgPrepTime}분</span>
        </div>
        <div className="flex items-center gap-2 col-span-2">
          <Wallet size={16} className="text-slate-400 shrink-0" />
          <span>{restaurant.priceRange}</span>
        </div>
      </div>

      <div className="border-t border-slate-100 pt-4">
        <p className="text-xs font-medium text-slate-400 mb-0.5">대표 메뉴</p>
        <p className="text-sm font-medium text-slate-800">{restaurant.signatureMenu}</p>
      </div>

      <a
        href={restaurant.mapUrl}
        target="_blank"
        rel="noopener noreferrer"
        className="mt-auto inline-flex items-center justify-center gap-1.5 rounded-xl
                   bg-slate-900 text-white text-sm font-medium py-2.5 px-4
                   hover:bg-slate-800 transition-colors"
      >
        지도에서 보기
        <ExternalLink size={15} />
      </a>
    </div>
  );
}
