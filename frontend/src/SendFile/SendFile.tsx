import { useEffect, useState } from "react";
import './sendFile.css'
import uploadSvg from './upload.svg'
import { checkFileAvailability, formHash } from "./Hash";

export const SendFile = () => {
    const [file,setFile] = useState<File | null>(null)

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            setFile(e.target.files[0]); 
        }
    };


    useEffect(() => {
        if (file) {
            handleUpload();
        }
    }, [file]); // Dependency array ensures this runs only when `file` changes

    const handleUpload = async () => {
        if (file) {
            console.log('Uploading file...');

            const formData = new FormData();
            try {
                const hashId = await formHash(file); // Compute the file hash

                console.log(typeof hashId);

                formData.append('file', file);
                formData.append('id', hashId);

                console.log("Hash is",hashId)

                const settings = {
                    method: 'POST',
                    body: formData,
                }
            
                try {
                    const response = await fetch('/protocol-api/api/protocol/upload_file', settings);
                    // const respData = await response.json();
            
                    if (response.status === 200) {
                        alert("Successful")
                        await checkFileAvailability(hashId);
                    } else {
                        alert("Error uploading file " );
                    }
                } catch (error) {
                    console.error("Error:", error);
                    alert("Error uploading file " );
                }
        }
        catch(error){
            console.log("Something fucked up",error)
        }
    }
}

    return (
        <div className="container">
            <div className="send__file__container">
                <input
                    id="file-input"
                    style={{ display: 'none' }}
                    type="file"
                    onChange={handleFileChange}
                    accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                />
                <label htmlFor="file-input" className="input__file-button">
                    <span className="input__file-icon-wrapper">
                        <img className="input__file-icon" src={uploadSvg} alt="Word файл" width="25" />
                    </span>
                    <span className="input__file-button-text">Выберите файл</span>
                </label>
            </div>
            {file && (
                <section>
                    File details:
                    <ul style={{ listStyle: "none" }}>
                        <li>Name: {file.name}</li>
                    </ul>
                </section>
            )}
        </div>
    );
};