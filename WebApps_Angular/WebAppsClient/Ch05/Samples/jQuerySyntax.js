
// ready event
$(document).ready(function () {

    // jQuery methods go here...

});

$(function () {
    // jQuery methods go here...
});


// Event handlers
$(document).ready(function () {
    $('button').click(function () {
        $('div').hide();
    });
});

function clickHandler() {
    $('div').hide();
}

$(document).ready(function () {
    $('button').click(clickHandler);
});

// Forms serialize
const str = $('#contactform').serialize();
