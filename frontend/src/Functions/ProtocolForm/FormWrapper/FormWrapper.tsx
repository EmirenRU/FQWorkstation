import { ReactNode } from "react"

type FormWrapperProps = {
    title: string,
    children: ReactNode
}

export function FormWrapper({title, children} : FormWrapperProps){
    return <>
        <h2 className="form__title__style">{title}</h2>
        <div className="children__style">{children}</div>
        </>
}