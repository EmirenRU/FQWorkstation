$(document).ready(function() {
    $('.slider-val').select2({
        width: 'element',
        tags: true,
        placeholder: $('.slider-val').data('placeholder'),

    });
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
