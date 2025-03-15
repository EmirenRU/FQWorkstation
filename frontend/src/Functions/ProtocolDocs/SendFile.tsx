import { useEffect, useState } from "react";
import './sendFile.css';
import uploadSvg from './upload.svg';
import downloadSvg from './download.png';
import xlsxIcon from './xlsx_icon.svg.png'
import docxIcon from './docx_icon.svg.png'
import { handleUpload } from "./Hash";
import { downloadTableTemplate } from "../../api/dowloadApi";

export const SendFile = () => {
    const [file, setFile] = useState<File | null>(null);
    const [template, setTemplate] = useState<File | null>(null);
    const [ready, setReady] = useState(false);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>, obj: string) => {
        if (e.target.files && e.target.files.length > 0) {
            if (obj === 'template') {
                setTemplate(e.target.files[0]);
            } else {
                setFile(e.target.files[0]);
            }
        }
    };

    // DEBUG ONLY
    useEffect(() => {
        console.log(template, "<-TMP File->", file);
    }, [template, file]);
    //

    useEffect(() => {
        if (file || template) {
            setReady(true);
        }
    }, [file, template]);

    function getExtension(filename: string) {
        return filename.split('.').pop()
    }

    const handleDownload = (id: string) => {
        downloadTableTemplate(id);
    }

    function uploadDocs() {
        console.log("Sending ", template, "<-TMP File->", file);
        handleUpload(file, template);
    }

    return (
        <main>
            <div className="container">
                <div className="send__file__container">
                    <div className="send__file__section">
                        <div className="send__file__inside">
                            <div className="send__file-top col-xl-12">

                                <div className="col-xl-6 text__section">
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

                                    <div style={{ display: "flex", flexDirection: "column" }}>
                                        <input
                                            id="template-input" 
                                            style={{ display: 'none' }}
                                            type="file"
                                            onChange={e => handleFileChange(e, 'template')}
                                            accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                        />
                                        <label htmlFor="template-input" className="input__file-button" style={{ maxWidth: "220px" }}>
                                            <span className="input__file-icon-wrapper">
                                                <img className="input__file-icon" src={uploadSvg} alt="Word файл" width="25" />
                                            </span>
                                            <span className="input__file-button-text">файл шаблона</span>
                                        </label>
                                    </div>


                                    <label htmlFor="file-input-table" className="input__file-button" onClick={() => handleDownload("table-templates")} style={{ maxWidth: "220px", marginRight: "2%" }}>
                                        <span className="input__file-icon-wrapper">
                                            <img className="input__file-icon" src={downloadSvg} alt="Word файл" width="25" />
                                        </span>
                                        <span className="input__file-button-text">Шаблоны таблиц</span>
                                    </label>


                                    <div style={{ display: "flex" }}>
                                        <input
                                            id="file-input-table" 
                                            style={{ display: 'none' }}
                                            type="file"
                                            onChange={e => handleFileChange(e, 'whatever')}
                                            accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                        />
                                        <label htmlFor="file-input-table" className="input__file-button" style={{ maxWidth: "220px" }}>
                                            <span className="input__file-icon-wrapper">
                                                <img className="input__file-icon" src={uploadSvg} alt="Word файл" width="25" />
                                            </span>
                                            <span className="input__file-button-text">Файл на обработку</span>
                                        </label>

                                    </div>


                                </div>
                            </div>

                            <div className="templates_and_files">
                                <h2 className="send__file__section-description">
                                    Загруженные файлы
                                </h2>
                                <div style={{ display: "flex", justifyContent: "center" }}>
                                    {template && (
                                        <section>
                                            Template details:
                                            <ul style={{ listStyle: "none", fontSize: "10px" }}>
                                                <li>
                                                    <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                                                        {getExtension(template.name)?.toLowerCase() === "docx" ? (
                                                            <img src={docxIcon} style={{ maxWidth: "40px" }} />
                                                        ) : (
                                                            <img src={xlsxIcon} style={{ maxWidth: "40px" }} />
                                                        )}
                                                        <span>Name: {template.name}</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </section>
                                    )}
                                    {file && (
                                        <section>
                                            File details:
                                            <ul style={{ listStyle: "none", fontSize: "10px" }}>
                                                <li>
                                                    <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                                                        {getExtension(file.name)?.toLowerCase() === "docx" ? (
                                                            <img src={docxIcon} style={{ maxWidth: "40px" }} />
                                                        ) : (
                                                            <img src={xlsxIcon} style={{ maxWidth: "40px" }} />
                                                        )}
                                                        <span>Name: {file.name}</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </section>
                                    )}
                                </div>
                                {ready && (
                                    <button className="input__file-button" onClick={uploadDocs} style={{ maxWidth: "120px", display: "flex", justifyContent: "center" }}>Загрузить файлы</button>
                                )}
                            </div>

                        </div>
                    </div>


                </div>

            </div>
        </main>
    );
};