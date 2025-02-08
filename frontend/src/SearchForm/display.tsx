
import { ReactElement, useEffect, useRef, useState } from 'react';

import {  useFormContext } from '../context';
import { downloadExcel } from "react-export-table-to-excel";
import "./display.css"
import { getTableInfo } from '../api/getData';

export const ToggleDisplayAndSaveState= ():ReactElement => {

///changed
    const header = ["ФИО Преподавателя ", "Ученная степень", "Должность","Кафедра", "ФИО Студента", "Студ.Номер","Гражданство", "Тема"];

    const tableRef = useRef(null);
    const { formData } = useFormContext();

    const [sortedData, setSortedData] = useState<Array<DTO>>([]);
    const [tableBody, setTableBody] = useState<Array<string | number>>([]);
    const [sortDirection, setSortDirection] = useState(false);

    useEffect(() => {
        const loadData = async () => {
            try {
                const data = await getTableInfo();
                setSortedData(data); // Assuming the API returns an array of DTO
                setTableBody(data);
            } catch (error) {
                alert("Error");
            } 
        };

        loadData();
        
    }, []);




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
///
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
// changed
    function createTableBody(sortedData:DTO[]){
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
        setTableBody(tmpBody)
    }
//
    let flag:boolean = false;
    Object.values(formData).forEach(value => {
        if(Object.keys(value).length === 0){ flag = true; }
        console.log("Val is",Object.keys(value));
    });

    /// changed
    const handleSort = (key: keyof DTO) => {
        const direction = sortDirection;
        setSortDirection(direction => !direction)
        let sortedArray = [...sortedData];
        if(direction){
            sortedArray = [...sortedData].sort((a , b):number => {
                if(a[key] < b[key]){
                    console.log(a[key], " and ", b[key])
                    return direction === true ? -1 : 1;
                }
                if(a[key] === b[key]){
                    return 0;
                }
                else{
                    console.log(a[key], " and ", b[key])
                    return direction === true ? -1 : 1;
                }

            })
        }
        
       createTableBody(sortedArray);

        setSortedData(sortedArray);

    };
///
//changed
    return( <>
    {flag ? <span></span> : 
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
                        <tbody key={crypto.randomUUID()}>
                            {sortedData.map(value => {console.log(value);
                                return(<>
                                <tr className="tr-table">
                                    <td className="td-table lecturer_name">{value.fullLecturerName}</td>
                                    <td className="td-table academic_degree">{value.academicDegree}</td>
                                    <td className="td-table lecturer_position">{value.academicDegree}</td>
                                    <td className="td-table department_name">{value.department}</td>
                                    <td className="td-table student_name">{value.fullStudentName}</td>
                                    <td className="td-table stud_num">{value.studNum}</td>
                                    <td className="td-table student_citizenship">{value.citizenship}</td>
                                    <td className="td-table fqw_name">{value.theme}</td>
                                </tr>
                                </>)

                            })}
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