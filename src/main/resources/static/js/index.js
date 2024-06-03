$(document).ready(function() {
    $('.slider-val').select2({ // Changed selector to match the class in HTML
        placeholder: $('.slider-val').data('placeholder'), // Set placeholder text
        width:fit-content,
        tags: true // Enable tagging/searching for options
    });
});


$(document).ready(function() {

  $('.slider-val').select2( {
    tags: true });
  
});

function toggleDisplayAndSaveState() {
    var x = document.getElementById("display-container");
    if (x.style.display === "none") {
        x.style.display = "block";
    }
}

document.addEventListener('DOMContentLoaded', function() {
    var x = document.getElementById("display-container");
    x.style.display = "block";
});