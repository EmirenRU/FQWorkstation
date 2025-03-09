import { FormWrapper } from "../FormWrapper/FormWrapper";

type TeachersFieldsData = {
    lecturersName: string,
    academicPosition: string,
    position: string,
    departmentName: string,
    isScientificSupervisor: boolean,
    isConsultant: boolean,
}

type TeachersFormProps = TeachersFieldsData &{

    updateFields: (fields: Partial<TeachersFieldsData>) => void
}




export function TeachersData({lecturersName, academicPosition, position, departmentName, isScientificSupervisor, isConsultant, updateFields}: TeachersFormProps) {

    const handleConsultantChange = (e: React.ChangeEvent<HTMLInputElement>, position:string) => {
        const isChecked = e.target.checked;
        console.log(isChecked, e.target)
        position === "consultant" ? updateFields({isConsultant: isChecked, isScientificSupervisor: !isChecked }):updateFields({isConsultant: !isChecked, isScientificSupervisor: isChecked });
    };


    return <FormWrapper title="Teacher details">

        <label className="form-label">ФИО преподавателя</label>

        <input className="form-input" name="lecturersName" type="text" id="8-field" required value={lecturersName} onChange={e => updateFields ({lecturersName: e.target.value})}/>

        <label className="form-label">Ученая степень</label>

        <input className="form-input" name="academicPosition" type="text" id="9-field" required value={academicPosition}  onChange={e => updateFields ({academicPosition: e.target.value})}/>

        <label className="form-label">Должность</label>

        <input className="form-input" name="position" type="text" id="10-field" required value={position}  onChange={e => updateFields ({position: e.target.value})} />

        <label className="form-label">Наименование кафедры</label>

        <input className="form-input" name="departmentName" type="text" id="11-field" required value={departmentName}  onChange={e => updateFields ({departmentName: e.target.value})}/>


        <label className="form-label">Научный руководитель

        <input className="form-input-checkbox" name="isScientificSupervisor" type="checkbox" id="12-field"  checked={isScientificSupervisor}   onChange={e => handleConsultantChange(e, "supervisor")}/>
        </label>

        <label className="form-label">Консультант


        <input className="form-input-checkbox" name="isConsultant" type="checkbox" id="13-field"  checked={isConsultant}  onChange={e => handleConsultantChange(e, "consultant")} />

        </label>

    </FormWrapper>

}