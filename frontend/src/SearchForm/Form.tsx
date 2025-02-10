import { FormContent } from "./FormContent"
import './Form.css' 
import { FormEvent, useEffect, useState } from "react";
import { useFormContext } from "../context";
import { ToggleDisplayAndSaveState } from "./display";

export const SearchForm = () => {
    const { formData } = useFormContext();
    const [readyToCall, setReady] = useState(false)

    function handleSubmit(event: FormEvent<HTMLFormElement>): void {
        event.preventDefault();
        console.log("Submitted Data:", formData);
        setReady(true);
        console.log("Ready ?", readyToCall);
    }

    useEffect(() => {
        console.log("Ready ?", readyToCall);
    }, [readyToCall]);

    return (
        <>
    <section  className="selector-form">
            <div className="container-fluid form-container">
                <h2 className="section-header form-header">Получить документ</h2>
                <div className="form-space flex">
                    <form method="post" className="form-body" onSubmit={handleSubmit}>

                    <FormContent />
                    </form>

                </div>
            </div>
        </section>
            {readyToCall ? <ToggleDisplayAndSaveState ready={readyToCall}/>: <span>whatever</span>}
                    </>
    )
}