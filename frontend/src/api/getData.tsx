export function getTableInfo (data, set) {

    console.log("get data with following pattern", data)

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };
    const response = fetch('/fqw-api/api/v1/receive-by-params', requestOptions)
        .then(response => response.json());
        console.log('Response',response);
    return response.then(res => res);

}


export function getFakeInfo(data, set){
    return import('./data.json') // Adjust the path to your local JSON file
    .then(localData => {
        return localData.default; // Return the local JSON data
    })
    .catch(error => {
        console.error('Failed to load local JSON:', error);
        throw error; // Re-throw the error for the caller to handle
    });
}


export function getSelectors( ){
    const getOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    };
    const response =  fetch("/fqw-api/api/v2/receive-selectors", getOptions)
        .then(response => response.json())
        .catch(error => {
            console.error('Failed to load local JSON:', error);
            throw error; // Re-throw the error for the caller to handle
        });
    console.log("get data with following pattern", response)
    return response.then(body => body)
}