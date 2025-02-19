import { FormWrapper } from "../FormWrapper/FormWrapper"

type FQWFieldsData = {
    themeName: string,
    uniqueness: string,
    feedback: string,
    volume: string,
    
}

type FQWFormProps = FQWFieldsData &{

    updateFields: (fields: Partial<FQWFieldsData>) => void
}

export function FQWData({themeName, uniqueness, feedback, volume,  updateFields}: FQWFormProps) {

    



    return <FormWrapper title="Информация о ВКР">

        <label className="form-label">Наименование темы </label>

        <input className="form-input" name="themeName" type="text" id="0-field" value={themeName} required onChange={e => updateFields ({themeName: e.target.value})}/>




        <label className="form-label">Оригинальность</label>
        <input className="form-input" name="uniqueness" pattern="\d*" type="text" id="1-field" required value={uniqueness} onChange={e => updateFields ({uniqueness: e.target.value})}/>


        <label className="form-label">Отзыв</label>

        <input className="form-input" name="feedback" type="text" id="2-field" required value={feedback} onChange={e => updateFields ({feedback: e.target.value})} />



        <label className="form-label">Объем работы</label>

        <input className="form-input" name="volume" type="text" id="3-field" required value={ volume} onChange={e => updateFields ({ volume: e.target.value})}/>


    </FormWrapper>
}
