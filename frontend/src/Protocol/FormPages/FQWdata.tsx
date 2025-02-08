import { FormWrapper } from "../FormWrapper/FormWrapper"

type FQWFieldsData = {
    subject: string,
    originality: string,
    review: string,
    volume: string
    
}

type FQWFormProps = FQWFieldsData &{

    updateFields: (fields: Partial<FQWFieldsData>) => void
}

export function FQWData({subject, originality, review, volume,  updateFields}: FQWFormProps) {

    



    return <FormWrapper title="Информация о ВКР">

        <label className="form-label">Наименование темы </label>

        <input className="form-input" name="subject" type="text" id="0-field" value={subject} required onChange={e => updateFields ({subject: e.target.value})}/>




        <label className="form-label">Оригинальность</label>
        <input className="form-input" name="originality" pattern="\d*" type="text" id="1-field" required value={originality} onChange={e => updateFields ({originality: e.target.value})}/>


        <label className="form-label">Отзыв</label>

        <input className="form-input" name="review" type="text" id="2-field" required value={review} onChange={e => updateFields ({review: e.target.value})} />



        <label className="form-label">Объем работы</label>

        <input className="form-input" name="volume" type="text" id="3-field" required value={ volume} onChange={e => updateFields ({ volume: e.target.value})}/>


    </FormWrapper>
}
