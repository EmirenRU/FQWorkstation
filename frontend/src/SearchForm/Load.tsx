import  { useState, useEffect} from 'react';
import { saveInputs } from './Save';

import { useFormContext } from '../context';
import { getFakeSelectorData } from '../api/getData';


function jsonToData(data: string): object {
    return JSON.parse(data);
}

function getLocalData(name: string) {
    const data = localStorage.getItem(name);
    return data ? jsonToData(data) : [];
    
}

interface DepartmentProps {
    departmentValue: string;
    departmentName: string;
  }


  interface OrientationProps {
    orientation: string;
    orientationName: string;

  }

  interface TeachersProps {

    studentValue: string;
    studentName: string;

  }

  interface ThemeProps {
  themeName: string;
  themeValue: string;
  }
export const  LoadSaved = () =>  {

    const [orientationData, setOrientationData] = useState<string[]>([]);
    const [themesData, setThemesData] = useState<string[]>([]);
    const [departmentData, setDepartmentData] = useState<string[]>([]);
    const [lecturerData, setLecturerData] = useState<string[]>([]);
    const [fromData, setFromData] = useState('');
    const [tillData, setTillData] = useState('');
    const { setFormData } = useFormContext();
    const [selectorsStatus, setSelectorLoaded] = useState(false)

    //selectors
    const [Departments, setDepartments] = useState < DepartmentProps []>([])
    const [Orientations, setOrientations] = useState<OrientationProps[]>([]);
    const [Teachers, setTeachersData] = useState< TeachersProps []>([])
    const [Themes, seThemes] = useState< ThemeProps []>([])
    //




    interface DataItem {
        name: string;
        value: string[];
    }

    interface DataObject {
        [key: string]: DataItem;
    }

    async function fetchSelectorData() {
        try {
            console.log("In try section of fetch data");
            const result = await getFakeSelectorData();
            console.log("Parsed", result); // Log the result to verify its structure
            
            if (result && result.department && result.orientation && result.student && result.theme) {
 {
                    const DepartmentData: Array<DepartmentProps> = result.department.map((obj) => ({
                        departmentValue: obj.value,
                        departmentName: obj.name,
                    }));

                    const OrientationData: Array<OrientationProps> = result.orientation.map((obj) => ({
                        orientation: obj.value,
                        orientationName: obj.name,
                    }))

                    const TeachersData: Array<TeachersProps> = result.student.map((obj) => ({
                        studentName: obj.name,
                        studentValue: obj.value
                    }))

                    const ThemesData: Array<ThemeProps> = result.theme.map((obj) => ({
                        themeName: obj.name,
                        themeValue: obj.value
                    }))

                    setDepartments( DepartmentData);
                    setOrientations(OrientationData);
                    setTeachersData(TeachersData);
                    seThemes(ThemesData)
                }
            } else {
                console.error("Invalid data structure received from getFakeSelectorData");
            }
        } catch (error) {
            console.error("Error fetching data:", error);
        }
    }
    


    useEffect(() => {
        fetchSelectorData();
        const data = getLocalData('Restore data') as DataObject;
        let yearEntry;
        const filteredData = Object.entries(data);
        console.log("here", filteredData);

        for (const entry of filteredData) {
            const entryVal: string[] = entry[1].value;
            const entryName = entry[1].name;
            console.log("To save", entryVal)
            switch (entryName) {
                case 'orientation':
                    setOrientationData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case 'department':
                    setDepartmentData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case 'themes':
                    setThemesData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case 'lecturer':
                    setLecturerData(entryVal);
                    saveInputs(entryName, entryVal,"restore");
                    break;
                case "from":
                    yearEntry = Object.entries(entryVal);
                    console.log("Year entry",yearEntry[0][1])
                    setFromData(yearEntry[0][1]);
                    saveInputs(entryName, entryVal, "restore");
                break;
                case "to":
                    yearEntry = Object.entries(entryVal);
                    setTillData(yearEntry[0][1]);
                    saveInputs(entryName, entryVal, "restore");
                break;
            }
        }

    }, []);

    useEffect(() => {
        if (Departments.length > 0 && Orientations.length > 0 && Teachers.length > 0 && Themes.length > 0) {
            setSelectorLoaded(true);
        }
    }, [Departments, Orientations, Teachers, Themes, selectorsStatus]);


    useEffect(() => {
        if (selectorsStatus) {
            // Initialize bootstrap-select
            $('.selectpicker').selectpicker('refresh');
        }
    }, [selectorsStatus, Departments, Orientations, Teachers, Themes]);


    const handleYearsChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const  {name}  = event.target;
        switch (name) {
            case 'from':
                setFromData(value);
                saveInputs(name, {value});
                break;
            case 'to':
                setTillData(value);
                saveInputs(name, {value});
                break;
        }

    }




    const handleInputChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const options = event.target.options;
        const { name } = event.target;
        const selectedValues = Array.from(options)
            .filter(option => option.selected)
            .map(option => option.value);
        console.log("Selected values", selectedValues);

        switch (name) {
            case 'orientation':
                setOrientationData(selectedValues);
                saveInputs(name, selectedValues);

                break;
            case 'department':
                setDepartmentData(selectedValues);
                saveInputs(name, selectedValues);

                break;
            case 'themes':
                setThemesData(selectedValues);
                saveInputs(name, selectedValues);

                break;
            case 'lecturer':
                setLecturerData(selectedValues);
                saveInputs(name, selectedValues);

                break;
        }
    };

    const handleClick = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault;
        const objectToParse = {
            orientation: orientationData,
            department: departmentData,
            from: fromData,
            till: tillData, 
            themes: themesData,
            lecturer: lecturerData
        }
        setFormData((prev) => ({ ...prev, ...objectToParse }));
    }



    return (
        <>
        
            <div className="selector-patch">
                <label className="selection-param">Направление
                <select name="orientation" className="selectpicker" onChange={handleInputChange} id="orientation" multiple data-live-search="true" value={orientationData}>
                        {selectorsStatus ? (
                            Orientations.map((init) => (
                                console.log("Output cycle", init),
                                <option key={crypto.randomUUID()} value={init.orientation}> {init.orientationName} </option>

                            ))
                        ) : (
                            <option value="-1">Loading...</option>
                        )}
                        </select>
                </label>
            </div>
            <div className="selector-patch">
                <label className="selection-param">Кафедра
                    <select name="department"  className="selectpicker" multiple onChange={handleInputChange} id="department" value={departmentData}>
                        {selectorsStatus ? (
                            Departments.map((init) => (
                                <option key={crypto.randomUUID()} value={init.departmentValue}>
                                    {init.departmentName}
                                </option>
                            ),

                        )) : (
                            <option value="-1">Loading...</option>
                        )}
                    </select>
                </label>
            </div>
            <div className="selector-patch-2">
                <label className="selection-param">Годы работ
                    <div className="selection-param__years flex">
                        <span className="selection-param__years__text">с</span>
                        <input type="text" name="from" id="from" className="val" value={fromData} onChange={handleYearsChange} />
                        <span className="selection-param__years__text"> до</span>
                        <input type="text" name="to" id="to" className="val" value={tillData} onChange={handleYearsChange} />
                    </div>
                </label>
            </div>
            <div className="selector-patch-2">
                <label className="selection-param">Тема
                    <select className="selectpicker" name="themes" data-placeholder="Select or type to search" onChange={handleInputChange} id="themes" multiple data-live-search="true" value={themesData}>
                        <option value="-1" disabled>Выберите тему: </option>
                        {selectorsStatus ? (
                            Themes.map((init) => (
                                <option key={init.themeName} value={init.themeValue}>
                                    {init.themeName}
                                </option>
                            ))
                        ) : (
                            <option value="-1">Loading...</option>
                        )}
                    </select>
                </label>
            </div>
            <div className="selector-patch-2 margin-fix">
                <label className="selection-param">Науч рук
                    <select name="lecturer" className="selectpicker" onChange={handleInputChange} value={lecturerData} id="lecturer" multiple data-live-search="true">
                        <option value="-1">
                            Выберите преподавателя
                        </option>
                        {selectorsStatus ? (
                            Teachers.map((init) => (
                                <option key={init.studentName} value={init.studentValue}>
                                    {init.studentName}
                                </option>
                            ))
                        ) : (
                            <option value="-1">Loading...</option>
                        )}
                    </select>
                </label>
            </div>
            <button className="btn-reset form-button list-button btn btn-primary" onClick={(e) => { handleClick(e) }} type='submit'> Получить</button>
        </>
    )
}