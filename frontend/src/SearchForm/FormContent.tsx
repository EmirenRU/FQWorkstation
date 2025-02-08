import { LoadSaved} from "./Load";


function jsonToData(data: string): object {
    return JSON.parse(data);
}



export const FormContent = () => {
    
    const locData = () => {  
        const data = localStorage.getItem('Restore data');
        return data ? jsonToData(data) : {}; 
    }

    const data = locData(); 

    if (Object.keys(data).length === 0) {
        return (<LoadSaved/>);
    } else {
        return (<LoadSaved />);
    }
}


