export async function downloadFile(id: string) {
    try {
        const response = await fetch("/protocol-api/api/protocol/download_file/" + id, { method: "GET" });

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

export async function downloadFileByUrl(url: string) {
    try {
        const response = await fetch(url, { method: "GET" });

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


export async function downloadTableTemplate(id: string) {
    try {
        console.log("Downloading template with ID:", id);

        const url = id ? `/protocol-api/api/protocol/download_template/${id}` : "/protocol-api/api/protocol/download_template";

        const response = await fetch(url, { method: id ? "POST" : "GET" });

        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.status} ${response.statusText}`);
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
