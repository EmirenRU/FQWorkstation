let currentPage = document.getElementById("wrapper-1");
let wrappersList = document.getElementsByClassName("input-wrapper");
let docForm = document.getElementById("doc-form");
let buttons = document.getElementsByClassName("section-button");
let nextPage = document.getElementById("next__button");

function checkFinalPage(nextPage){
    if((currentPage.id).includes("6")){
        nextPage.textContent = "send";
        nextPage.setAttribute('type','submit');
        nextPage.addEventListener('click', function(e){
            sendFormData();
            // docForm.submit();
            // cleanInputField();
        })
    }
}


for (let button of buttons){
    button.addEventListener('click', function(e){
        e.preventDefault();
        let currentID=button.id;
        for(let wrapper of wrappersList){
            let wrapperId= wrapper.id;
            let currentButtonData = currentID.match(/(\d+)/)[0];
            if(wrapperId.includes(currentButtonData)){
                currentPage.style.display = "none";
                currentPage = wrapper;
                currentPage.style.display = "block";
            }
            if(currentID.includes("6")){
                checkFinalPage(nextPage);
            }
            else{
                nextPage.textContent = "Next →";
            }
        }
    })
}


nextPage.addEventListener('click', function(e){
    e.preventDefault();
    let currentPageId = currentPage.id;
    let currentPageNumber = Number(currentPageId.match(/(\d+)/)[0]);
    currentPageNumber += 1;
    for(let wrapper of wrappersList){
        let wrapperId= wrapper.id;
        if(wrapperId.includes(currentPageNumber)){
            currentPage.style.display = "none";
            currentPage = wrapper;
            currentPage.style.display = "block";
        }
        checkFinalPage(nextPage);
    }

})

function getData(){
    return docForm.querySelectorAll('input');
}


function serializeFormDataMap(){
    const serializedData = {};

    for (let input of getData()){
        if (input.type === "checkbox"){
            serializedData[input.name] = input.checked;
        } else if (input.type === "date"){
            serializedData[input.name] = input.value;
        } else {
            serializedData[input.name] = input.value;
        }
    }
    return serializedData;
}

function sendFormData() {
    const json = JSON.stringify(serializeFormDataMap());

    console.log(json);  // Debugging: Check if the JSON is correct before sending

    $.ajax({
        type: 'POST',
        url: '/api/add-data',
        data: json,  // Ensure it is stringified JSON
        contentType: 'application/json',  // Set content type to JSON
        processData: false,  // Don't process data into query string

        success: function (response) {
            console.log("Uploaded data with response: " + response);
        },
        error: function (response) {
            console.log("Error: " + response.statusText);
        },
        timeout: 5000,
    });
}

$("7-field").on("change", function() {
    this.setAttribute(
        "data-date",
        moment(this.value, "YYYY-MM-DD")
            .format( this.getAttribute("data-date-format") )
    )
}).trigger("change")

// function cleanInputField(){
//     const inputs = getData()
//
//     for (let inp of inputs){
//         if (inp.type === "checkbox"){
//             inp.checked = false;
//         } else if (inp.type === "date"){
//             inp.value = new Date();
//         } else {
//             inp.value = "";
//         }
//     }
// }
