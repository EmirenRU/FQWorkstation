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

    console.log("CALLED SAVE INPUT")

    if( name === "orientation"){
        value = $('#orientation').val();
        //alert("here we go again");
    }



    //console.log(name);
    //console.log(value);
    /*
    if (value === ""){
        value = -1;
    }*/
    let saveEntry = {
        name: name,
        value: value
    }
    console.log(saveEntry);

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
    console.log("TERMINATED SAVE INPUT")
}



function loadSaved(){

    console.log("CALLED LOAD INPUT")
    let loaded = getLocalData('Restore data');
    console.log('loaded data',loaded)
    for(let i = 0; i < loaded.length; i++){
        let e = $("#"+loaded[i].name);
        if(loaded[i].name === "orientation"){
            console.log('i is', i)
            console.log('E is ', (e[0]) );
            e[0].value= loaded[i].value ;
            console.log("Here's your val ", loaded[i].value); 
            $('#orientation').val(loaded[i].value).trigger('select2:close');
            e[0].dispatchEvent(new Event('change'))

/*        $(document).ready(function() {
            let rendered = document.querySelector("select2-selection__rendered");
            console.log("PARENT NODE", rendered);
            console.log("RENDERED CHILDREN NODES ",rendered_children)
            rendered.removeChild(rendered_children[0]);
            })
            
            */
        }
        else{
            console.log('i is', i)
            console.log('E is ', e[i]);
            e[0].value = loaded[i].value;
            e[0].dispatchEvent(new Event('change'))
        }
    }

    console.log("TERMINATED SAVE INPUT")

}




loadSaved();


document.addEventListener("DOMContentLoaded", (event) => {

    setTimeout(() => {
        let select = $('#orientation')
        let returnObj = document.createElement('li');
        let closeSpan = document.createElement('span');
        closeSpan.classList.add("select2-selection__choice__remove");    
        closeSpan.textContent = '▼';
        closeSpan.setAttribute("role","presentation")
        returnObj.classList.add('select2-selection__choice')
        $('#orientation').next('span.select2').find('ul').html(function() {
            let count = select.select2('data').length
            if(count == 0 ){
                returnObj.textContent = " Выбрано " +  count + " Oбъектов ";
                returnObj.prepend(closeSpan)
                return returnObj;
            }
            console.log(select.select2('data')[count-1].text)
        
        
            returnObj.textContent = " Выбрано " +  count + " -- "+  select.select2('data')[count-1].text;
            returnObj.prepend(closeSpan)
            returnObj.value = $(select).val();
            return returnObj;
          })
      },0);

});


