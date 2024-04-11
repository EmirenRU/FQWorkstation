$(document).ready(function() {
    $('.slider-val').select2({ // Changed selector to match the class in HTML
        placeholder: $('.slider-val').data('placeholder'), // Set placeholder text
        tags: true // Enable tagging/searching for options
    });
});