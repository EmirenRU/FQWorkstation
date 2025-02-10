import {  useEffect, useRef, useState } from 'react';
import {  useFormContext } from '../context';
import { downloadExcel } from "react-export-table-to-excel";
import "./display.css"
import { getFakeInfo, getTableInfo } from '../api/getData';

export function ToggleDisplayAndSaveState ({ ready}) {

///changed
    const [ReadyAction, setReadyAction] = useState(ready);
    const header = ["ФИО Преподавателя", "Ученная степень", "Должность", "Кафедра", "ФИО Студента", "Студ.Номер", "Гражданство", "Тема"];
    const tableRef = useRef(null);
    const { formData } = useFormContext();
    const [loading, setLoading] = useState(true);
    const [sortedData, setSortedData] = useState<Array<DTO>>([])
    const [tableBody, setTableBody] = useState<any[]>([]);
    const [sortDirection, setSortDirection] = useState(false);
    const [parsedData, setParsedData] = useState([]);
    const [error, setError] = useState<string | null>(null);

    async function fetchData () {
        try {
            console.log("In try section of fetch data")
            const result = await getFakeInfo(formData, setParsedData);
            setReadyAction(false)
            console.log("Parsed",parsedData);
            setSortedData(result);
            setLoading(false)
            createTableBody(result)
        } catch (error) {
            console.error("Error fetching data:", error);
            setError("Error fetching data:")
        }
    }

    if(ReadyAction) {
        fetchData()
    }

    function handleDownloadExcel() {
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
        if(sortedData !== undefined){
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
    if (loading) {
        return <div>Loading...</div>; // You can replace this with a spinner or any loading component
    }

//changed
    return( <>
        { (sortedData.length === 0  && !ready) ? <span> wait a little  more</span> :
            <div className='container-fluid display-section'>
                <table className="columns"  ref={tableRef}>
                    <thead>
                    <tr className="tr-table">
                        <th className="td-table" onClick={() => handleSort('fullLecturerName')} >ФИО Преподавателя</th>
                        <th className="td-table" onClick={() => handleSort('academicDegree')}>Ученная степень</th>
                        <th className="td-table" onClick={() => handleSort('position')}>Должность</th>
                        <th className="td-table" onClick={() => handleSort('department')}>Кафедра</th>
                        <th className="td-table" onClick={() => handleSort('fullStudentName')}>ФИО Студента</th>
                        <th className="td-table" onClick={() => handleSort('studNum')}>Студ.Номер</th>
                        <th className="td-table" onClick={() => handleSort('citizenship')}>Гражданство</th>
                        <th className="td-table"  onClick={() => handleSort('theme')}>Тема</th>
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
                    <button className="download-button table-download-btn" type="button" onClick={handleDownloadExcel} >Скачать таблицу</button>
                </div>
            </div>
        }
    </>)

}

//{display ? <span>Amogus</span> : <span>sus</span>}