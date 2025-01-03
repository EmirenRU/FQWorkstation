/*
async function get_by_id(options){
    const response = await fetch('/api/v2/download', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json', // the type of content I'm sending
        Accept: 'application/', // the type of content I want back from the server
      },
      body: JSON.stringify(filters),
    });
    if (!response.ok) {
      throw new Error(`Failed to fetch - ${response.statusText}`);
    }
    // TODO: handle the response
    else{
        const blob = await response.blob();
        const objectUrl = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = objectUrl;
        link.download = 'Download'; // the default filename when the user saves the file
        link.click();
    }
}
*/
//get_by_id(options);
import "js-sha256";
import "hashfile";

var sha256 = require('js-sha256');

let url = '/api/v2/check/';

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


// PROMISE SECTION
// REQUIRES TESTING



// PROMISE SECTION
// REQUIRES TESTING

function formHash(file){
  return new Promise(function (resolve){

  let reader = new FileReader();
   reader.onload = function(event) { 
     sha256(reader.result)
     resolve(sha256(reader.result))
     //console.log(sha256(reader.result))
    };
     reader.readAsText(file)
   })

}


async function initialHash(file){
 let hash;
 file.addEventListener("change", async function(){
   const file = this.files;
   if(!file.length) return;
   hash = await formHash(file[0]);
   console.log(hash);
   resolve( hash);
 })
 
 }

 let inputSpan = document.getElementById("file-input-listener");
 console.log("Here",inputSpan);

 inputSpan.addEventListener("click",  async function  (){
   let file = document.getElementById("input__file");
    const h = initialHash(file);
   console.log(h)
   if (file) file.click()
   }
)
