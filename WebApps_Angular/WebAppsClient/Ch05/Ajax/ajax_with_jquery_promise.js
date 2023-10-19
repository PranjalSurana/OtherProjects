function showDetails() {
    const url = 'http://localhost:3000/contacts';
    const selection = $('#firstnames').val();
    const parameters = {
        firstName: selection
    };
    $('#details').text('<p> Grabbing details for ' + selection + '</p>');

    $.getJSON(url, parameters)
        .done(onSuccess)
        .fail(onFailure)
        .always(onCompletion);

    // $.getJSON(url, parameters)
    //     .then(onSuccess, onFailure)
    //     .always(onCompletion);

}

function onSuccess(result) {
    const message = result.title + ' ' + result.firstName + ' ' + result.lastName + ' '
        + ' works at ' + result.company + ' as ' + result.jobTitle;
    $('#details').text(message);
}

function onFailure(jqXHR, textStatus, errorThrown) {
    $('#details').text('Error');
    console.log('getJSON request failed: ' + textStatus + ' (' + jqXHR.status + ')');
    console.log(errorThrown);
}

function onCompletion() {
    console.log('getJSON request ended');
}

$(document).ready(() => {
    $('#firstnames').change(showDetails);
    showDetails(); // to initialize the page
});
