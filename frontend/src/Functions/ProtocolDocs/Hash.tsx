import { sha256 } from 'js-sha256';
import { downloadFile } from '../../api/dowloadApi';

export function formHash(file: Blob): Promise<string>{
    return new Promise(function (resolve, reject) {
        const reader = new FileReader();

        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        reader.onload = function (_event) {
            try {

                const arrayBuffer = reader.result as ArrayBuffer;
                const uint8Array = new Uint8Array(arrayBuffer);


                const hash = sha256(uint8Array);
                resolve(hash);
            } catch (error) {
                reject(error);
            }
        };

        reader.onerror = function (error) {
            reject(error);
        };


        reader.readAsArrayBuffer(file);
    });
}


export async function checkFileAvailability(id: string) {
    // Для кадого случая свой id на нужные документы -- само значение id можно изменить в SendFile (строка 83, строка 52)
    //protocol-template
    //table-templates
    let isAvailable = false;
    const settings = {
        method: "POST",
    };

    while (!isAvailable) {
        console.log("Checking file availability...");
        const response = await fetch("/protocol-api/api/protocol/check_file_availability/" + id, settings);

        if (await response.text() === '200') {
            console.log("File is available. Proceeding to download...");
            isAvailable = true;
            await downloadFile(id);
        } else {
            alert("Retrying to fetch file")
            await new Promise(r => setTimeout(r, 3000));
        }
    }
}



export const handleUpload = async (fileToUpload: File , template:boolean) => {
        //null case 

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

                let response;
            
                try {

                    if(!template)
                     response = await fetch('/protocol-api/api/protocol/upload_file', settings);
                    else
                    response = await fetch('/protocol-api/api/protocol/upload_file', settings);
                    if (response.status === 200 && !template) {
                        alert("Successful");
                       await checkFileAvailability(hashId);
                    }
                    if (response.status === 200 && template) {
                        alert("Successful template update");
                    }
                    else {
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


