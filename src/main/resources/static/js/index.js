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

