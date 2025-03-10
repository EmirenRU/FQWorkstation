import { useState } from "react";
import { FormWrapper } from "../FormWrapper/FormWrapper";

type StudentFieldsData = {
    studName: string,
    studNum: string,
    citizenship: string,
    loe: string,
    classifier: string,
    orientationCode: string,
    orientationName: string,
    dateOfProtection: string
    
}

type StudentFormProps = StudentFieldsData &{

    updateFields: (fields: Partial<StudentFieldsData>) => void
}

export function StudentData({ studName, studNum, citizenship, loe, classifier, orientationCode, orientationName,  updateFields }: StudentFormProps) {

    const [formattedDateForInput, setDate] = useState("");

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newDateString = e.target.value; // Get the new date string from the input
        setDate(newDateString); // Update the state with the new date string
        
        // Check if the date string is not empty
        if (newDateString) {
            const newDate = new Date(newDateString);
            const formatedDateString = newDate.toISOString().split('T')[0];
            console.log(formatedDateString);
            // Check if the date is valid
            if (!isNaN(newDate.getTime())) {
                updateFields({ dateOfProtection: formatedDateString });
            } else {
                console.error("Invalid date");
            }
        }
    };

    return (
        <FormWrapper title="Student info">
            <label className="form-label">ФИО студента</label>
            <input className="form-input" name="studName" type="text" id="0-field" value={studName} required onChange={e => updateFields({ studName: e.target.value })} />

            <label className="form-label">Студенческий билет</label>
            <input className="form-input" name="studNum" pattern="\d*" type="text" id="1-field" required value={studNum} onChange={e => updateFields({ studNum: e.target.value })} />

            <label className="form-label">Гражданство студента</label>
            <input className="form-input" name="citizenship" type="text" id="2-field" required value={citizenship} onChange={e => updateFields({ citizenship: e.target.value })} />

            <label className="form-label">Уровень образования</label>
            <input className="form-input" name="loe" type="text" id="3-field" required value={loe} onChange={e => updateFields({ loe: e.target.value })} />

            <label className="form-label">Классификатор</label>
            <input className="form-input" name="classifier" type="text" id="4-field" required value={classifier} onChange={e => updateFields({ classifier: e.target.value })} />

            <label className="form-label">Код</label>
            <input className="form-input" name="orientationCode" pattern="\d{2}.\d{2}.\d{2}" type="text" id="5-field" required value={orientationCode} onChange={e => updateFields({ orientationCode: e.target.value })} />

            <label className="form-label">Наименование</label>
            <input className="form-input" name="orientationName" type="text" id="6-field" required value={orientationName} onChange={e => updateFields({ orientationName: e.target.value })} />

            <label className="form-label">Дата</label>
            <input className="form-input" name="dateOfProtection" type="date" id="7-field" required value={formattedDateForInput} onChange={handleDateChange} />
        </FormWrapper>
    );
}
// type="date" data-date-format="DD MM YYYY" id="7-field"