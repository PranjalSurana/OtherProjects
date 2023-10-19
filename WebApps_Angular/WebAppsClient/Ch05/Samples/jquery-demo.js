
$(document).ready(function () {
    $('button').click(function () {
        $('div').hide();
        $('.song').addClass('highlight')
            .show();
        $('#photo').attr('src', 'images/photo.jpg');
    });

    $('img').on('click', function () {
        console.log('clicked on an image');
    });

    $('div').on({
        mouseenter: function () {
            console.log('mouse entered a div');
        },
        mouseleave: function () {
            console.log('mouse left a div');
        },
        click: function () {
            console.log('clicked on a div');
        }
    });
});
