import { FormWrapper } from "../FormWrapper/FormWrapper";

type StudentFieldsData = {
    name: string,
    studnum: string,
    citizenship: string,
    education: string,
    clasifyer: string,
    code: string,
    naming: string,
    studdate: Date,
    
}

type StudentFormProps = StudentFieldsData &{

    updateFields: (fields: Partial<StudentFieldsData>) => void
}

export function StudentData({name, studnum, citizenship, education, clasifyer, code, naming, studdate, updateFields}: StudentFormProps) {

    

    const formattedDateForInput = studdate.toISOString().split('T')[0];

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newDate = new Date(e.target.value);
        // Check if the date is valid
        if (!isNaN(newDate.getTime())) {
            updateFields({ studdate: newDate });
        } else {
            console.error("Invalid date");
        }
    };

    return <FormWrapper title="Student info">

        <label className="form-label">ФИО студента</label>

        <input className="form-input" name="studName" type="text" id="0-field" value={name} required onChange={e => updateFields ({name: e.target.value})}/>




        <label className="form-label">Студенческий билет</label>
        <input className="form-input" name="studNum" pattern="\d*" type="text" id="1-field" required value={studnum} onChange={e => updateFields ({studnum: e.target.value})}/>


        <label className="form-label">Гражданство студента</label>

        <input className="form-input" name="citizenship" type="text" id="2-field" required value={citizenship} onChange={e => updateFields ({citizenship: e.target.value})} />



        <label className="form-label">Уровень образования</label>

        <input className="form-input" name="loe" type="text" id="3-field" required value={education} onChange={e => updateFields ({education: e.target.value})}/>


        <label className="form-label">Классификатор</label>

        <input className="form-input" name="classifier" type="text" id="4-field" required value={clasifyer} onChange={e => updateFields ({clasifyer: e.target.value})}/>



        <label className="form-label">Код</label>

        <input className="form-input" name="orientationCode" pattern="\d{2}.\d{2}.\d{2}" type="text" id="5-field" required value={code} onChange={e => updateFields ({code: e.target.value})}/>


        <label className="form-label">Наименование</label>

        <input className="form-input" name="orientationName" type="text" id="6-field" required value={naming} onChange={e => updateFields ({naming: e.target.value})}/>



        <label className="form-label">Дата</label>

    
        <input className="form-input" name="dateOfProtection" type="date" id="7-field" required value={formattedDateForInput}  onChange={ handleDateChange}/>

    </FormWrapper>
}

// type="date" data-date-format="DD MM YYYY" id="7-field"