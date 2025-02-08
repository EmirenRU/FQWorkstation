export const getTableInfo = async () => {

    const response = fetch('/api/v1/receive-lecturers', )
                                    .then(response => response.json());
    return response;
}