import { useEffect, useState } from "react";
import './sendFile.css';
import uploadSvg from './upload.svg';
import downloadSvg from './download.png';
import { checkFileAvailability, formHash, handleUpload } from "./Hash";

export const SendFile = () => {
    const [file, setFile] = useState<File | null>(null);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            setFile(e.target.files[0]); 
        }
    };


    useEffect(() => {
        if (file) {
            handleUpload(file);
        }
    }, [file]); // Dependency array ensures this runs only when `file` changes
    
/*
    const files = [
        { name: "Шаблон протокола", filename: "template.xlsx" },
        { name: "Шаблон таблицы", filename: "template_1.docx" },
        { name: "Шаблон таблицы", filename: "template_2.docx" }
    ];

 
*/

    const handleDownload = (filename: string) => {
        console.log(filename);
        const link: HTMLAnchorElement = document.createElement("a");
        link.href = `/src/assets/templates/${filename}`;
        link.download = filename;
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }


    const handleDownloadTemplates = () => {
        // This function will download both template_1.docx and template_2.docx
        handleDownload("template_2.docx");
        handleDownload("template.xlsx");
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
                            <button onClick={() => handleDownload("template.xlsx")} className="input__file-button" style={{ maxWidth: "220px", marginRight: "2%" }}>
                                    <span className="input__file-icon-wrapper">
                                        <img className="input__file-icon" src={downloadSvg} alt="Download шаблон протокола" width="25" />
                                    </span>
                                    <span className="input__file-button-text">Шаблон протокола</span>
                                </button>

                                
                        <input
                            id="file-input"
                            style={{ display: 'none' }}
                            type="file"
                            onChange={handleFileChange}
                            accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        />
                        <label htmlFor="file-input" className="input__file-button" style={{ maxWidth: "220px" }}>
                            <span className="input__file-icon-wrapper">
                                <img className="input__file-icon" src={uploadSvg} alt="Word файл" width="25" />
                            </span>
                            <span className="input__file-button-text">Выберите файл</span>
                        </label>
                        {file && (
                            <section>
                                File details:
                                <ul style={{ listStyle: "none" }}>
                                    <li>Name: {file.name}</li>
                                </ul>
                            </section>
                        )}


<label htmlFor="file-input-table" className="input__file-button" onClick={handleDownloadTemplates} style={{ maxWidth: "220px", marginRight: "2%" }}>
                                    <span className="input__file-icon-wrapper">
                                        <img className="input__file-icon" src={downloadSvg} alt="Word файл" width="25" />
                                    </span>
                                    <span className="input__file-button-text">Шаблон таблицы</span>
                                </label>

                    

                        <input
                            id="file-input"
                            style={{ display: 'none' }}
                            type="file"
                            onChange={handleFileChange}
                            accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        />
                        <label htmlFor="file-input" className="input__file-button" style={{ maxWidth: "220px" }}>
                            <span className="input__file-icon-wrapper">
                                <img className="input__file-icon" src={uploadSvg} alt="Word файл" width="25" />
                            </span>
                            <span className="input__file-button-text">Выберите таблицу</span>
                        </label>
                        {file && (
                            <section>
                                File details:
                                <ul style={{ listStyle: "none" }}>
                                    <li>Name: {file.name}</li>
                                </ul>
                            </section>
                        )}

                            </div>

                        </div>
                    </div>



                </div>
            </div>
        </main>
    );
};