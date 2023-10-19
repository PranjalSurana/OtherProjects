
$(() => {
    $('#same-origin').change(() => {
        processCheckbox('#same-origin', 'allow-same-origin')
    });
    $('#popups').change(() => {
        processCheckbox('#popups', 'allow-popups')
    });
    $('#top-nav').change(() => {
        processCheckbox('#top-nav', 'allow-top-navigation')
    });
    /*
     * Double-check these when re-loading a page after navigation as
     * the check-boxes may still be set, depending on how the exact
     * type of navigation.
     * 
     * This is still only a partial solution as these need to be set
     * before the frame is loaded and that may not always happen
     */
    processCheckbox('#same-origin', 'allow-same-origin')
    processCheckbox('#popups', 'allow-popups')
    processCheckbox('#top-nav', 'allow-top-navigation')
});

function processCheckbox(selector, sandbox) {
    if ($(selector).prop('checked')) {
        $('#frame3')[0].sandbox.add(sandbox);
    } else {
        $('#frame3')[0].sandbox.remove(sandbox);
    }
}
