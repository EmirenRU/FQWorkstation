let appContainer = document.getElementById("application-container");

let options = {
    method: 'GET'
}
let appsNumber;
async function getAppsNumber(){
    appsNumber = await fetch('/hub_application/api', options)
}
let gridCells = 9;

if(appsNumber.ok){
    gridCells = appsNumber;
}
else{
    alert("Не удалось загрузить сетку приложений");
}

for(let i = 0; i < gridCells; i++){
    let applicationConteainer = document.createElement("div");
    let image = document.createElement('img');
    let imageName = document.createElement('span');
    let url;
    let imageData = await fetch(' /api/recieve/id'+i, {
        method: 'GET'});
    if(imageData.ok){
        imageName.textContent = imageData.name;
        url = imageData.url;
        let id = imageData.imgId;
        let imgBlob = await fetch('/api/recieve_img/'+id, {
            method: 'GET',
            headers: {
                Accept:  'image/jpeg',
                'Content-Type': 'image/jpeg'
            }
        })
        image.src = URL.createObjectURL(imgBlob);

    }
    let applicationName = document.createElement('span');
    let name = imageName;
    applicationName.textContent = name;
    applicationConteainer.append(image);
    applicationConteainer.append(applicationName);
    applicationConteainer.addEventListener("click", function(e){
        let applicationLink = url;
        window.open(applicationLink,'_blank');
        
    })
    
}