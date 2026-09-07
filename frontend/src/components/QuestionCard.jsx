export default function QuestionCard({ question, onSelect }) {
  const Icon = question.icon;

  return (
    <div className="bg-white rounded-2xl shadow-sm border border-slate-200 p-6 sm:p-8">
      <div className="flex items-center gap-3 mb-1">
        <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-slate-900 text-white">
          <Icon size={20} />
        </div>
        <span className="text-sm font-medium text-slate-400">{question.subtitle}</span>
      </div>

      <h2 className="text-xl sm:text-2xl font-semibold text-slate-900 mt-3 mb-6">
        {question.title}
      </h2>

      <div
        className={`grid gap-3 ${
          question.options.length === 3 ? 'sm:grid-cols-3' : 'sm:grid-cols-2'
        }`}
      >
        {question.options.map((option) => (
          <button
            key={option.id}
            type="button"
            onClick={() => onSelect(question.id, option.id)}
            className="group text-left rounded-xl border border-slate-200 p-5 transition-all
                       hover:border-slate-900 hover:shadow-md active:scale-[0.99]
                       focus:outline-none focus:ring-2 focus:ring-slate-900 focus:ring-offset-2"
          >
            <p className="font-semibold text-slate-900 group-hover:text-slate-900">
              {option.label}
            </p>
            <p className="mt-1 text-sm text-slate-500">{option.description}</p>
          </button>
        ))}
      </div>
    </div>
  );
}
