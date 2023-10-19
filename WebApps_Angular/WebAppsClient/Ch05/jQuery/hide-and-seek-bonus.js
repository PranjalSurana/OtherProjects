
function notify() {
    if ($('#notify').prop('checked')) {
        alert('Complete for: ' + $(this).text());
    }
    console.log('Complete for: ' + $(this).text());
}

function init() {
    $('.hide').click(function () {
        $('div').hide();
    });
    $('.show').click(function () {
        $('div').show();
    });
    /*
     * Note that unlike the other animation functions, toggle behaves differently with a completion
     * function. Without a function, it instantly shows or hides the element(s). With a completion
     * function, it simultaneously animates the width, height and opacity of the element(s) before
     * hiding the element(s) completely (if this is a hide operation).
     */
    $('#toggle').click(function () {
        $('div').toggle();
    });
    $('#togglenotify').click(function () {
        $('div').toggle(notify);
    });
    $('#slidetoggle').click(function () {
        $('div').slideToggle(notify);
    });
    $('#slidetoggleslow').click(function () {
        $('div').slideToggle('slow', notify);
    });
    $('#slidetogglevslow').click(function () {
        $('div').slideToggle(1000, notify);
    });
    $('#fadetoggle').click(function () {
        $('div').fadeToggle(notify);
    });
    $('#fadetoggleslow').click(function () {
        $('div').fadeToggle('slow', notify);
    });
    $('#fadetogglevslow').click(function () {
        $('div').fadeToggle(1000, notify);
    });

    // You can only animate pure numeric values unless you add a plug-in, such as the jQuery.Color plugin.
    $('#animate').click(function () {
        $('div').animate({
            fontSize: "2rem",
            fontWeight: "900",
            height: "+=30",
            outlineWidth: "5px"
        }, 1000, notify);
    });
    $('#undo').click(function () {
        $('div').animate({
            fontSize: "1rem",
            fontWeight: "400",
            height: "-=30",
            outlineWidth: "1px"
        }, 1000, notify);
    });
}

$(document).ready(function () {
    init()
});
