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