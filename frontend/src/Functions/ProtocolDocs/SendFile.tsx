import { useEffect, useState } from "react";
import './sendFile.css';
import uploadSvg from './upload.svg';
import downloadSvg from './download.png';
import { checkFileAvailability, formHash, handleUpload } from "./Hash";
import { downloadTableTemplate } from "../../api/dowloadApi";

export const SendFile = () => {
    const [file, setFile] = useState<File | null>(null);
    const [template, setTemplate] = useState<boolean >(false);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>, obj:string) => {
        if(obj === 'template'){
            setTemplate(t => true);
        } else{
            setTemplate(t=>false);
        }
        if (e.target.files) {
            setFile(e.target.files[0]); 
        }
        console.log(template);
    };


    useEffect(() => {
        if (file) {
            handleUpload(file, template);
        }
    }, [file, template]); // Dependency array ensures this runs only when `file` changes
    
/*
    const files = [
        { name: "Шаблон протокола", filename: "template.xlsx" },
        { name: "Шаблон таблицы", filename: "template_1.docx" },
        { name: "Шаблон таблицы", filename: "template_2.docx" }
    ];

 
*/

    const handleDownload = (id: string) => {
            downloadTableTemplate(id);
    }



    return (
        <main>
            <div className="container">
                <div className="send__file__container">
                    <div className="send__file__section">
                        <div className="send__file__inside">
                            <div className="send__file-left col-xl-6">
                                <h2 className="send__file__section-description">
                                    Работа с протоколами
                                </h2>
                                <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Tenetur dolorum earum tempore fugiat recusandae dolorem nam eos odio repellat eligendi voluptates exercitationem, molestias a voluptate asperiores? Accusamus dicta ut reprehenderit.</p>
                            </div>
                            <div className="upload-download__container">
                            <button onClick={() => handleDownload("protocol-template")} className="input__file-button" style={{ maxWidth: "220px", marginRight: "2%" }}>
                                    <span className="input__file-icon-wrapper">
                                        <img className="input__file-icon" src={downloadSvg} alt="Download шаблон протокола" width="25" />
                                    </span>
                                    <span className="input__file-button-text">Шаблон протокола</span>
                                </button>

                                <div style={{display: "flex", flexDirection:"column"}}>   
                        <input
                            id="file-input"
                            style={{ display: 'none' }}
                            type="file"
                            onChange={e=>handleFileChange(e, 'template')}
                            accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        />
                        <label htmlFor="file-input" className="input__file-button" style={{ maxWidth: "220px" }}>
                            <span className="input__file-icon-wrapper">
                                <img className="input__file-icon" src={uploadSvg} alt="Word файл" width="25" />
                            </span>
                            <span className="input__file-button-text">Выберите файл шаблона</span>
                        </label>
                        {(template && file !== null ) && (
                            <section>
                                File details:
                                <ul style={{ listStyle: "none", fontSize: "10px" }}>
                                    <li>Name: {file.name}</li>
                                </ul>
                            </section>
                        )}
                    </div>   


<label htmlFor="file-input-table" className="input__file-button" onClick={() => handleDownload("table-templates")} style={{ maxWidth: "220px", marginRight: "2%" }}>
                                    <span className="input__file-icon-wrapper">
                                        <img className="input__file-icon" src={downloadSvg} alt="Word файл" width="25" />
                                    </span>
                                    <span className="input__file-button-text">Шаблоны таблиц</span>
                                </label>

                    
                        <div style={{display: "flex"}}>
                        <input
                            id="file-input"
                            style={{ display: 'none' }}
                            type="file"
                            onChange={e=>handleFileChange(e, 'whatever')}
                            accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        />
                        <label htmlFor="file-input" className="input__file-button" style={{ maxWidth: "220px" }}>
                            <span className="input__file-icon-wrapper">
                                <img className="input__file-icon" src={uploadSvg} alt="Word файл" width="25" />
                            </span>
                            <span className="input__file-button-text">Файл на обработку</span>
                        </label>
                        {(file && !template) && (
                            <section>
                                File details:
                                <ul style={{ listStyle: "none", fontSize: "10px" }}>
                                    <li>Name: {file.name}</li>
                                </ul>
                            </section>
                        )}
</div>
                    

                            </div>

                        </div>
                    </div>



                </div>
            </div>
        </main>
    );
};