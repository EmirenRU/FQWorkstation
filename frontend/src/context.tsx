import React, { createContext, useContext, useState } from 'react';

interface FormData {
    orientation: string[];
    department: string[];
    from: string;
    till: string;
    themes: string[];
    lecturer: string[]; // Adjust the type as needed
}

interface FormContextType {
    formData: FormData;
    setFormData: React.Dispatch<React.SetStateAction<FormData>>;
}

const defaultFormContext: FormContextType = {
    formData: {
        orientation: [],
        department: [],
        from: '',
        till: '',
        themes: [],
        lecturer: []
    },
    setFormData: () => {} // No-op function
};

const FormContext = createContext<FormContextType>(defaultFormContext);
const defaultObj = {
    orientation: [],
    department: [],
    from: '',
    till: '',
    themes: [],
    lecturer: []
}

export const FormProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [formData, setFormData] = useState<FormData>(defaultObj);

    return (
        <FormContext.Provider value={{ formData, setFormData }}>
            {children}
        </FormContext.Provider>
    );
};

export const useFormContext = () => {
    return useContext(FormContext);
};