
function validate() {
    const fromDate = new Date($('#from').val());
    const toDate = new Date($('#to').val());
    if (toDate <= fromDate) {
        $('#to').get(0).setCustomValidity('Must be later than the "from" date');
    }
}

function resetValidity() {
    $('#to').get(0).setCustomValidity('');
}

function applyColorChange() {
    $('h1').addClass('color-change');
}

function removeColorChange() {
    $('h1').removeClass('color-change');
}

function init() {
    $('button').click(validate);
    $('input[type="date"]').on('input', resetValidity);
    // bonus
    $('button').hover(applyColorChange, removeColorChange);
}

$(document).ready(init());

