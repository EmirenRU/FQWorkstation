let appContainer = document.getElementById("application-container");

async function main() {

    let options = {
        method: 'GET'
    }
// async function getAppsNumber(){
//     let response =  await fetch('/api/get-grid', options)
//     return await response.body;
// }

    async function getGridData() {
        let response = await fetch('/api/get-grid', options);
        return response.json();
    }

    // let response = async () => {
    //     let response = ;
    //     return response
    // };

    let gridCells = await getGridData();
// if(appsNumber.ok){
//     gridCells = appsNumber;
// }
// else{
//     alert("Не удалось загрузить сетку приложений");
// }

    for (let i = 0; i < gridCells; i++) {
        let applicationContainer = document.createElement("div");
        applicationContainer.classList.add('hub__application__space');
        let image = document.createElement('img');
        image.classList.add('hub__application__icon')
        let imageName;
        let url;
        let imageData = await fetch(
            '/api/recieve/' + i,
            {method: 'GET'}
        );
        if (imageData.ok) {
            let json = await imageData.json();
            imageName.textContent = json.name;

            url = json.url;
            let id = json.imgPath;
            let imgBlob = await fetch('/api/recieve_img/' + id, {
                method: 'GET',
                headers: {
                    Accept: 'image/jpeg',
                    'Content-Type': 'image/jpeg'
                }
            })

            image.src = URL.createObjectURL(await imgBlob.blob());
        }
        let applicationName = document.createElement('span');
        applicationName.textContent = imageName;
        applicationContainer.append(image);
        applicationContainer.append(applicationName);
        applicationContainer.addEventListener("click", function (e) {
            let applicationLink = url;
            window.open(applicationLink, '_blank');
        })
        appContainer.append(applicationConteainer)
    }

}

main()