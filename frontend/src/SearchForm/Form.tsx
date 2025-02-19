import { FormContent } from "./FormContent"
import './Form.css' 
import { FormEvent, useEffect, useState } from "react";
import { useFormContext } from "../context";
import { ToggleDisplayAndSaveState } from "./display";

export const SearchForm = () => {
    const { formData } = useFormContext();
    const [signal, setReady] = useState("none")

    function handleSubmit(event: FormEvent<HTMLFormElement>): void {
        setReady(signal =>("none"))
        event.preventDefault();
        console.log("Submitted Data:", formData);
        setReady(signal =>("display"))
        console.log("Ready ?", signal);
    }

    useEffect(() => {
        console.log("Ready ?", signal);
    }, [signal]);

    return (
        <main>
    <section  className="selector-form">
            <div className="container-fluid form-container">
                <h2 className="section-header form-header">Получить документ</h2>
                <div className="form-space flex">
                    <form method="post" className="form-body" onSubmit={handleSubmit}>

                    <FormContent  signal={signal} setReady={setReady}/>
                    </form>

                </div>
            </div>
        </section>
            {(signal === "display" || signal === "pending") ? <ToggleDisplayAndSaveState signal={signal} setReady={setReady}/>: <span></span>}
                    </main>
    )
}