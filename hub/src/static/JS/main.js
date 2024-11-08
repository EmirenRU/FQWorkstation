let appContainer = document.getElementById("application-container");

let options = {
    method: 'GET'
}
async function getAppsNumber(){
    let appsNumber = await fetch('/hub_application/api', options)
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
    let imageName = "{image"+ i + ".jpg}";
    image.setAttribute = ("th:src",imageName);
    let applicationName = document.createElement("h2");
    let name = "${stlec.lecturer.name"+i+"}";
    applicationName.setAttribute("th:text", name);
    applicationConteainer.append(image);
    applicationConteainer.append(applicationName);
    applicationConteainer.addEventListener("click", function(e){
        let applicationLink = "http://adress.com/application"+i;
        window.open(applicationLink,'_blank');
        
    })
}