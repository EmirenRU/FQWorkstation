import { DynamicFieldGroup, ProjectData } from "./project";

export const ProjectInfoPage = ({
  data,
  updateData,
  nextPage
}: {
  data: ProjectData;
  updateData: (field: keyof ProjectData, value: unknown) => void;
  nextPage: () => void;
}) => {
  // Обработчики для динамических полей
  const handleAddKeyword = () => updateData('keywords', [...data.keywords, '']);
  const handleRemoveKeyword = (index: number) =>
    updateData(
      'keywords',
      data.keywords.filter((_, i) => i !== index)
    );
  const handleKeywordChange = (index: number, value: string) => {
    const newKeywords = [...data.keywords];
    newKeywords[index] = value;
    updateData('keywords', newKeywords);
  };

  // Аналогичные обработчики для других динамических полей...
  
  return (
    <div className="d-flex flex-column" style={{ width: '560px' }}>
      <h2 className="form__title__style">Информация о проекте</h2>
      <div className="children__style" style={{ minWidth: '250px' }}>
        <div style={{ marginBottom: '25px' }}>
          <label className="form-label">Название проекта</label>
          <input
            type="text"
            value={data.projectName}
            onChange={(e) => updateData('projectName', e.target.value)}
            className="form-input"
            style={{ width: '100%' }}
          />
        </div>

        <DynamicFieldGroup
          values={data.keywords}
          label="Ключевые слова"
          onAdd={handleAddKeyword}
          onRemove={handleRemoveKeyword}
          onChange={handleKeywordChange}
        />

        <DynamicFieldGroup
          values={data.scientificAreas}
          label="Области наук"
          onAdd={() => updateData('scientificAreas', [...data.scientificAreas, ''])}
          onRemove={(index) =>
            updateData(
              'scientificAreas',
              data.scientificAreas.filter((_, i) => i !== index)
            )
          }
          onChange={(index, value) => {
            const newAreas = [...data.scientificAreas];
            newAreas[index] = value;
            updateData('scientificAreas', newAreas);
          }}
          isTextarea
        />

        <DynamicFieldGroup
          values={data.researchDirections}
          label="Направления исследования"
          onAdd={() => updateData('researchDirections', [...data.researchDirections, ''])}
          onRemove={(index) =>
            updateData(
              'researchDirections',
              data.researchDirections.filter((_, i) => i !== index)
            )
          }
          onChange={(index, value) => {
            const newDirections = [...data.researchDirections];
            newDirections[index] = value;
            updateData('researchDirections', newDirections);
          }}
        />

        <div style={{ marginBottom: '25px' }}>
          <label className="form-label">Категория проекта</label>
          <select
            value={data.category}
            onChange={(e) => updateData('category', e.target.value)}
            className="form-input"
            style={{ width: '100%' }}
          >
            <option value="">Выберите категорию</option>
            <option value="A">Категория A</option>
            <option value="B">Категория B</option>
          </select>
        </div>

        <button
          type="button"
          onClick={nextPage}
          className="btn btn-primary"
          disabled={!data.projectName || !data.category}
        >
          Далее
        </button>
      </div>
    </div>
  );
};