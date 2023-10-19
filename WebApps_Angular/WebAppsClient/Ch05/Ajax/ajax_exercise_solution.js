
// Done 1: define this constant as the appropriate URL for the service
const urlBase = 'http://localhost:3000/groups';

function showDetails() {
    // Done 2: Get the selected value from the SELECT
    const selection = $('#groups').val();
    // Done 2: If it is the empty value (the special value you added at the start of the SELECT), just clear the output
    if (selection == '') {
        $('#details').text('');
        return;
    }
    $('#details').html('<p> Grabbing details for ' + selection + '</p>');

    // Done 2: Send a request to the appropriate URL to get the contacts for that group
    $.getJSON(urlBase + '/' + selection)
        // Done 2: If the request succeeds, call an appropriate method to put the results into the details element
        // Done 2: In the event of a failure, call the onAnyFailure method
        .then(onDetailSuccess, onAnyFailure);
}

function onDetailSuccess(contacts) {
    let message = '';
    for (let contact of contacts) {
        message += contact.title + ' ' + contact.firstName + ' ' + contact.lastName + ' '
            + ' works at ' + contact.company + ' as ' + contact.jobTitle + '<br>';
    }
    $('#details').html(message);
}

// Bonus version of the method that creates a table
//
// function onDetailSuccess(contacts) {
//     let message = '<table><tr><th>Name</th><th>Company</th><th>Job Title</th></tr>';
//     for (let contact of contacts) {
//         // Can do this with concatenation as well
//         message += `
//             <tr>
//                 <td>${contact.title} ${contact.firstName} ${contact.lastName}</td>
//                 <td>${contact.company}</td>
//                 <td>${contact.jobTitle}</td>
//             </tr>`
//     }
//     message += '</table>';
//     $('#details').html(message);
// }

function onAnyFailure(jqXHR, textStatus, errorThrown) {
    $('#details').text('Error');
    console.log('getJSON request failed: ' + textStatus + ' (' + jqXHR.status + ')');
}

function getGroupList() {
    // Done 1: Send a request to the appropriate URL to get a list of groups
    // Done 1: If the request succeeds, call an appropriate method that adds the results to the SELECT as OPTIONs
    // Done 1: In the event of a failure, call the onAnyFailure method
    $.getJSON(urlBase)
        .then(onListSuccess, onAnyFailure);
}

function onListSuccess(groups) {
    // Done 1: Add an initial OPTION that returns no value and says something like 'None selected'
    let result = '<option value="">(None selected)</option>';
    for (let group of groups) {
        result += '<option>' + group + '</option>';
    }
    $('#groups').html(result);
}

$(document).ready(() => {
    // Get the group list and populate the select
    getGroupList();
    // Set up the change event
    $('#groups').change(showDetails);
});
