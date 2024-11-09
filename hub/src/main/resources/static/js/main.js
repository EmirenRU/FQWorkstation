console.log("I am here")

let appContainer = document.getElementById("application-container");
let gridCells = 9;

let optionsForGet = {
    method: 'GET'
}

async function getAppsNumber(){
    let appsNumber = await fetch('/api/get-grid', optionsForGet)
}

let appsNumber = await getAppsNumber();

if(appsNumber.ok){
    gridCells = appsNumber;
}  else {
    gridCells = 2;
    alert("Не удалось загрузить сетку приложений");
}


for (let i = 0; i < gridCells; i++){
    console.log("I am here")
    let applicationContainer = document.createElement("div");
    let image = document.createElement('img');
    let imageName = "@{${websiteList["+i+"].imgPath} }";
    console.info(imageName);
    image.setAttribute = ("src", imageName);
    let applicationName = document.createElement("h2");
    let name = "${websiteList["+i+"].name}";
    console.info(name);
    applicationName.setAttribute("text", name);
    applicationContainer.append(image);
    applicationContainer.append(applicationName);
    applicationContainer.addEventListener("click", function(e){
        let applicationLink = "${websiteList["+i+"].url}";
        window.open(applicationLink,'_blank');
        
    })
    appContainer.append(applicationContainer)
}

console.log(appContainer)