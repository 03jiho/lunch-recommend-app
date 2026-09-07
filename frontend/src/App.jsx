import { useState } from 'react';
import QuizContainer from './components/QuizContainer';
import ResultsScreen from './components/ResultsScreen';

export default function App() {
  const [answers, setAnswers] = useState(null);

  const handleComplete = (finalAnswers) => {
    setAnswers(finalAnswers);
  };

  const handleRestart = () => {
    setAnswers(null);
  };

  return (
    <div className="min-h-screen flex flex-col">
      <header className="border-b border-slate-200 bg-white">
        <div className="max-w-5xl mx-auto px-4 py-5">
          <p className="text-xs font-medium text-slate-400 tracking-wide uppercase">
            동아대학교 &middot; 승학캠퍼스
          </p>
          <h1 className="text-xl sm:text-2xl font-semibold text-slate-900 mt-1">
            점심 뭐먹지?
          </h1>
        </div>
      </header>

      <main className="flex-1 flex items-center justify-center px-4 py-10">
        {answers ? (
          <ResultsScreen answers={answers} onRestart={handleRestart} />
        ) : (
          <QuizContainer onComplete={handleComplete} />
        )}
      </main>

      <footer className="py-6 text-center text-xs text-slate-400">
        매장명·메뉴·가격은 실제 정보를 기반으로 하나, 도보시간·경사도는 추정치입니다. 방문 전 정보를 다시 확인해주세요.
      </footer>
    </div>
  );
}
