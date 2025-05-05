import { ProjectData } from "../Project/project";

export async function sendProjectData(params:ProjectData) {
    const requestData ={
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(params)
    };
    try{
    const res = await fetch('url', requestData);
    const status = res.json();
    if(!res.ok){
        alert("server error")
    }
    console.log(status)
    return status;

    } catch (e){
        alert("failed to send project data")
    }
}