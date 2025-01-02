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


function saveInputs(name, value){
    //console.log(name);
    //console.log(value);
    let saveEntry = {
        name: name,
        value: value
    }
    if(name === "orientation"){
        let selectedItem = $('#orientation').val();
        saveEntry.value = selectedItem;
        alert(selectedItem);

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


    let loaded = getLocalData('Restore data');
    console.log('loaded',loaded)
    for(let i = 0; i < loaded.length; i++){
        let e = $("#"+loaded[i].name);
        if(loaded[i].name === "orientation"){
            console.log('i is', i)
            console.log('E is ', (e[0]) );
            e[0].value= loaded[i].value ;

            console.log("Here's your val ", loaded[i].value);
            let orientationData = loaded[i].value;
            $('#orientation').val(orientationData);
            $("#orientation").multiselect("refresh");
            e[0].dispatchEvent(new Event('change'))

        }
        console.log('loaded',loaded);
        console.log('i is', i)
        console.log('E is ', e[i]);
        e[0].value = loaded[i].value;
        e[0].dispatchEvent(new Event('change'))
    }

}

loadSaved();
