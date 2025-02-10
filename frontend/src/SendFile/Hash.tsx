import { sha256 } from 'js-sha256';

export function formHash(file: Blob): Promise<string>{
    return new Promise(function (resolve, reject) {
        const reader = new FileReader();

        reader.onload = function (event) {
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
    let isAvailable = false;
    const settings = {
        method: "POST",
    };

    while (!isAvailable) {
        console.log("Checking file availability...");
        const response = await fetch("/api/v2/check_file_availability/" + id, settings);

        if (await response.text() === '200') {
            console.log("File is available. Proceeding to download...");
            isAvailable = true;
            await downloadFile(id);
        } else {

            await new Promise(r => setTimeout(r, 3000));
        }
    }
}

async function downloadFile(options) {
    try {
        const response = await fetch("/api/v2/download_file/" + options, { method: "GET" });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const blob = await response.blob();
        const objectUrl = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = objectUrl;
        link.download = 'protocols.docx';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        URL.revokeObjectURL(objectUrl);
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}