/* TIME TO END THIS */
$('select').select2()

$('#orientation').on('select2:close', function() {
    let select = $(this)
    let returnObj = document.createElement('li');
    let closeSpan = document.createElement('span');
    closeSpan.classList.add("select2-selection__choice__remove");
    closeSpan.textContent = '▼';
    closeSpan.setAttribute("role","presentation")
    returnObj.classList.add('select2-selection__choice')
    $(this).next('span.select2').find('ul').html(function() {
        let count = select.select2('data').length - 1 // - 1 is necessary
        if(count === 0 ){
            // returnObj.textContent = " Выбрано " +  count + " Oбъектов "; // Emiren: default value not that better to delete
            // Emiren:  Fullscreen to minimal and minimal to fullscreen are destroying layout
            returnObj.prepend(closeSpan)
            return returnObj;
        }
        console.log(select.select2('data')[count-1].text)

        if (count >= 1) {
            returnObj.textContent = " Выбрано " + count + " направления "; // Emiren: do something with that I don't like it // select.select2('data')[count-1].text
            returnObj.prepend(closeSpan)
        }
        return returnObj;
    })
})

// Emiren: Try to do it and don't forget about another select2
// Emiren: In theme section make all other section by default
/* TIME TO END THIS */


$(document).ready(function() {
    console.log($('#orientation'))
    $('.slider-val').select2({ // Changed selector to match the class in HTML
        placeholder: $('.slider-val').data('placeholder'), // Set placeholder text
        tags: true // Enable tagging/searching for options
    });
    $('#orientation').select2({ // Changed selector to match the class in HTML
        placeholder: $('#orientation').data('placeholder'), // Set placeholder text
        tags: true // Enable tagging/searching for options
    });
});



function toggleDisplayAndSaveState() {
    let x = document.getElementById("display-container");
    if (x.style.display === "none") {
        x.style.display = "flex";
    }
}


$('#orientation').select2({
    closeOnSelect: true
});
document.addEventListener('DOMContentLoaded', function() {
    let x = document.getElementById("display-container");
    if (x!==null) {
        x.style.display = "flex";
    }
});

$(document).ready(function() {
    $('#input__file').on('change', function() {
        let formData = new FormData();
        let file = this.files[0];
        formData.append('protocol', file);

        $.ajax({
            type: 'POST',
            url: '/api/v1/deserialization-data',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                $('#uploadStatus').html('<p>' + response + '</p>');
            },
            error: function(response) {
                $('#uploadStatus').html('<p>File upload failed.</p>');
            }
        });
    });
});


$(document).ready(function() {
    $('#input__file__excel').on('change', function() {
        let formData = new FormData();
        let file = this.files[0];
        formData.append('excel', file);

        $.ajax({
            type: 'POST',
            url: '/api/v2/upload-excel',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                $('#uploadStatus').html('<p>' + response + '</p>');
            },
            error: function(response) {
                $('#uploadStatus').html('<p>File upload failed.</p>');
            }
        });
    });
});

$(document).ready(function() {
    $('#input__file__excel').on('change', function() {
        let formData = new FormData();
        let file = this.files[0];
        formData.append('excel', file);

        $.ajax({
            type: 'POST',
            url: '/api/v2/upload-excel',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                $('#uploadStatus').html('<p>' + response + '</p>');
            },
            error: function(response) {
                $('#uploadStatus').html('<p>File upload failed.</p>');
            }
        });
    });
});

$(document).ready(function() {
    $('#input__file__zip').on('change', function() {
        let formData = new FormData();
        let file = this.files[0];

        formData.append('zip', file);

        $.ajax({
            type: 'POST',
            url: '/api/v1/upload-zip-file',
            data: formData,
            contentType: false,
            processData: false,
            timeout: 600000,
            success: function(response) {
                $('#uploadStatus').html('<p>' + response + '</p>');
            },
            error: function(response) {
                $('#uploadStatus').html('<p>File upload failed.</p>');
            }
        });
    });
});

$('select[data-plugin="select2"]').select2({
    dropdownParent: $("#selectParent")
});