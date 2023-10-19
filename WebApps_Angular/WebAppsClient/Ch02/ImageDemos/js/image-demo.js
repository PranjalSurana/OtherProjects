
$(document).ready(function () {
    init();
});

function init() {
    $('img').hover(function () {
        $('.imgContainer1 img').animate({
            'width': '5024px',
            'height': '2304px',
            'top': '-1000px',
            'left': '-4360px'
        }, 300);
        $('.imgContainer2 img').animate({
            'width': '5024px',
            'height': '2304px',
            'top': '-400px',
            'left': '-1200px'
        }, 300);
    }, function () {
        $('img').animate({
            'width': '471px',
            'height': '216px',
            'top': '0px',
            'left': '0px'
        }, 300);
    });
}
