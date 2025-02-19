import { FormWrapper } from "../FormWrapper/FormWrapper"

type ReviewerFieldsData = {
    reviewerName: string,
    reviewerAD: string,
    reviewerPos: string,
}

type ReviewerFormProps = ReviewerFieldsData & {
    updateFields: (fields: Partial<ReviewerFieldsData>) => void
}

export function ReviewerData({reviewerName, reviewerAD, reviewerPos,  updateFields}: ReviewerFormProps) {

    



    return <FormWrapper title="Рецензент">

        <label className="form-label">ФИО </label>

        <input className="form-input" name="reviewerName" type="text" id="0-field" value={reviewerName} required onChange={e => updateFields ({reviewerName: e.target.value})}/>




        <label className="form-label">Ученая степень</label>
<<<<<<< HEAD
        <input className="form-input" name="reviewerAD" pattern="\d*" type="text" id="1-field" required value={reviewerAD} onChange={e => updateFields ({reviewerAD: e.target.value})}/>
=======
        <input className="form-input" name="ReviewerDegree" pattern="\w*" type="text" id="1-field" required value={ReviewerDegree} onChange={e => updateFields ({ReviewerDegree: e.target.value})}/>
>>>>>>> ff53f91d87ebb8894329ba611ef0261817a64bd9


        <label className="form-label">Должность</label>

        <input className="form-input" name="reviewerPos" type="text" id="2-field" required value={reviewerPos} onChange={e => updateFields ({reviewerPos: e.target.value})} />



    </FormWrapper>
}