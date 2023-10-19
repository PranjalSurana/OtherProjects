function showDetails() {
    const selection = document.getElementById('firstnames').value;
    const url = 'http://localhost:3000/contacts?firstName=' + selection;
    document.getElementById('details').innerHTML = '<p> Grabbing details for ' + selection + '</p>';

    // get details from web service
    const request = new XMLHttpRequest();
    request.open('GET', url, true);
    request.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                const result = JSON.parse(this.responseText);
                // process response
                const message = result.title + ' ' + result.firstName + ' ' + result.lastName + ' '
                    + ' works at ' + result.company + ' as ' + result.jobTitle;
                document.getElementById('details').innerHTML = message;
            } else if (request.status === 404) {
                console.log('Error: Page not found!');
                document.getElementById('details').innerHTML = 'Error';
            } else {
                console.log('Error: ' + this.statusText + '(' + this.status + ')');
                document.getElementById('details').innerHTML = 'Error';
            }
        } else {
            console.log('readyState: ' + this.readyState + ' status: ' + this.status + ' statusText: ' + this.statusText);
            document.getElementById('details').innerHTML = 'Request in progress';
        }
    }
    request.send();

}

window.onload = function () {
    document.getElementById('firstnames').onchange = showDetails;
    showDetails(); // to initialize the page
}