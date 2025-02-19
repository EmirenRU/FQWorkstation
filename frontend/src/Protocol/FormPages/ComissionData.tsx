import { FormWrapper } from "../FormWrapper/FormWrapper"

type ComissionFieldsData = {
    commissionerName1: string,
    commissionerName2: string,
    commissionerName3: string,
    commissionerUniv1: string,
    commissionerUniv2: string,
    commissionerUniv3: string,
    commissionerDep1: string,
    commissionerDep2: string,
    commissionerDep3: string,
    question1: string,
    question2: string,
    question3: string,

}

type ComissionFormProps = ComissionFieldsData & {

    updateFields: (fields: Partial<ComissionFieldsData>) => void
}

export function ComissionData({ commissionerName1, commissionerName2, commissionerName3,
     commissionerUniv1,commissionerUniv2,commissionerUniv3,
     commissionerDep1,commissionerDep2,commissionerDep3,
     question1,question2,question3,
      updateFields }: ComissionFormProps) {





    return <FormWrapper title="Комиссия">

        <label className="form-label">ФИО </label>

        <input className="form-input" name="commissionerName1" type="text" id="0-field" value={commissionerName1} required onChange={e => updateFields({ commissionerName1: e.target.value })} />



        <label className="form-label">Университет</label>
<<<<<<< HEAD
        <input className="form-input" name="commissionerUniv1" pattern="\d*" type="text" id="1-field" required value={commissionerUniv1} onChange={e => updateFields({ commissionerUniv1: e.target.value })} />
=======
        <input className="form-input" name="comissionerUniversity1" pattern="\w*" type="text" id="1-field" required value={comissionerUniversity1} onChange={e => updateFields({ comissionerUniversity1: e.target.value })} />
>>>>>>> ff53f91d87ebb8894329ba611ef0261817a64bd9


        <label className="form-label">Кафедра</label>

        <input className="form-input" name=" commissionerDep1" type="text" id="2-field" required value={ commissionerDep1} onChange={e => updateFields({  commissionerDep1: e.target.value })} />



        <label className="form-label">Вопрос</label>

        <input className="form-input" name="question1" type="text" id="3-field" required value={question1} onChange={e => updateFields({ question1: e.target.value })} />







        <label className="form-label">ФИО </label>

        <input className="form-input" name="commissionerName2" type="text" id="0-field" value={commissionerName2} required onChange={e => updateFields({ commissionerName2: e.target.value })} />




        <label className="form-label">Университет</label>
<<<<<<< HEAD
        <input className="form-input" name="commissionerUniv2" pattern="\d*" type="text" id="1-field" required value={commissionerUniv2} onChange={e => updateFields({ commissionerUniv2: e.target.value })} />
=======
        <input className="form-input" name="comissionerUniversity2" pattern="\w*" type="text" id="1-field" required value={comissionerUniversity2} onChange={e => updateFields({ comissionerUniversity2: e.target.value })} />
>>>>>>> ff53f91d87ebb8894329ba611ef0261817a64bd9


        <label className="form-label">Кафедра</label>

        <input className="form-input" name=" commissionerDep2" type="text" id="2-field" required value={ commissionerDep2} onChange={e => updateFields({  commissionerDep2: e.target.value })} />



        <label className="form-label">Вопрос</label>

        <input className="form-input" name="question2" type="text" id="3-field" required value={question2} onChange={e => updateFields({ question2: e.target.value })} />






        <label className="form-label">ФИО </label>

        <input className="form-input" name="commissionerName3" type="text" id="0-field" value={commissionerName3} required onChange={e => updateFields({ commissionerName3: e.target.value })} />




        <label className="form-label">Университет</label>
<<<<<<< HEAD
        <input className="form-input" name="commissionerUniv3" pattern="\d*" type="text" id="1-field" required value={commissionerUniv3} onChange={e => updateFields({ commissionerUniv3: e.target.value })} />
=======
        <input className="form-input" name="comissionerUniversity3" pattern="\w*" type="text" id="1-field" required value={comissionerUniversity3} onChange={e => updateFields({ comissionerUniversity3: e.target.value })} />
>>>>>>> ff53f91d87ebb8894329ba611ef0261817a64bd9


        <label className="form-label">Кафедра</label>

        <input className="form-input" name=" commissionerDep3" type="text" id="2-field" required value={ commissionerDep3} onChange={e => updateFields({  commissionerDep3: e.target.value })} />



        <label className="form-label">Вопрос</label>

        <input className="form-input" name="question3" type="text" id="3-field" required value={question3} onChange={e => updateFields({question3: e.target.value })} />


    </FormWrapper>
}
