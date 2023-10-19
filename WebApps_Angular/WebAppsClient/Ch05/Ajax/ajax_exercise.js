
// TODO 1: define this constant as the appropriate URL for the service
const urlBase = '';

function showDetails() {
    // TODO 2: Get the selected value from the SELECT
    // TODO 2: If it is the empty value (the special value you added at the start of the SELECT), just clear the output
    // TODO 2: Send a request to the appropriate URL to get the contacts for that group
    // TODO 2: If the request succeeds, call an appropriate method to put the results into the details element
    // TODO 2: In the event of a failure, call the onAnyFailure method
}

function onAnyFailure(jqXHR, textStatus, errorThrown) {
    $('#details').text('Error');
    console.log('getJSON request failed: ' + textStatus + ' (' + jqXHR.status + ')');
}

function getGroupList() {
    // TODO 1: Send a request to the appropriate URL to get a list of groups
    // TODO 1: If the request succeeds, call an appropriate method that adds the results to the SELECT as OPTIONs
    // TODO 1: Add an initial OPTION that returns no value and says something like 'None selected'
    // TODO 1: In the event of a failure, call the onAnyFailure method
}

$(document).ready(() => {
    // Get the group list and populate the select
    getGroupList();
    // Set up the change event
    $('#groups').change(showDetails);
});
