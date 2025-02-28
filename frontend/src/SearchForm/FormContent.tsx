import { FC } from "react";
import { LoadSaved } from "./Load";


function jsonToData(data: string): object {
    return JSON.parse(data);
}

interface ToggleDisplayAndSaveStateProps {
    signal: string;
    setReady: React.Dispatch<React.SetStateAction<string>>;
}


export const FormContent: FC<ToggleDisplayAndSaveStateProps> = ({signal,setReady}) =>  {

    const locData = () => {
        const data = localStorage.getItem('Restore data');
        return data ? jsonToData(data) : {};
    }

    const data = locData();

    if (Object.keys(data).length === 0) {
        return (<LoadSaved signal={signal} setReady={setReady} />);
    } else {
        return (<LoadSaved signal={signal} setReady={setReady} />);
    }
}


