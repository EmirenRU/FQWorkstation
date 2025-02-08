import { FormWrapper } from "../FormWrapper/FormWrapper"

type ProtocolFieldsData = {
    FQWSupervisor: string,
    critique: string,
    assessment: string,
    protocolVolume: string
}

type ProtocolFormProps = ProtocolFieldsData &{

    updateFields: (fields: Partial<ProtocolFieldsData>) => void
}

export function ProtocolData({ FQWSupervisor, critique, assessment, protocolVolume,  updateFields}: ProtocolFormProps) {

    



    return <FormWrapper title="Протокол">

        <label className="form-label">Руководитель ВКР</label>

        <input className="form-input" name="FQWSupervisor" type="text" id="0-field" value={ FQWSupervisor} required onChange={e => updateFields ({FQWSupervisor: e.target.value})}/>




        <label className="form-label">Рецензия</label>
        <input className="form-input" name="critique"  type="text" id="1-field" required value={critique} onChange={e => updateFields ({critique: e.target.value})}/>


        <label className="form-label">Оценка</label>

        <input className="form-input" name="assessment" type="text" id="2-field" required value={assessment} onChange={e => updateFields ({assessment: e.target.value})} />



        <label className="form-label">Объем</label>

        <input className="form-input" name="protocolVolume" type="text" id="3-field" required value={ protocolVolume} onChange={e => updateFields ({ protocolVolume: e.target.value})}/>


    </FormWrapper>
}
