import { useEffect, useState } from "react";
import './sendFile.css'
import uploadSvg from './upload.svg'
import downloadSvg from '../assets/download.png'
import { checkFileAvailability, formHash } from "./Hash";


export const SendFile = () => {
    const [file, setFile] = useState<File | null>(null);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            setFile(e.target.files[0]); 
        }
    };

    const handleUpload = async (fileToUpload: File) => {
        if (fileToUpload) {
            console.log('Uploading file...');

            const formData = new FormData();
            try {
                const hashId = await formHash(fileToUpload); // Compute the file hash

                console.log(typeof hashId);

                formData.append('file', fileToUpload);
                formData.append('id', hashId);

                console.log("Hash is", hashId);

                const settings = {
                    method: 'POST',
                    body: formData,
                }
            
                try {
                    const response = await fetch('/protocol-api/api/protocol/upload_file', settings);
                    if (response.status === 200) {
                        alert("Successful");
                        await checkFileAvailability(hashId);
                    } else {
                        alert("Error uploading file");
                    }
                } catch (error) {
                    console.error("Error:", error);
                    alert("Error uploading file");
                }
            } catch (error) {
                console.log("Something went wrong", error);
            }
        }
    };

    const handleEmptyFileUpload = () => {
        const emptyFile = new File([""], "empty.txt", { type: "text/plain" });
        handleUpload(emptyFile);
    };

    useEffect(() => {
        if (file) {
            handleUpload(file);
        }
    }, [file]); // Dependency array ensures this runs only when `file` changes

    return (
        <main>
            <div className="container">
                <div className="send__file__container">

                    <div className=" send__file__section " >
                        <div className= "send__file__inside">
                        <h2 className="send__file__section-description" >
                            Шаблон документа с данными
                        </h2>

                        <p> Lorem ipsum dolor sit amet consectetur adipisicing elit. Tenetur dolorum earum tempore fugiat recusandae dolorem nam eos odio repellat eligendi voluptates exercitationem, molestias a voluptate asperiores? Accusamus dicta ut reprehenderit.</p>

                        <label htmlFor="file-input" className="input__file-button" onClick={handleEmptyFileUpload}  style={{ maxWidth: "220px" }}>
                        <span className="input__file-icon-wrapper">
                                <img className="input__file-icon" src={downloadSvg} alt="Word файл" width="25" />
                            </span>
                            <span className="input__file-button-text">Скачать файл</span>
                        </label>
                        </div>
                    </div>

                    <div className=" send__file__section" >
                    <div className= "send__file__inside">
                        <h2 className="send__file__section-description">
                            Загрузить шаблон 
                        </h2>
                        <p> Lorem ipsum dolor sit amet consectetur adipisicing elit. Tenetur dolorum earum tempore fugiat recusandae dolorem nam eos odio repellat eligendi voluptates exercitationem, molestias a voluptate asperiores? Accusamus dicta ut reprehenderit.</p>

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
                        </div>
                    </div>



                    <div className=" send__file__section " >
                        <div className= "send__file__inside">
                        <h2 className="send__file__section-description">
                            Скачать пример таблицы с данными
                        </h2>
                        <p> Lorem ipsum dolor sit amet consectetur adipisicing elit. Tenetur dolorum earum tempore fugiat recusandae dolorem nam eos odio repellat eligendi voluptates exercitationem, molestias a voluptate asperiores? Accusamus dicta ut reprehenderit.</p>

                        <label htmlFor="file-input" className="input__file-button" onClick={handleEmptyFileUpload}  style={{ maxWidth: "220px" }}>
                        <span className="input__file-icon-wrapper">
                                <img className="input__file-icon" src={downloadSvg} alt="Word файл" width="25" />
                            </span>
                            <span className="input__file-button-text">Скачать файл</span>
                        </label>
                        </div>
                    </div>

                    <div className=" send__file__section" >
                    <div className= "send__file__inside">
                        <h2 className="send__file__section-description">
                            Загрузить таблицу с данными 
                        </h2>
                        <p> Lorem ipsum dolor sit amet consectetur adipisicing elit. Tenetur dolorum earum tempore fugiat recusandae dolorem nam eos odio repellat eligendi voluptates exercitationem, molestias a voluptate asperiores? Accusamus dicta ut reprehenderit.</p>

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
                        </div>
                    </div>



                </div>
            </div>
        </main>
    );
};