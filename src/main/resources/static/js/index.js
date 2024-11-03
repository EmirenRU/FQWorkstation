$(document).ready(function() {
    if ($.fn.select2){
        $('.slider-val').select2({ // Changed selector to match the class in HTML
            placeholder: $('.slider-val').data('placeholder'), // Set placeholder text
            width:fit-content,
            tags: true // Enable tagging/searching for options
        });
    }
});


$(document).ready(function() {
  if ($.ui && $.ui.select2) {
      $('.slider-val').select2({
          tags: true
      });
  }
});

function toggleDisplayAndSaveState() {
    let x = document.getElementById("display-container");
    if (x.style.display === "none") {
        x.style.display = "flex";
    }
}

document.addEventListener('DOMContentLoaded', function() {
    let x = document.getElementById("display-container");
    if (x!==null) {
        x.style.display = "flex";
    }
});

function fileToByteArray(file) {
    return new Promise((resolve, reject) => {
        try {
            let reader = new FileReader();
            let fileByteArray = [];
            reader.readAsArrayBuffer(file);
            reader.onloadend = (evt) => {
                if (evt.target.readyState == FileReader.DONE) {
                    let arrayBuffer = evt.target.result,
                        array = new Uint8Array(arrayBuffer);
                    for (byte of array) {
                        fileByteArray.push(byte);
                    }
                }
                resolve(fileByteArray);
            }
        }
        catch (e) {
            reject(e);
        }
    })
}

function formHash(file){
    return new Promise(function (resolve){

        let reader = new FileReader();
        reader.onload = function(event) {
            sha256(reader.result)
            resolve(sha256(reader.result))
        };
        reader.readAsText(file)
    })
}


async function uploadFileAndDownload() {
    let id = new Date().toJSON();
    let formData = new FormData();
    let file = $('#input__file')[0].files[0];
    let hashId = await formHash(file);
    // file.readAsArrayBuffer()
    console.log(hashId)

    if (!file) {
        $('#uploadProtocolStatus').text("Please select a file.");
        return;
    }

    formData.append('file', file);
    formData.append('id', id);

    // Upload the file
    let settings = {
        method: 'POST',
        body: formData,
    };

    try {
        let response = await fetch('/api/v2/upload_file', settings);
        let respData = await response.json();

        if (respData.status === '200') {
            $('#uploadProtocolStatus').text("File uploaded successfully!");
            await checkFileAvailability(id);
        } else {
            $('#uploadProtocolStatus').text("Error uploading file: " + respData.status);
        }
    } catch (error) {
        console.error("Error:", error);
        $('#uploadProtocolStatus').text("Error: " + error.message);
    }
}

async function checkFileAvailability(id) {
    let isAvailable = false;
    let settings = {
        method: "POST",
    };

    while (!isAvailable) {
        console.log("Checking file availability...");
        let response = await fetch("/api/v2/check_file_availability/" + id, settings);

        if (await response.text() === '200') {
            console.log("File is available. Proceeding to download...");
            isAvailable = true;
            await downloadFile(id);
        } else {

            await new Promise(r => setTimeout(r, 3000));
        }
    }
}


async function downloadFile(options) {
    try {
        const response = await fetch("/api/v2/download_file/" + options, { method: "GET" });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const blob = await response.blob();
        const objectUrl = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = objectUrl;
        link.download = 'protocols.docx';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        URL.revokeObjectURL(objectUrl);
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}
