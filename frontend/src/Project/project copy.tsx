import { useState } from 'react';
import { sendProjectData } from '../api/sendProjectData';


export interface ProjectData {
    projectName: string,
    keywords: Array<string>,
    scientificAreas: Array<string>,
    researchDirections: Array<string>,
    category: string,
    goals: Array<string>,
    actuality: Array<string>,
    novelty: Array<string>,
    plannedResults: Array<string>,
    scientificDivision: string
 }


export const Project = () => {
  // Состояния для каждого раздела
  const [projectName, setProjectName] = useState('');
  const [keywords, setKeywords] = useState(['']);
  const [scientificAreas, setScientificAreas] = useState(['']);
  const [researchDirections, setResearchDirections] = useState(['']);
  const [category, setCategory] = useState('');
  const [goals, setGoals] = useState(['']);
  const [actuality, setActuality] = useState(['']);
  const [novelty, setNovelty] = useState(['']);
  const [plannedResults, setPlannedResults] = useState(['']);
  const [scientificDivision, setScientificDivision] = useState('');

  // Функции для добавления полей
  const addKeyword = () => setKeywords([...keywords, '']);
  const addScientificArea = () => setScientificAreas([...scientificAreas, '']);
  const addResearchDirection = () => setResearchDirections([...researchDirections, '']);
  const addGoal = () => setGoals([...goals, '']);
  const addActuality = () => setActuality([...actuality, '']);
  const addNovelty = () => setNovelty([...novelty, '']);
  const addPlannedResult = () => setPlannedResults([...plannedResults, '']);

  // Функции для удаления полей
  const removeKeyword = (index: number) => {
    const newKeywords = [...keywords];
    newKeywords.splice(index, 1);
    setKeywords(newKeywords);
  };

  const removeScientificArea = (index: number) => {
    const newAreas = [...scientificAreas];
    newAreas.splice(index, 1);
    setScientificAreas(newAreas);
  };

  const removeResearchDirection = (index: number) => {
    const newDirections = [...researchDirections];
    newDirections.splice(index, 1);
    setResearchDirections(newDirections);
  };

  const removeGoal = (index: number) => {
    const newGoals = [...goals];
    newGoals.splice(index, 1);
    setGoals(newGoals);
  };

  const removeActuality = (index: number) => {
    const newActuality = [...actuality];
    newActuality.splice(index, 1);
    setActuality(newActuality);
  };

  const removeNovelty = (index: number) => {
    const newNovelty = [...novelty];
    newNovelty.splice(index, 1);
    setNovelty(newNovelty);
  };

  const removePlannedResult = (index: number) => {
    const newResults = [...plannedResults];
    newResults.splice(index, 1);
    setPlannedResults(newResults);
  };

  // Функции для обновления значений полей
  const updateKeyword = (index: number, value: string) => {
    const newKeywords = [...keywords];
    newKeywords[index] = value;
    setKeywords(newKeywords);
  };

  const updateScientificArea = (index: number, value: string) => {
    const newAreas = [...scientificAreas];
    newAreas[index] = value;
    setScientificAreas(newAreas);
  };

  const updateResearchDirection = (index: number, value: string) => {
    const newDirections = [...researchDirections];
    newDirections[index] = value;
    setResearchDirections(newDirections);
  };

  const updateGoal = (index: number, value: string) => {
    const newGoals = [...goals];
    newGoals[index] = value;
    setGoals(newGoals);
  };

  const updateActuality = (index: number, value: string) => {
    const newActuality = [...actuality];
    newActuality[index] = value;
    setActuality(newActuality);
  };

  const updateNovelty = (index: number, value: string) => {
    const newNovelty = [...novelty];
    newNovelty[index] = value;
    setNovelty(newNovelty);
  };

  const updatePlannedResult = (index: number, value: string) => {
    const newResults = [...plannedResults];
    newResults[index] = value;
    setPlannedResults(newResults);
  };



 async function handleSubmit   (e: React.FormEvent)  {
    e.preventDefault();

    const projectData: ProjectData = {
        projectName: projectName,
        keywords: keywords,
        scientificAreas: scientificAreas,
        researchDirections: researchDirections,
        category: category,
        goals: goals,
        actuality: actuality,
        novelty: novelty,
        plannedResults: plannedResults,
        scientificDivision: scientificDivision
    }
    sendProjectData(projectData)
   
  } 

  return (
    <div className="container d-flex flex-column" style={{ marginBottom: "95px" }}> 
      <form onSubmit={handleSubmit} className="form-style d-flex flex-row flex-wrap justify-content-center" style={{ maxWidth: "100%", alignItems:"flex-start"}}>
        
        <div className="d-flex flex-column mr-auto" style={{ width: "460px", height: "100%"}}>
          <h2 className="form__title__style">Информация о проекте</h2>
          <div className="children__style" style={{ minWidth: "250px" }}>
            <label className="form-label">Название проекта</label>
            <input 
              type="text" 
              value={projectName}
              onChange={(e) => setProjectName(e.target.value)}
              className="form-input" 
              style={{ width: "90%", marginBottom:"25px" }} 
            />
            <div style={{ marginBottom:"25px" }}>
            <label className="form-label">Ключевые слова</label>
            {keywords.map((keyword, index) => (
              <div key={index} className="d-flex align-items-center mb-2">
                <input
                  type="text"
                  value={keyword}
                  onChange={(e) => updateKeyword(index, e.target.value)}
                  className="form-input"
                  style={{ width: "90%" }}
                />
                {keywords.length > 1 && (
                  <button
                    type="button"
                    onClick={() => removeKeyword(index)}
                    className="btn btn-danger ms-2"
                  >
                    ×
                  </button>
                )}
                <button 
                  type="button" 
                  onClick={addKeyword} 
                  className="btn btn-secondary ms-2"
                >
                  +
                </button>
              </div>
            ))}
            </div>
            <div style={{ marginBottom:"25px" }}>
            <label className="form-label">Области наук</label>
            {scientificAreas.map((area, index) => (
              <div key={index} className="d-flex align-items-center mb-2">
                <input
                  value={area}
                  onChange={(e) => updateScientificArea(index, e.target.value)}
                  className="form-input"
                  style={{ width: "90%", height: "60px" }}
                />
                {scientificAreas.length > 1 && (
                  <button
                    type="button"
                    onClick={() => removeScientificArea(index)}
                    className="btn btn-danger ms-2"
                  >
                    ×
                  </button>
                )}
                <button 
                  type="button" 
                  onClick={addScientificArea} 
                  className="btn btn-secondary ms-2"
                >
                  +
                </button>
              </div>
            ))}
            </div>
            
            <div style={{ marginBottom:"25px" }}>
            <label className="form-label">Направления исследования</label>
            {researchDirections.map((direction, index) => (
              <div key={index} className="d-flex align-items-center mb-2">
                <input
                  type="text"
                  value={direction}
                  onChange={(e) => updateResearchDirection(index, e.target.value)}
                  className="form-input"
                  style={{ width: "90%" }}
                />
                {researchDirections.length > 1 && (
                  <button
                    type="button"
                    onClick={() => removeResearchDirection(index)}
                    className="btn btn-danger ms-2"
                  >
                    ×
                  </button>
                )}
                <button 
                  type="button" 
                  onClick={addResearchDirection} 
                  className="btn btn-secondary ms-2"
                >
                  +
                </button>
              </div>
            ))}
            </div>
            
            <label className="form-label mt-3">Категория проекта</label>
            <input 
              type="text" 
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              className="form-input" 
              style={{ width: "90%", marginBottom: "40px" }} 
            />
          </div>
        </div>
        
        <div className="d-flex flex-column mb-5" style={{ width: "460px" }}>
          <h2 className="form__title__style">Аннотация</h2>
          
          <div style={{ marginBottom:"25px" }}>
          <label className="form-label">Цели и задачи</label>
          {goals.map((goal, index) => (
            <div key={index} className="d-flex align-items-center mb-2">
              <input
                value={goal}
                onChange={(e) => updateGoal(index, e.target.value)}
                className="form-input"
                style={{ width: "90%" }}
              />
              {goals.length > 1 && (
                <button
                  type="button"
                  onClick={() => removeGoal(index)}
                  className="btn btn-danger ms-2"
                >
                  ×
                </button>
              )}
              <button 
                type="button" 
                onClick={addGoal} 
                className="btn btn-secondary ms-2"
              >
                +
              </button>
            </div>
          ))}
          </div>
          
          <div style={{ marginBottom:"25px" }}>
          <label className="form-label mt-3">Актуальность</label>
          {actuality.map((item, index) => (
            <div key={index} className="d-flex align-items-center mb-2">
              <input
                value={item}
                onChange={(e) => updateActuality(index, e.target.value)}
                className="form-input"
                style={{ width: "90%" }}
              />
              {actuality.length > 1 && (
                <button
                  type="button"
                  onClick={() => removeActuality(index)}
                  className="btn btn-danger ms-2"
                >
                  ×
                </button>
              )}
              <button 
                type="button" 
                onClick={addActuality} 
                className="btn btn-secondary ms-2"
              >
                +
              </button>
            </div>
          ))}
          </div>

          <div style={{ marginBottom:"25px" }}>
          <label className="form-label mt-3">Научная новизна</label>
          {novelty.map((item, index) => (
            <div key={index} className="d-flex align-items-center mb-2">
              <input
                value={item}
                onChange={(e) => updateNovelty(index, e.target.value)}
                className="form-input"
                style={{ width: "90%" }}
              />
              {novelty.length > 1 && (
                <button
                  type="button"
                  onClick={() => removeNovelty(index)}
                  className="btn btn-danger ms-2"
                >
                  ×
                </button>
              )}
              <button 
                type="button" 
                onClick={addNovelty} 
                className="btn btn-secondary ms-2"
              >
                +
              </button>
            </div>
          ))}
          </div>
          
          <div style={{ marginBottom:"25px" }}>
          <label className="form-label mt-3">Планируемые результаты</label>
          {plannedResults.map((result, index) => (
            <div key={index} className="d-flex align-items-center mb-2">
              <input
                value={result}
                onChange={(e) => updatePlannedResult(index, e.target.value)}
                className="form-input"
                style={{ width: "90%" }}
              />
              {plannedResults.length > 1 && (
                <button
                  type="button"
                  onClick={() => removePlannedResult(index)}
                  className="btn btn-danger ms-2"
                >
                  ×
                </button>
              )}
              <button 
                type="button" 
                onClick={addPlannedResult} 
                className="btn btn-secondary ms-2"
              >
                +
              </button>
            </div>
          ))}
          </div>
          <span style={{ width: "100%", borderTop: "1px solid gray", marginBottom: "40px" }}></span>
          <label className="form-label">Название ОУП или НП</label>
          <textarea 
            value={scientificDivision}
            onChange={(e) => setScientificDivision(e.target.value)}
            className="form-input" 
            style={{ width: "100%", height: "165px" }}
          />
        </div>
        
        <button type="submit" className="btn primary-btn form-button" style={{ color: "#fff" }}>
          Отправить
        </button>
      </form>
    </div>
  );
};