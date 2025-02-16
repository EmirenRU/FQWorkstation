import { FormEvent, useState } from "react";
import { StudentData } from "./FormPages/StudentData";
import { TeachersData } from "./FormPages/TeachersData";
import { useMultistepForm } from "./MultiStepHook/MutltistepHook";
import "./protocol.css"
import { FQWData } from "./FormPages/FQWdata";
import { ProtocolData } from "./FormPages/ProtocolData";
import { ReviewerData } from "./FormPages/ReviewerData";
import { ComissionData } from "./FormPages/ComissionData";

type FormData ={
    name: string,
    studnum: string,
    citizenship: string,
    education: string,
    clasifyer: string,
    code: string,
    naming: string,
    studdate: Date
    teachers_name: string,
    degree: string,
    postion: string,
    department_name: string,
    is_supervisor: boolean,
    is_consultant: boolean,
    subject: string,
    originality: string,
    review: string,
    volume: string,
    FQWSupervisor: string,
    critique: string,
    assessment: string,
    protocolVolume: string,
    ReviewerName: string,
    ReviewerDegree: string,
    ReviewerDuty: string,
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

const INITIALDATA:FormData = {
    name: "",
    studnum: "",
    citizenship: "",
    education: "",
    clasifyer: "",
    code: "",
    naming: "",
    studdate: new Date(),
    teachers_name: "",
    degree: "",
    postion: "",
    department_name: "",
    is_supervisor: false,
    is_consultant: false,
    subject: "",
    originality: "",
    review: "",
    volume: "",
    FQWSupervisor: "",
    critique: "",
    assessment: "",
    protocolVolume: "",
    ReviewerName: "",
    ReviewerDegree: "",
    ReviewerDuty: "",
    comissionerName1:  "",
    comissionerName2:  "",
    comissionerName3:  "",
    comissionerUniversity1: "",
    comissionerUniversity2:  "",
    comissionerUniversity3:  "",
    comissionerDepartment1: "",
    comissionerDepartment2:  "",
    comissionerDepartment3:  "",
    comissionerQuestion1:  "",
    comissionerQuestion2:  "",
    comissionerQuestion3:  ""

}

export function Protocol  (){

    function updateFields(fields: Partial<FormData>){
        setData((prev) => {return {...prev, ...fields} } )
    }

    const [data,setData] = useState(INITIALDATA)

    const {steps, currentStepIndex, step, isFirstStep,isLastStep, back, next} = useMultistepForm([
        <StudentData {...data} updateFields = {updateFields}/>,
         <TeachersData  {...data} updateFields = {updateFields}/>,
          <FQWData {...data} updateFields = {updateFields}/>,
           <ProtocolData {...data} updateFields = {updateFields}/>,
           <ReviewerData {...data} updateFields = {updateFields}/>,
            <ComissionData {...data} updateFields = {updateFields}/>
    ]);

    function onSubmit(e: FormEvent){
        e.preventDefault();
        if(!isLastStep)
            next();
        else{
        console.log(data)
        alert("Time to send!")

        fetch('/api/name', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data), 
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); 
        })
        .then(data => {
            console.log('Success:', data); 
            alert("Data submitted successfully!");
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("There was an error submitting the data.");
        });

        }
    }

    return <div className="container protocol-container">
        <form className="form-style" onSubmit={onSubmit}>
            <div className="steps-style">
                {currentStepIndex +1 }/{steps.length}
            </div>
            {step}
            <div className="button-container">
               { !isFirstStep && <button className="form-button" type="button" onClick={back}>back</button>}
                <button type="submit" className="form-button">
                   { isLastStep ? "Submit": "next"}
                    </button>
            </div>
        </form>
    </div>
}
