function showDetails() {
    const selection = document.getElementById('firstnames').value;
    const url = 'http://localhost:3000/contacts?firstName=' + selection;
    document.getElementById('details').innerHTML = '<p> Grabbing details for ' + selection + '</p>';

    // get details from web service
    const request = new XMLHttpRequest();
    request.open('GET', url, false);
    request.send();
    if (request.status === 200) {
        const result = JSON.parse(request.responseText);
        // process response
        const message = result.title + ' ' + result.firstName + ' ' + result.lastName + ' '
            + ' works at ' + result.company + ' as ' + result.jobTitle;
        document.getElementById('details').innerHTML = message;
    } else if (request.status === 404) {
        console.log('Error: Page not found!');
        document.getElementById('details').innerHTML = 'Error';
    } else {
        console.log('Error: ' + request.statusText);
        document.getElementById('details').innerHTML = 'Error';
    }
}

window.onload = function () {
    document.getElementById('firstnames').onchange = showDetails;
    showDetails(); // to initialize the page
}