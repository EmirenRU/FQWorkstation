
function dataToJson(data:object){
    return JSON.stringify(data);
}

function jsonToData(data: string): object {
    return JSON.parse(data);
}

function getLocalData(name: string) {
    const data = localStorage.getItem(name);
    return data ? jsonToData(data) : [];
}



interface SaveEntry {
    name: string;
    value: object;
  }

  const savedData:Array<SaveEntry> = [];

export const saveInputs = (name:string, value: object, flag?:string) =>{

    const saveEntry: SaveEntry = {
        name: name,
        value: value
    }
    
    if(name === "orientation" || name === "themes" || name === "lecturer" || name === "department"){
            const idVal:string = "#" + name;
            const selectedItem= $(idVal).val();

            if(flag === undefined && typeof selectedItem === "object"){
            saveEntry.value = selectedItem;

            }
    }

    const searchSimilar = savedData.find((element: SaveEntry) => (element.name === saveEntry.name))
    if(searchSimilar === undefined){
        savedData.push(saveEntry);
    }
    else{
        
        savedData.splice(savedData.indexOf(searchSimilar),1);
        savedData.push(saveEntry);

    }
    const data = dataToJson(savedData);
    console.log('All data ', getLocalData('Restore data'));
    localStorage.setItem('Restore data', data);

}
