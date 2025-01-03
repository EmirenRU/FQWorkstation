 function dataToJson(data){
    return JSON.stringify(data);
}

function jsonToData(data){
    return JSON.parse(data);
}

function getLocalData(name){
    let data = localStorage.getItem(name);
    return data ? jsonToData(data): [];
}


let savedData = [];


function saveInputs(name, value,flag){
    console.log(flag)
    let saveEntry = {
        name: name,
        value: value
    }
    if(name === "orientation" || name === "themes" || name === "lecturer"){
            let idVal = "#" + name;
            let selectedItem = $(idVal).val();
            console.log("Compare values ", (saveEntry.value)," and ", selectedItem)
            if(flag === undefined){
            saveEntry.value = selectedItem;
            }


    }

    console.log(saveEntry)
    let searchSimilar = savedData.find((element) => (element.name === saveEntry.name))
    if(searchSimilar === undefined){
        savedData.push(saveEntry);
    }
    else{
        
        savedData.splice(savedData.indexOf(searchSimilar),1);
        savedData.push(saveEntry);
        console.log('Saved data ', savedData);
    }
    let data = dataToJson(savedData);
    console.log('All data ', getLocalData('Restore data'));
    localStorage.setItem('Restore data', data);
    console.log('DATA STRINIGIFIED :', data);

}



function loadSaved(){

    window.onload = function() { 
    let loaded = getLocalData('Restore data');
    console.log('loaded',loaded)
    for(let i = 0; i < loaded.length; i++){
        let e = $("#"+loaded[i].name);
        if(loaded[i].name === "orientation" || loaded[i].name === "themes" || loaded[i].name === "lecturer"){
            e[0].value= loaded[i].value ;
            let strVal = loaded[i].name;
            let idVal = "#" + loaded[i].name;
            console.log("Here's your val ", loaded[i].value); 
            let orientationData = loaded[i].value;
            saveInputs(strVal, orientationData, "load")
            $(idVal).selectpicker("val",orientationData);
            $(idVal).trigger("change");
            $(idVal).selectpicker('render');
            e[0].dispatchEvent(new Event('change'))

        }
        else{
        console.log('loaded',loaded);
        console.log('i is', i)
        console.log('E is ', e[i]);

        e[0].value = loaded[i].value;
        e[0].dispatchEvent(new Event('change'))

            }
        }
    }
}

loadSaved();
