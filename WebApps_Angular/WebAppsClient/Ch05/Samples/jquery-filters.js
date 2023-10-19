
$(document).ready(function () {

    $('ul:first li').eq(2).css('color', 'seagreen');
    $('ul:first li').eq(-1).css('color', 'coral');

    $('div')
        .find('li')
        .filter(function (index) {
            return index % 3 == 2;
        })
        .css({
            'font-style': 'italic',
            'color': 'goldenrod'
        });

    // All equivalent
    let spans = $('span');
    $('h2')
        .has(spans)
        // .has($('span'))
        // .has('span')
        .css({
            'font-family': 'sans-serif',
            'color': 'seagreen'
        });

    $('div')
        .not('.wisdom')
        .children()
        .css({
            'font-style': 'italic',
            'color': 'brown'
        });

});
