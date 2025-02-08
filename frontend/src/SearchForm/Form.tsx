import { FormContent } from "./FormContent"
import './Form.css' 
import { FormEvent } from "react";
import { useFormContext } from "../context";

export const SearchForm = () => {
    const { formData } = useFormContext();

    function handleSubmit(event: FormEvent<HTMLFormElement>): void {
       event.preventDefault();
        console.log("Submitted Data:", formData);
    }

    return (
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
    )
}