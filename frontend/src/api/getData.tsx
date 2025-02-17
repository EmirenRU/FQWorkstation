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
    return response.then(res => set(res));

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


export function getFakeSelectorData( ){
    return import('./SelectorData.json') // Adjust the path to your local JSON file
    .then(localData => {
        return localData.default; // Return the local JSON data
    })
    .catch(error => {
        console.error('Failed to load local JSON:', error);
        throw error; // Re-throw the error for the caller to handle
    });
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