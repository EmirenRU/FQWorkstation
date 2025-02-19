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
    studName: string,
    studNum: string,
    citizenship: string,
    loe: string,
    classifier: string,
    orientationCode: string,
    orientationName: string,
    dateOfProtection: string,
    lecturersName: string,
    academicPosition: string,
    position: string,
    departmentName: string,
    isScientificSupervisor: boolean,
    isConsultant: boolean,
    themeName: string,
    uniqueness: string,
    feedback: string,
    volume: string,
    headOfTheFQW: string,
    review: string,
    grade: string,
    protocolVolume: string,
    reviewerName: string,
    reviewerAD: string,
    reviewerPos: string,
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

const INITIALDATA:FormData = {
    studName: "",
    studNum: "",
    citizenship: "",
    loe: "",
    classifier: "",
    orientationCode: "",
    orientationName: "",
    dateOfProtection: "",
    lecturersName: "",
    academicPosition: "",
    position: "",
    departmentName: "",
    isScientificSupervisor: false,
    isConsultant: false,
    themeName: "",
    uniqueness: "",
    feedback: "",
    volume: "",
    headOfTheFQW: "",
    review: "",
    grade: "",
    protocolVolume: "",
    reviewerName: "",
    reviewerAD: "",
    reviewerPos: "",
    commissionerName1: "",
    commissionerName2: "",
    commissionerName3: "",
    commissionerUniversity1: "",
    commissionerUniversity2: "",
    commissionerUniversity3: "",
    commissionerDepartment1: "",
    commissionerDepartment2: "",
    commissionerDepartment3: "",
    question1: "",
    question2: "",
    question3: "",

}

export function Protocol  (){

    function updateFields(fields: Partial<FormData>){
        console.log(fields);
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

        fetch('/fqw-api/api/add-data', {
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
