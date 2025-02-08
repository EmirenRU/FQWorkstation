
import { ReactElement, useEffect, useRef, useState } from 'react';

import {  useFormContext } from '../context';
import { downloadExcel } from "react-export-table-to-excel";
import "./display.css"
import { getTableInfo } from '../api/getData';

export const ToggleDisplayAndSaveState= ():ReactElement => {



///changed
    const header = ["ФИО Преподавателя", "Ученная степень", "Должность", "Кафедра", "ФИО Студента", "Студ.Номер", "Гражданство", "Тема"];
    const tableRef = useRef(null);
    const { formData } = useFormContext();

    const [sortedData, setSortedData] = useState([])
    const [tableBody, setTableBody] = useState<any[]>([]);
    const [sortDirection, setSortDirection] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [dataD, setDataD] = useState([]);


    const fetchData = async () => {
        try {
            const data = await getTableInfo(formData);
            console.log(data);
            setLoading(false);
            return data;
        } catch (error) {
            console.error("Error fetching data:", error);
            setError("Error fetching data:")
        }
    };

    useEffect(() => {
        fetchData();
    }, []);


    const res  = fetchData().then(res => setDataD(res));

    console.log("data",dataD)


    console.log(typeof sortedData.map, sortedData)

    function handleDownloadExcel() {
        downloadExcel({
            fileName: "Таблица данных учеников",
            sheet: "1",
            tablePayload: {
                header,
                body: tableBody,
            },
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
        const direction = sortDirection;
        setSortDirection(!direction);
        const sortedArray = [...sortedData].sort((a, b) => {
            if (a[key] < b[key]) return direction ? 1 : -1;
            if (a[key] > b[key]) return direction ? -1 : 1;
            return 0;
        });
        createTableBody(sortedArray)
    };

    if (loading) {
        return <div>Loading...</div>; // Show a loading state
    }

    if (error) {
        return <div>Error: {error}</div>; // Show an error message
    }


//changed
    return( <>
        { sortedData.length === 0 ? <span> No data</span> :
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