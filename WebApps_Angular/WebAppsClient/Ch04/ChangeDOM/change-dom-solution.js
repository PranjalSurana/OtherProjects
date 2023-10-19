function init() {
    document.getElementById('song').innerHTML = createSong(10);

    // change title
    var title = document.getElementsByTagName('title')[0];
    title.childNodes[0].nodeValue = 'No more zipcodes!';

    // remove the separator
    var sep = document.getElementById('separator');
    sep.parentNode.removeChild(sep);

    // change the CSS style
    document.getElementById('songtitle').classList.add('highlight');

    // add a new section containing MOTD
    insertMOTD('motd');
}

/*
 * By creating a new function and calling it from the existing onload
 * function, we can have our cake and eat it too, or have our song
 * and our uplifting message.
 * 
 * Note two things here:
 * - the use of backticks, which allow the string to span lines
 *   (a relatively modern feature, available in about 90% of browsers)
 * - putting the newline in the tag rather than the text 
 *   (ensuring no extra newline in the page)
 */
function insertMOTD(element) {
    var element = document.getElementById('motd');
    element.innerHTML = `<p><span style="font-style: italic"
        >"A successful man is one who can lay a firm foundation with the bricks others have thrown at him."</span
        > – David Brinkley</p>`;
}

function createSong(zipCount) {
    var song = '';
    while (zipCount > 0) {
        song += zipCount + ' little zipcodes jumping on the bed<br>';
        song += ' one fell down and broke his head.<br>';
        --zipCount;
    }
    song += 'No more zipcodes jumping on the bed<br>';
    return song;
}

window.onload = init;
