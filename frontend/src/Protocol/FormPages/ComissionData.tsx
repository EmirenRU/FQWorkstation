import { FormWrapper } from "../FormWrapper/FormWrapper"

type ComissionFieldsData = {
    commissionerName1: string,
    commissionerName2: string,
    commissionerName3: string,
    commissionerUniversity1: string,
    commissionerUniversity2: string,
    commissionerUniversity3: string,
    commissionerDepartment1: string,
    commissionerDepartment2: string,
    commissionerDepartment3: string,
    question1: string,
    question2: string,
    question3: string,

}

type ComissionFormProps = ComissionFieldsData & {

    updateFields: (fields: Partial<ComissionFieldsData>) => void
}

export function ComissionData({ commissionerName1, commissionerName2, commissionerName3,
     commissionerUniversity1,commissionerUniversity2,commissionerUniversity3,
     commissionerDepartment1,commissionerDepartment2,commissionerDepartment3,
     question1,question2,question3,
      updateFields }: ComissionFormProps) {





    return <FormWrapper title="Комиссия">

        <label className="form-label">ФИО </label>
        <input className="form-input" name="commissionerName1" type="text" id="0-field" value={commissionerName1} required onChange={e => updateFields({ commissionerName1: e.target.value })} />

        <label className="form-label">Университет</label>
        <input className="form-input" name="commissionerUniversity1" type="text" id="1-field" required value={commissionerUniversity1} onChange={e => updateFields({ commissionerUniversity1: e.target.value })} />

        <label className="form-label">Кафедра</label>
        <input className="form-input" name=" commissionerDep1" type="text" id="2-field" required value={ commissionerDepartment1} onChange={e => updateFields({  commissionerDepartment1: e.target.value })} />

        <label className="form-label">Вопрос</label>
        <input className="form-input" name="question1" type="text" id="3-field" required value={question1} onChange={e => updateFields({ question1: e.target.value })} />

        <label className="form-label">ФИО </label>
        <input className="form-input" name="commissionerName2" type="text" id="0-field" value={commissionerName2} required onChange={e => updateFields({ commissionerName2: e.target.value })} />

        <label className="form-label">Университет</label>
        <input className="form-input" name="commissionerUniversity2"  type="text" id="1-field" required value={commissionerUniversity2} onChange={e => updateFields({ commissionerUniversity2: e.target.value })} />

        <label className="form-label">Кафедра</label>
        <input className="form-input" name=" commissionerDep2" type="text" id="2-field" required value={ commissionerDepartment2} onChange={e => updateFields({  commissionerDepartment2: e.target.value })} />

        <label className="form-label">Вопрос</label>
        <input className="form-input" name="question2" type="text" id="3-field" required value={question2} onChange={e => updateFields({ question2: e.target.value })} />


        <label className="form-label">ФИО </label>
        <input className="form-input" name="commissionerName3" type="text" id="0-field" value={commissionerName3} required onChange={e => updateFields({ commissionerName3: e.target.value })} />

        <label className="form-label">Университет</label>
        <input className="form-input" name="commissionerUniversity3" type="text" id="1-field" required value={commissionerUniversity3} onChange={e => updateFields({ commissionerUniversity3: e.target.value })} />

        <label className="form-label">Кафедра</label>
        <input className="form-input" name="commissionerDepartment3" type="text" id="2-field" required value={ commissionerDepartment3} onChange={e => updateFields({  commissionerDepartment3: e.target.value })} />

        <label className="form-label">Вопрос</label>
        <input className="form-input" name="question3" type="text" id="3-field" required value={question3} onChange={e => updateFields({question3: e.target.value })} />

    </FormWrapper>
}
