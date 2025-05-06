import { sha256 } from "js-sha256";
import { ProjectData } from "../Project/project";

interface ApiResponse {
    success: boolean;
    message?: string;
    data?: Blob;
    error?: string;
}

export async function sendProjectData(params: ProjectData): Promise<ApiResponse> {
 
    const dataString = JSON.stringify(params);
    const dataHash = sha256(dataString);
    
    const sendData = {
        data: params,  
        hash: dataHash
    };

 
    const API_URL = 'url';  
    
    const requestOptions = {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(sendData)   
    };

 
    try {
        const response = await fetch(API_URL, requestOptions);
        
        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            throw new Error(
                errorData?.message || 
                `Server error: ${response.status} ${response.statusText}`
            );
        }

        const responseData: ApiResponse = await response.json();
        console.log('Data sent successfully:', responseData);
        return responseData;

    } catch (error) {
        console.error('Failed to send project data:', error);
 
        return {
            success: false,
            error: error instanceof Error ? error.message : 'Unknown error occurred'
        };
    }
}