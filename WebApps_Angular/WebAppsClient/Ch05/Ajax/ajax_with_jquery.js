function showDetails() {
    const url = 'http://localhost:3000/contacts';
    const selection = $('#firstnames').val();
    const parameters = {
        firstName: selection
    };
    $('#details').text('<p> Grabbing details for ' + selection + '</p>');

    $.getJSON(url, parameters, onSuccess);
}

function onSuccess(result) {
    const message = result.title + ' ' + result.firstName + ' ' + result.lastName + ' '
        + ' works at ' + result.company + ' as ' + result.jobTitle;
    $('#details').text(message);
}

$(document).ready(() => {
    $('#firstnames').change(showDetails);
    showDetails(); // to initialize the page
});
