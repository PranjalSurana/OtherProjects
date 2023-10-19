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
