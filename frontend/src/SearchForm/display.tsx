import { useEffect, useRef, useState } from 'react';
import { useFormContext } from '../context';
import { downloadExcel } from "react-export-table-to-excel";
import "./display.css"
import { getTableInfo} from '../api/getData';
//import { getTableInfo } from '../api/getData';

export const ToggleDisplayAndSaveState =({signal,setReady}) => {
    console.log("recevide signal ", signal);
    ///changed
    const header = ["ФИО Преподавателя", "Ученная степень", "Должность", "Кафедра", "ФИО Студента", "Студ.Номер", "Гражданство", "Тема"];
    const tableRef = useRef(null);
    const { formData } = useFormContext();
    const [loading, setLoading] = useState(true);
    const [sortedData, setSortedData] = useState<Array<DTO>>([])
    const [tableBody, setTableBody] = useState<(string | number | boolean)[][]>([])
    const [sortDirection, setSortDirection] = useState(false);
    const [parsedData, setParsedData] = useState([]);
    const [error, setError] = useState<string | null>(null);

    async function fetchData() {
        try {
            console.log("In try section of fetch data")
<<<<<<< HEAD
            const result = await getFakeInfo(formData, setParsedData);

=======
            const result = await getTableInfo(formData, setParsedData);
>>>>>>> 7d40f90b05bc9b69b15c1665e552249f5e28369a
            console.log("Parsed", parsedData);
            setSortedData(result)
            createTableBody(result)
            setReady("pending")
            setLoading(false)

        } catch (error) {
            console.error("Error fetching data:", error);
            setError("Error fetching data:")
        }
    }

    signal === "display" ? fetchData():console.log("not display");



    useEffect(()=>{
        console.log("signal",signal)
        fetchData()

        console.log("loading state", loading);
        console.log("PDL", sortedData.length);
        if(parsedData.length != 0){
            setLoading(false)
        }
    },[])




    function handleDownloadExcel() {
        //  createTableBody(sortedData);
        downloadExcel({
            fileName: "Таблица данных учеников",
            sheet: "1",
            tablePayload: {
                header,
                body: tableBody
            }
        });
    }

    interface DTO {
        fullLecturerName: string;
        academicDegree: string;
        position: string;
        department: string;
        fullStudentName: string;
        studNum: number;
        citizenship: string;
        theme: string;
    }

    function createTableBody(sortedData: DTO[]) {
        if (sortedData !== undefined) {
            const tmpBody = sortedData.map((row) => [
                row.fullLecturerName,
                row.academicDegree,
                row.position,
                row.department,
                row.fullStudentName,
                row.studNum,
                row.citizenship,
                row.theme,
            ]);
            setTableBody(tmpBody);
            console.log("TMP BODY IS", tmpBody)
        }
    }




    const handleSort = (key: keyof DTO) => {
        console.log("IM HERE", key)
        const direction = sortDirection;
        setSortDirection(!direction);
        const sortedArray = [...sortedData].sort((a, b) => {
            if (a[key] < b[key]) return direction ? 1 : -1;
            if (a[key] > b[key]) return direction ? -1 : 1;
            return 0;
        });
        console.log("Sort function finished", sortedArray);
        createTableBody(sortedArray)
        setSortedData(sortedArray);
    };

    if (error) {
        return <div>Error: {error}</div>; // Show an error message
    }
    if (signal === "display") {
        return <div>Loading...</div>; // You can replace this with a spinner or any loading component
    }

    //changed
    return (<>
        {loading ? <span> wait a little  more</span> :
            <div className='container-fluid display-section'>
                <table className="table table-striped table-bordered " ref={tableRef}>
                    <thead className="thead-light">
                        <tr className="tr-table">
                            <th scope="col" className="td-table" onClick={() => handleSort('fullLecturerName')} >ФИО Преподавателя</th>
                            <th scope="col" className="td-table" onClick={() => handleSort('academicDegree')}>Ученная степень</th>
                            <th scope="col" className="td-table" onClick={() => handleSort('position')}>Должность</th>
                            <th scope="col" className="td-table" onClick={() => handleSort('department')}>Кафедра</th>
                            <th className="td-table" onClick={() => handleSort('fullStudentName')}>ФИО Студента</th>
                            <th scope="col" className="td-table" onClick={() => handleSort('studNum')}>Студ.Номер</th>
                            <th scope="col" className="td-table" onClick={() => handleSort('citizenship')}>Гражданство</th>
                            <th scope="col" className="td-table" onClick={() => handleSort('theme')}>Тема</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sortedData.map((value, index) => (
                            <tr className="tr-table" key={index}> {/* Use index as key, but ideally use a unique identifier */}
                                <td className="td-table lecturer_name">{value.fullLecturerName}</td>
                                <td className="td-table academic_degree">{value.academicDegree}</td>
                                <td className="td-table lecturer_position">{value.position}</td> {/* Corrected to use position */}
                                <td className="td-table department_name">{value.department}</td>
                                <td className="td-table student_name">{value.fullStudentName}</td>
                                <td className="td-table stud_num">{value.studNum}</td>
                                <td className="td-table student_citizenship">{value.citizenship}</td>
                                <td className="td-table fqw_name">{value.theme}</td>
                            </tr>
                        ))} 
                    </tbody>
                </table>
                <div className="download">
                    <button className="download-button table-download-btn btn btn-primary" type="button" onClick={handleDownloadExcel} >Скачать таблицу</button>
                </div>
            </div>
        }
    </>)

}

//{display ? <span>Amogus</span> : <span>sus</span>}