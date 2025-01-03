async function sendData(data){
    console.log(data);
    try {
        const response = await fetch('../api/v1/upload_data_to_sql', {
            method: 'POST',
            headers: {
                'Content-Type': 'plain/text',
            },
            body: data,
        });


        if (!response.ok) {
            alert('Network response was not ok ' + response.statusText);
        }

        const processedResponse = await response.json();
        return processedResponse;
    }
    catch (error) {
        alert('Ошибка:', error); // Обрабатываем ошибки
    }
}