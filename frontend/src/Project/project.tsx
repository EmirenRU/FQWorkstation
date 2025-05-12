import { useState } from 'react';
import { sendProjectData } from '../api/sendProjectData';
import { ProjectInfoPage } from './ProjectInfo';
import { AnnotationPage } from './AnnotationInfo';
import { ProjectDetailsPage } from './projectDetails';

// Типы данных

export interface ResearchTeamMember {
  fullName: string;
  workPlace: string; // TO BE ADDED SOMEDAY
  position: string;
  education: string;
  speciality: string;
  academicDegree?: string;
  academicTitle?: string;
  roleInProject: string;
  experience: string;
}

export interface EstimatedCost {
  item: string;
  quantity: number;
  pricePerUnit: number;
  total: number;
  justification: string;
}

export interface ProjectData {
  projectName: string;
  keywords: string[];
  scientificAreas: string[];
  researchDirections: string[];
  category: string;
  goals: string[];
  actuality: string[];
  novelty: string[];
  plannedResults: string[];
  scientificDivision: string;
  date: string;
  projectSupervisor: string;
  projectTasks: string;
  descriptionPlannedScientificResults: string;
  descriptionOfMethods: string;
  descriptionOfScientificFuture: string;
  futureResults: string;
  justificationOfRequestedFinancing: string;
  researchTeams: ResearchTeamMember[];
  fullNameOfSupervisor: string;
  listOfEstimatedCosts: EstimatedCost[];
}

// Компонент для управления динамическими полями
export const DynamicFieldGroup = ({
  values,
  label,
  onAdd,
  onRemove,
  onChange,
  isTextarea = false
}: {
  values: string[]  ;
  label: string;
  onAdd: () => void;
  onRemove: (index: number) => void;
  onChange: (index: number, value: string) => void;
  isTextarea?: boolean;
}) => (
  <div style={{ marginBottom: '25px' }}>
    <label className="form-label">{label}</label>
    {values.map((value, index) => (
      <div key={index} className="d-flex align-items-center mb-2">
        {isTextarea ? (
          <textarea
            value={value}
            onChange={(e) => onChange(index, e.target.value)}
            className="form-input"
            style={{ width: '90%', height: '60px' }}
          />
        ) : (
          <input
            type="text"
            value={value}
            onChange={(e) => onChange(index, e.target.value)}
            className="form-input"
            style={{ width: '90%' }}
          />
        )}
        {values.length > 1 && (
          <button
            type="button"
            onClick={() => onRemove(index)}
            className="btn btn-danger ms-2"
          >
            ×
          </button>
        )}
        <button
          type="button"
          onClick={onAdd}
          className="btn btn-secondary ms-2"
        >
          +
        </button>
      </div>
    ))}
  </div>
);



export const Project = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const [formData, setFormData] = useState<ProjectData>({
    projectName: '',
    keywords: [''],
    scientificAreas: [''],
    researchDirections: [''],
    category: '',
    goals: [''],
    actuality: [''],
    novelty: [''],
    plannedResults: [''],
    scientificDivision: '',
        date: new Date().toISOString().split('T')[0],
    projectSupervisor: '',
    projectTasks: '',
    descriptionPlannedScientificResults: '',
    descriptionOfMethods: '',
    descriptionOfScientificFuture: '',
    futureResults: '',
    justificationOfRequestedFinancing: '',
    researchTeams: [],
    fullNameOfSupervisor: '',
    listOfEstimatedCosts: []
  });

  const updateFormData = (field: keyof ProjectData, value: unknown) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const nextPage = () => setCurrentPage(prev => prev + 1);
  const prevPage = () => setCurrentPage(prev => prev - 1);

  const handleSubmit = async (e: React.FormEvent):Promise<void> => {
    console.log(formData)
    e.preventDefault();
    try {
      await sendProjectData(formData);
      alert('Данные успешно отправлены!');
    } catch (error) {
      console.error('Ошибка отправки:', error);
      alert('Произошла ошибка при отправке данных');
    }
  };

  return (
    <div className="container d-flex flex-column" style={{ marginBottom: '95px' }}>
      <form onSubmit={handleSubmit} className="form-style d-flex flex-row flex-wrap justify-content-center" style={{ maxWidth: '100%', alignItems: 'flex-start' }}>
        {currentPage === 1 && (
          <ProjectInfoPage
            data={formData}
            updateData={updateFormData}
            nextPage={nextPage}
          />
        )}

        {currentPage === 2 && (
          <AnnotationPage
            data={formData} 
            updateData={updateFormData}
            prevPage={prevPage}
            nextPage={nextPage}
          />
        )}

        {currentPage === 3 && (
          <ProjectDetailsPage
          data={formData}
          updateData={updateFormData}
          prevPage={prevPage}
          onSubmit={handleSubmit}
          />
        )}
      </form>

      {/* Индикатор прогресса */}
      <div className="d-flex justify-content-center mt-3">
        <div className="progress" style={{ width: '50%' }}>
          <div
            className="progress-bar"
            role="progressbar"
            style={{ width: `${(currentPage / 2) * 100}%` }}
            aria-valuenow={currentPage}
            aria-valuemin={1}
            aria-valuemax={2}
          >
            Шаг {currentPage} из 3
          </div>
        </div>
      </div>
    </div>
  );
};