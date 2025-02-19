import { FormWrapper } from "../FormWrapper/FormWrapper"

type ReviewerFieldsData = {
    ReviewerName: string,
    ReviewerDegree: string,
    ReviewerDuty: string
}

type ReviewerFormProps = ReviewerFieldsData & {
    updateFields: (fields: Partial<ReviewerFieldsData>) => void
}

export function ReviewerData({ReviewerName, ReviewerDegree, ReviewerDuty,  updateFields}: ReviewerFormProps) {

    



    return <FormWrapper title="Рецензент">

        <label className="form-label">ФИО </label>

        <input className="form-input" name="ReviewerName" type="text" id="0-field" value={ReviewerName} required onChange={e => updateFields ({ReviewerName: e.target.value})}/>




        <label className="form-label">Ученая степень</label>
        <input className="form-input" name="ReviewerDegree" pattern="\w*" type="text" id="1-field" required value={ReviewerDegree} onChange={e => updateFields ({ReviewerDegree: e.target.value})}/>


        <label className="form-label">Должность</label>

        <input className="form-input" name="ReviewerDuty" type="text" id="2-field" required value={ReviewerDuty} onChange={e => updateFields ({ReviewerDuty: e.target.value})} />



    </FormWrapper>
}