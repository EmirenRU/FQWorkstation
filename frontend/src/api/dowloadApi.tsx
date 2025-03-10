export async function downloadFile(options: string) {
    try {
        const response = await fetch("/protocol-api/api/protocol/download_file/" + options, { method: "GET" });

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


export async function downloadTableTemplate(options: string) {
    try {
        const response = await fetch("/protocol-api/api/protocol/download_template/" + options, { method: "GET" });

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