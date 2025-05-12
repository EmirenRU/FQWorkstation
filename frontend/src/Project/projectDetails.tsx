 

import { MouseEvent, useCallback } from "react";
import { ProjectData, ResearchTeamMember, EstimatedCost } from "./project";

export const ProjectDetailsPage = ({
    data,
    updateData,
    prevPage,
    onSubmit
}: {
    data: ProjectData;
    updateData: (field: keyof ProjectData, value: unknown) => void;
    prevPage: () => void;
    onSubmit:  (e: React.FormEvent) => Promise<void>;
}) => {
    // Мемоизированные компоненты полей
    const TextAreaFieldGroup = useCallback(({
        value,
        label,
        onChange,
        height = '150px',
    }: {
        value: string;
        label: string;
        onChange: (value: string) => void;
        height?: string;
    }) => (
        <div className="form-group" style={{ marginBottom: '25px' }}>
            <label className="form-label">{label}</label>
            <textarea
                value={value}
                onChange={(e) => onChange(e.target.value)}
                className="form-input w-100"
                style={{ height }}
            />
        </div>
    ), []);

    const InputField = useCallback(({
        value,
        label,
        onChange,
        type = 'text'
    }: {
        value: string;
        label: string;
        onChange: (value: string) => void;
        type?: string;
    }) => (
        <div className="form-group" style={{ marginBottom: '25px' }}>
            <label className="form-label">{label}</label>
            <input
                type={type}
                value={value}
                onChange={(e) => onChange(e.target.value)}
                className="form-input w-100"
            />
        </div>
    ), []);

    // Мемоизированные обработчики
    const handleRemoveTeamMember = useCallback((index: number): void => {
        const updatedTeamMembers = [...data.researchTeams];
        updatedTeamMembers.splice(index, 1);
        updateData('researchTeams', updatedTeamMembers);
    }, [data.researchTeams, updateData]);

    const handleAddTeamMember = useCallback((event: MouseEvent<HTMLButtonElement>): void => {
        event.preventDefault();
        const newMember: ResearchTeamMember = {
            fullName: '',
            position: '',
            education: '',
            speciality: '',
            roleInProject: '',
            experience: '',
            workPlace: ''
        };
        updateData('researchTeams', [...data.researchTeams, newMember]);
    }, [data.researchTeams, updateData]);

    const handleTeamMemberChange = useCallback((index: number, field: keyof ResearchTeamMember, value: string) => {
        const updatedTeamMembers = [...data.researchTeams];
        updatedTeamMembers[index] = {
            ...updatedTeamMembers[index],
            [field]: value
        };
        updateData('researchTeams', updatedTeamMembers);
    }, [data.researchTeams, updateData]);

    const handleRemoveCostItem = useCallback((index: number): void => {
        const updatedCostItems = [...data.listOfEstimatedCosts];
        updatedCostItems.splice(index, 1);
        updateData('listOfEstimatedCosts', updatedCostItems);
    }, [data.listOfEstimatedCosts, updateData]);

    const handleAddCostItem = useCallback((event: MouseEvent<HTMLButtonElement>): void => {
        event.preventDefault();
        const newCostItem: EstimatedCost = {
            item: '',
            quantity: 0,
            pricePerUnit: 0,
            total: 0,
            justification: ''
        };
        updateData('listOfEstimatedCosts', [...data.listOfEstimatedCosts, newCostItem]);
    }, [data.listOfEstimatedCosts, updateData]);

    const handleCostItemChange = useCallback((index: number, field: keyof EstimatedCost, value: string | number) => {
        const updatedCostItems = [...data.listOfEstimatedCosts];
        const updatedItem = {
            ...updatedCostItems[index],
            [field]: value
        };

        if (field === 'quantity' || field === 'pricePerUnit') {
            updatedItem.total = Number(updatedItem.quantity) * Number(updatedItem.pricePerUnit);
        }

        updatedCostItems[index] = updatedItem;
        updateData('listOfEstimatedCosts', updatedCostItems);
    }, [data.listOfEstimatedCosts, updateData]);


    return (
        <div className="d-flex flex-column" style={{ width: '100%',   }}>
            <h2 className="form__title__style">Детали проекта</h2>

            {/* Основные поля */}
            <InputField
                label="Дата"
                value={data.date}
                onChange={(value) => updateData('date', value)}
                type="date"
            />

            <InputField
                label="Руководитель проекта"
                value={data.projectSupervisor}
                onChange={(value) => updateData('projectSupervisor', value)}
            />

            {/* Текстовые области */}
            <TextAreaFieldGroup
                label="Задачи проекта"
                value={data.projectTasks}
                onChange={(value) => updateData('projectTasks', value)}
            />

            <TextAreaFieldGroup
                label="Описание предлагаемого научного исследования"
                value={data.descriptionPlannedScientificResults}
                onChange={(value) => updateData('descriptionPlannedScientificResults', value)}
            />

            <TextAreaFieldGroup
                label="Описание научных подходов и методов"
                value={data.descriptionOfMethods}
                onChange={(value) => updateData('descriptionOfMethods', value)}
            />

            <TextAreaFieldGroup
                label="Описание научного задела"
                value={data.descriptionOfScientificFuture}
                onChange={(value) => updateData('descriptionOfScientificFuture', value)}
            />

            <TextAreaFieldGroup
                label="Ожидаемые результаты научного исследования"
                value={data.futureResults}
                onChange={(value) => updateData('futureResults', value)}
                height="200px"
            />

            <TextAreaFieldGroup
                label="Обоснование запрашиваемого финансирования"
                value={data.justificationOfRequestedFinancing}
                onChange={(value) => updateData('justificationOfRequestedFinancing', value)}
            />

            {/* Состав научного коллектива */}
            <div className="form-group">
                <label className="form-label">Состав научного коллектива</label>
{data.researchTeams.map((member, index) => (
  <div key={`member-${index}`} className="card mb-3 p-3">
                    
                        <div className="d-flex justify-content-between mb-2">
                            <h5>Участник #{index + 1}</h5>
                            <button
                                type="button"
                                onClick={() => handleRemoveTeamMember(index)}
                                className="btn btn-danger btn-sm"
                            >
                                Удалить
                            </button>
                        </div>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                            <label className="mr-3">ФИО</label>
                                <input
                                    type="text"
                                    value={member.fullName}
                                    onChange={(e) => handleTeamMemberChange(index, 'fullName', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="mr-3">Должность</label>
                                <input
                                    type="text"
                                    value={member.position}
                                    onChange={(e) => handleTeamMemberChange(index, 'position', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                            <label className="mr-3">Образование</label>
                                <input
                                    type="text"
                                    value={member.education}
                                    onChange={(e) => handleTeamMemberChange(index, 'education', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="mr-3">Специальность</label>
                                <input
                                    type="text"
                                    value={member.speciality}
                                    onChange={(e) => handleTeamMemberChange(index, 'speciality', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                            <label className="mr-3">Ученая степень</label>
                                <input
                                    type="text"
                                    value={member.academicDegree || ''}
                                    onChange={(e) => handleTeamMemberChange(index, 'academicDegree', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                            <div className="col-md-6 mb-3">
                                <label className="mr-3">Ученое звание</label>
                                <input
                                    type="text"
                                    value={member.academicTitle || ''}
                                    onChange={(e) => handleTeamMemberChange(index, 'academicTitle', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-6 mb-3">
                            <label className="mr-3">Роль в проекте</label>
                                <input
                                    type="text"
                                    value={member.roleInProject}
                                    onChange={(e) => handleTeamMemberChange(index, 'roleInProject', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                        <div className="col-md-6 mb-3">
                            <label className="mr-3">Рабочее место</label>
                                <input
                                    type="text"
                                    value={member.workPlace}
                                    onChange={(e) => handleTeamMemberChange(index, 'workPlace', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                            <div className="col-md-6 mb-3">
                            <label className="mr-3">Опыт работы</label>
                                <input
                                    type="text"
                                    value={member.experience}
                                    onChange={(e) => handleTeamMemberChange(index, 'experience', e.target.value)}
                                    className="form-input"
                                />
                            </div>
                        </div>
                    </div>
                ))}
                <button
                    type="button"
                    onClick={handleAddTeamMember}
                    className="btn btn-secondary"
                >
                    Добавить участника
                </button>
            </div>

            {/* ФИО руководителя */}
            <InputField
                label="ФИО руководителя принимающего подразделения"
                value={data.fullNameOfSupervisor}
                onChange={(value) => updateData('fullNameOfSupervisor', value)}
            />

            {/* Смета расходов */}
            <div className="form-group d-flex flex-wrap" style={{maxWidth:"100%", width:"100%", overflow: "scroll"}}>
                <label className="form-label">Смета расходов</label>
                <table className="table">
                    <thead>
                        <tr>
                            <th>Наименование</th>
                            <th>Количество</th>
                            <th>Цена за единицу</th>
                            <th>Итого</th>
                            <th>Обоснование</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    {data.listOfEstimatedCosts.map((cost, index) => (
                            <tr key={`cost-${index}`}>
                                <td>
                                    <input
                                        type="text"
                                        value={cost.item}
                                        onChange={(e) => handleCostItemChange(index, 'item', e.target.value)}
                                        className="form-input"
                                    />
                                </td>
                                <td>
                                    <input
                                        type="number"
                                        value={cost.quantity}
                                        onChange={(e) => handleCostItemChange(index, 'quantity', e.target.value)}
                                        className="form-input"
                                    />
                                </td>
                                <td>
                                    <input
                                        type="number"
                                        value={cost.pricePerUnit}
                                        onChange={(e) => handleCostItemChange(index, 'pricePerUnit', e.target.value)}
                                        className="form-input"
                                    />
                                </td>
                                <td>{cost.total}</td>
                                <td>
                                    <input
                                        type="text"
                                        value={cost.justification}
                                        onChange={(e) => handleCostItemChange(index, 'justification', e.target.value)}
                                        className="form-input"
                                    />
                                </td>
                                <td>
                                    <button
                                        type="button"
                                        onClick={() => handleRemoveCostItem(index)}
                                        className="btn btn-danger btn-sm"
                                    >
                                        ×
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <button
                    type="button"
                    onClick={handleAddCostItem}
                    className="btn btn-secondary"
                >
                    Добавить статью расходов
                </button>
            </div>

            {/* Навигация */}
            <div className="d-flex justify-content-between mt-4">
                <button
                    type="button"
                    onClick={prevPage}
                    className="btn btn-secondary"
                >
                    Назад
                </button>
                <button
                    type="submit"
                    onClick={onSubmit}
                    className="btn btn-primary"
                >
                    Отправить
                </button>
            </div>
        </div>
    );
};