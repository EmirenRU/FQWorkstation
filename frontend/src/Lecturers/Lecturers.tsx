import { FormProvider } from "../context"
import { ToggleDisplayAndSaveState } from "../SearchForm/display"
import { SearchForm } from "../SearchForm/Form"

export const Lecturers = () =>{
    return(        <FormProvider>
            <SearchForm/>
            <ToggleDisplayAndSaveState/>
            </FormProvider>)
}