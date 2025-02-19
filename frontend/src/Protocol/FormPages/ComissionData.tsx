import { FormWrapper } from "../FormWrapper/FormWrapper"

type ComissionFieldsData = {
    comissionerName1: string,
    comissionerName2: string,
    comissionerName3: string,
    comissionerUniversity1: string,
    comissionerUniversity2: string,
    comissionerUniversity3: string,
    comissionerDepartment1: string,
    comissionerDepartment2: string,
    comissionerDepartment3: string,
    comissionerQuestion1: string,
    comissionerQuestion2: string,
    comissionerQuestion3: string,

}

type ComissionFormProps = ComissionFieldsData & {

    updateFields: (fields: Partial<ComissionFieldsData>) => void
}

export function ComissionData({ comissionerName1, comissionerName2, comissionerName3,
     comissionerUniversity1,comissionerUniversity2,comissionerUniversity3,
     comissionerDepartment1,comissionerDepartment2,comissionerDepartment3,
     comissionerQuestion1,comissionerQuestion2,comissionerQuestion3,
      updateFields }: ComissionFormProps) {





    return <FormWrapper title="Комиссия">

        <label className="form-label">ФИО </label>

        <input className="form-input" name="comissionerName1" type="text" id="0-field" value={comissionerName1} required onChange={e => updateFields({ comissionerName1: e.target.value })} />



        <label className="form-label">Университет</label>
        <input className="form-input" name="comissionerUniversity1" pattern="\w*" type="text" id="1-field" required value={comissionerUniversity1} onChange={e => updateFields({ comissionerUniversity1: e.target.value })} />


        <label className="form-label">Кафедра</label>

        <input className="form-input" name=" comissionerDepartment1" type="text" id="2-field" required value={ comissionerDepartment1} onChange={e => updateFields({  comissionerDepartment1: e.target.value })} />



        <label className="form-label">Вопрос</label>

        <input className="form-input" name="comissionerQuestion1" type="text" id="3-field" required value={comissionerQuestion1} onChange={e => updateFields({ comissionerQuestion1: e.target.value })} />







        <label className="form-label">ФИО </label>

        <input className="form-input" name="comissionerName2" type="text" id="0-field" value={comissionerName2} required onChange={e => updateFields({ comissionerName2: e.target.value })} />




        <label className="form-label">Университет</label>
        <input className="form-input" name="comissionerUniversity2" pattern="\w*" type="text" id="1-field" required value={comissionerUniversity2} onChange={e => updateFields({ comissionerUniversity2: e.target.value })} />


        <label className="form-label">Кафедра</label>

        <input className="form-input" name=" comissionerDepartment2" type="text" id="2-field" required value={ comissionerDepartment2} onChange={e => updateFields({  comissionerDepartment2: e.target.value })} />



        <label className="form-label">Вопрос</label>

        <input className="form-input" name="comissionerQuestion2" type="text" id="3-field" required value={comissionerQuestion2} onChange={e => updateFields({ comissionerQuestion2: e.target.value })} />






        <label className="form-label">ФИО </label>

        <input className="form-input" name="comissionerName3" type="text" id="0-field" value={comissionerName3} required onChange={e => updateFields({ comissionerName3: e.target.value })} />




        <label className="form-label">Университет</label>
        <input className="form-input" name="comissionerUniversity3" pattern="\w*" type="text" id="1-field" required value={comissionerUniversity3} onChange={e => updateFields({ comissionerUniversity3: e.target.value })} />


        <label className="form-label">Кафедра</label>

        <input className="form-input" name=" comissionerDepartment3" type="text" id="2-field" required value={ comissionerDepartment3} onChange={e => updateFields({  comissionerDepartment3: e.target.value })} />



        <label className="form-label">Вопрос</label>

        <input className="form-input" name="comissionerQuestion3" type="text" id="3-field" required value={comissionerQuestion3} onChange={e => updateFields({comissionerQuestion3: e.target.value })} />


    </FormWrapper>
}
