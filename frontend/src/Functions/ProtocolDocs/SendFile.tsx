import { useEffect, useState } from "react";
import './sendFile.css';
import uploadSvg from './upload.svg';
import downloadSvg from './download.png';
import xlsxIcon from './xlsx_icon.svg.png'
import docxIcon from './docx_icon.svg.png'
import { handleUpload } from "./Hash";
import { downloadTableTemplate } from "../../api/dowloadApi";
import NProgress from 'nprogress';
import 'nprogress/nprogress.css'


export const SendFile = () => {
    const [file, setFile] = useState<File | null>(null);
    const [template, setTemplate] = useState<File | null >(null);
    const [id, setId] = useState(String);
    const [ready, setReady] = useState(false);
    // const [loading, setLoading] = useState<boolean>(false);
    // const [progress, setProgress] = useState<number>(0);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>, object:string) => {
        if (e.target.files) {
            console.log(e.target.files)
            const target = e.target.files[0];
            if (object === 'file') {
                console.log("Changing file")
                setFile(e.target.files[0]);
                handleUpload(file, template, setId);
            }
            else if (object === 'template'){
                console.log("Changing template")
                setTemplate(target);
            }
            console.log(target);
        }
        else {
            console.warn("Something has happen with file")
        }
    };

    function getExtension(filename:string) {
        return filename.split('.').pop()
    }

    // const sleep = (ms : number) => new Promise(resolve => setTimeout(resolve, ms));
    useEffect(() => {
        if (file) {
            setReady(true);
        }
    }, [file, template]); // Dependency array ensures this runs only when `file` changes

    const handleDownload = (id: string) => {
        downloadTableTemplate(id);
    }



    function uploadDocs() {
        console.log("Sending ", template, "<-TMP File->", file);
        NProgress.start();
        handleUpload(file, template, setId);
        NProgress.done();

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
                                    <p>
                                        Lorem ipsum dolor sit amet consectetur adipisicing elit. Tenetur dolorum earum tempore fugiat recusandae dolorem nam eos odio repellat eligendi voluptates exercitationem, molestias a voluptate asperiores? Accusamus dicta ut reprehenderit.
                                    </p>
                                </div>
                                <div className="upload-download__container">
                                    <button onClick={() => handleDownload(id)} className="input__file-button" style={{ maxWidth: "220px", marginRight: "2%" }}>
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
                                            <span className="input__file-button-text">Выберите файл шаблона</span>
                                        </label>
                                    </div>

                                    <label htmlFor="file-input-table" className="input__file-button" onClick={() => handleDownload(id)} style={{ maxWidth: "220px", marginRight: "2%" }}>
                                <span className="input__file-icon-wrapper">
                                    <img className="input__file-icon" src={downloadSvg} alt="Word файл" width="25" />
                                </span>
                                        <span className="input__file-button-text">Шаблоны таблиц</span>
                                    </label>

                                    <div style={{ display: "flex" }}>
                                        <input
                                            id="file-input"
                                            style={{ display: 'none' }}
                                            type="file"
                                            onChange={e => handleFileChange(e, 'file')}
                                            accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                                        />
                                        <label htmlFor="file-input" className="input__file-button" style={{ maxWidth: "220px" }}>
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
                                    <button className="input__file-button" onClick={uploadDocs} style={{ maxWidth: "120px", display: "flex", justifyContent: "center" }}>
                                        Загрузить файлы
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="file-collection">
                <h3>Доступные файлы для загрузки</h3>
                <ul style={{ listStyle: "none", padding: 0 }}>
                    <li>
                        <a href="/template.xlsx" className="file-link" download>
                            Шаблон 1 (Excel файл)
                        </a>
                    </li>
                    <li>
                        <a href="/template_1.docx" className="file-link" download>
                            Шаблон 2 (Word файл)
                        </a>
                    </li>
                    <li>
                        <a href="/template_2.docx" className="file-link" download>
                            Шаблон 3 (Word файл)
                        </a>
                    </li>
                </ul>
            </div>
        </main>

    );
};