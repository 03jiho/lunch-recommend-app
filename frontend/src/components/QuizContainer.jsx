import { useState } from 'react';
import { ChevronLeft } from 'lucide-react';
import { questions } from '../data/questions';
import ProgressBar from './ProgressBar';
import QuestionCard from './QuestionCard';

export default function QuizContainer({ onComplete }) {
  const [stepIndex, setStepIndex] = useState(0);
  const [answers, setAnswers] = useState({});

  const currentQuestion = questions[stepIndex];

  const handleSelect = (questionId, optionId) => {
    const nextAnswers = { ...answers, [questionId]: optionId };
    setAnswers(nextAnswers);

    if (stepIndex < questions.length - 1) {
      setStepIndex(stepIndex + 1);
    } else {
      onComplete(nextAnswers);
    }
  };

  const handleBack = () => {
    if (stepIndex > 0) {
      setStepIndex(stepIndex - 1);
    }
  };

  return (
    <div className="w-full max-w-2xl mx-auto">
      <div className="mb-6">
        <ProgressBar current={stepIndex + 1} total={questions.length} />
      </div>

      <QuestionCard question={currentQuestion} onSelect={handleSelect} />

      <div className="mt-4 h-9">
        {stepIndex > 0 && (
          <button
            type="button"
            onClick={handleBack}
            className="inline-flex items-center gap-1 text-sm font-medium text-slate-500
                       hover:text-slate-900 transition-colors"
          >
            <ChevronLeft size={16} />
            이전 질문
          </button>
        )}
      </div>
    </div>
  );
}
