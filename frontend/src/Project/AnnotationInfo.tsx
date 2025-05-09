import { DynamicFieldGroup, ProjectData } from "./project";

export const AnnotationPage = ({
  data,
  updateData,
  prevPage,
  nextPage,
}: {
  data: ProjectData;
  updateData: (field: keyof ProjectData, value: unknown) => void;
  prevPage: () => void;
  nextPage: () => void;
}) => {
  return (
    <div className="d-flex flex-column" style={{ width: '560px' }}>
      <h2 className="form__title__style">Аннотация</h2>

      <DynamicFieldGroup
        values={data.goals}
        label="Цели и задачи"
        onAdd={() => updateData('goals', [...data.goals, ''])}
        onRemove={(index) =>
          updateData('goals', data.goals.filter((_, i) => i !== index))
        }
        onChange={(index, value) => {
          const newGoals = [...data.goals];
          newGoals[index] = value;
          updateData('goals', newGoals);
        }}
        isTextarea
      />
{/**  goals: string[];
  actuality: string[];
  novelty: string[];
  plannedResults: string[];
  scientificDivision: string; */}
<DynamicFieldGroup
        values={data.actuality}
        label="Актуальность"
        onAdd={() => updateData('actuality', [...data.actuality, ''])}
        onRemove={(index) =>
          updateData('actuality', data.actuality.filter((_, i) => i !== index))
        }
        onChange={(index, value) => {
          const newActuality = [...data.actuality];
          newActuality[index] = value;
          updateData('actuality', newActuality);
        }}
        isTextarea
/>

<DynamicFieldGroup
        values={data.novelty}
        label="Новизна"
        onAdd={() => updateData('novelty', [...data.novelty, ''])}
        onRemove={(index) =>
          updateData('novelty', data.novelty.filter((_, i) => i !== index))
        }
        onChange={(index, value) => {
          const newNovelty = [...data.novelty];
          newNovelty[index] = value;
          updateData('novelty', newNovelty);
        }}
        isTextarea
/>

{/** 
  plannedResults: string[];
  scientificDivision: string; */}

<DynamicFieldGroup
        values={data.plannedResults}
        label="Планируемые результаты"
        onAdd={() => updateData('plannedResults', [...data.plannedResults, ''])}
        onRemove={(index) =>
          updateData('plannedResults', data.plannedResults.filter((_, i) => i !== index))
        }
        onChange={(index, value) => {
          const newPlannedResults = [...data.plannedResults];
          newPlannedResults[index] = value;
          updateData('plannedResults', newPlannedResults);
        }}
        isTextarea
/>
<label className="form-label">Название ОУП или НП</label>
          <input
            type="text"
            value={data.scientificDivision}
            onChange={(e) => updateData('scientificDivision', e.target.value)}
            className="form-input"
            style={{ width: '90%' }}
          />



      <div className="d-flex justify-content-between mt-3">
        <button
          type="button"
          onClick={prevPage}
          className="btn btn-secondary"
        >
          Назад
        </button>
        <button
          type="button"
          onClick={nextPage}
          className="btn btn-primary"
          disabled={data.goals.length === 0 || data.goals.some(g => !g.trim())}
        >
          Далее
        </button>
      </div>
    </div>
  );
};
 