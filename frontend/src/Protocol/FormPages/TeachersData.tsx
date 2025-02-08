import { FormWrapper } from "../FormWrapper/FormWrapper";

type TeachersFieldsData = {
    teachers_name: string,
    degree: string,
    postion: string,
    department_name: string,
    clasifyer: string,
    is_supervisor: boolean,
    is_consultant: boolean,
}

type TeachersFormProps = TeachersFieldsData &{

    updateFields: (fields: Partial<TeachersFieldsData>) => void
}




export function TeachersData({teachers_name, degree, postion, department_name, is_supervisor, is_consultant, updateFields}: TeachersFormProps) {

    const handleConsultantChange = (e: React.ChangeEvent<HTMLInputElement>, position:string) => {
        const isChecked = e.target.checked;
        console.log(isChecked, e.target)
        position === "consultant" ? updateFields({is_consultant: isChecked, is_supervisor: !isChecked }):updateFields({is_consultant: !isChecked, is_supervisor: isChecked });
    };


    return <FormWrapper title="Teacher details">

        <label className="form-label">ФИО преподавателя</label>

        <input className="form-input" name="lecturersName" type="text" id="8-field" required value={teachers_name} onChange={e => updateFields ({teachers_name: e.target.value})}/>

        <label className="form-label">Ученая степень</label>

        <input className="form-input" name="academicPosition" type="text" id="9-field" required value={degree}  onChange={e => updateFields ({degree: e.target.value})}/>

        <label className="form-label">Должность</label>

        <input className="form-input" name="position" type="text" id="10-field" required value={postion}  onChange={e => updateFields ({postion: e.target.value})} />

        <label className="form-label">Наименование кафедры</label>

        <input className="form-input" name="departmentName" type="text" id="11-field" required value={department_name}  onChange={e => updateFields ({department_name: e.target.value})}/>


        <label className="form-label">Научный руководитель

        <input className="form-input-checkbox" name="isScientificSupervisor" type="checkbox" id="12-field"  checked={is_supervisor}   onChange={e => handleConsultantChange(e, "supervisor")}/>
        </label>

        <label className="form-label">Консультант


        <input className="form-input-checkbox" name="isConsultant" type="checkbox" id="13-field"  checked={is_consultant}  onChange={e => handleConsultantChange(e, "consultant")} />

        </label>

    </FormWrapper>

}