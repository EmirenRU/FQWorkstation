import { FormWrapper } from "../FormWrapper/FormWrapper"

type ProtocolFieldsData = {
    headOfTheFQW: string,
    review: string,
    grade: string,
    protocolVolume: string,
}

type ProtocolFormProps = ProtocolFieldsData &{

    updateFields: (fields: Partial<ProtocolFieldsData>) => void
}

export function ProtocolData({ headOfTheFQW, review, grade, protocolVolume,  updateFields}: ProtocolFormProps) {

    



    return <FormWrapper title="Протокол">

        <label className="form-label">Руководитель ВКР</label>

        <input className="form-input" name="headOfTheFQW" type="text" id="0-field" value={ headOfTheFQW} required onChange={e => updateFields ({headOfTheFQW: e.target.value})}/>




        <label className="form-label">Рецензия</label>
        <input className="form-input" name="review"  type="text" id="1-field" required value={review} onChange={e => updateFields ({review: e.target.value})}/>


        <label className="form-label">Оценка</label>

        <input className="form-input" name="grade" type="text" id="2-field" required value={grade} onChange={e => updateFields ({grade: e.target.value})} />



        <label className="form-label">Объем</label>

        <input className="form-input" name="protocolVolume" type="text" id="3-field" required value={ protocolVolume} onChange={e => updateFields ({ protocolVolume: e.target.value})}/>


    </FormWrapper>
}
