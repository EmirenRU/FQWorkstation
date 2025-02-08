

export function getTableInfo (data) {

    console.log("get data with following pattern", data)

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };
    const response = fetch('/api/v1/receive-by-params', requestOptions)
        .then(response => response.json());
    return response;
}