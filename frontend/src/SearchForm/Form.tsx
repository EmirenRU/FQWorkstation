import { FormContent } from "./FormContent"
import './Form.css' 
import { FormEvent, useEffect, useState } from "react";
import { useFormContext } from "../context";
import { ToggleDisplayAndSaveState } from "./display";

export const SearchForm = () => {
    const { formData } = useFormContext();
    const [ready, setReady] = useState(false)

    function handleSubmit(event: FormEvent<HTMLFormElement>): void {
        event.preventDefault();
        console.log("Submitted Data:", formData);
        setReady(true);
        console.log("Ready ?", ready);
    }

    useEffect(() => {
        console.log("Ready ?", ready);
    }, [ready]);

    return (
        <main>
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
            {ready ? <ToggleDisplayAndSaveState ready/>: <span></span>}
                    </main>
    )
}