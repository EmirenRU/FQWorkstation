export function registerRequest(username: string, phone: string, email: string, message: string, agreement: boolean):Promise<void> {
    alert("Sending");
    return fetch(" /api/support/message ",{
        method: "POST",
        headers: {
            "Content-Type":"application/json"
        },
        body: JSON.stringify({username, phone, email, message, agreement})
    }).then(validateResponse).then(() => undefined)
    }
    
    export async function validateResponse(response:Response): Promise<Response>{
        if(!response.ok){
            throw new Error(await response.text())
    
        }
    
        return response;
    }