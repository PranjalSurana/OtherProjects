function getLoopLength() {
    var loopLength = 0;
    var response = prompt('How many zipcodes?', '10');
    if (response != null && response != '') {
        loopLength = parseInt(response);
    }
    return loopLength;
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

function insertSong() {
    numZips = getLoopLength();
    document.getElementById('song').innerHTML = createSong(numZips);
}

function insertNewSong() {
    var song = '';
    // an alternative so you can continuously change the song on onclick 
    if (counter % 2 === 0) {
        song = 'This is an alternative song.<br>This is an alternative song.<br>This is an alternative song.<br>';
    } else {
        song = createSong(numZips);
    }
    document.getElementById('song').innerHTML = song;
    counter++;
}

// global variables 
var counter = 0;
var numZips = 0;

// or on a different event (onmouseover)
// normally we wouldn't do this and also set it in the HTML, but this works as an example
window.onload = insertSong;
document.getElementById('clickMe').onmouseover = insertNewSong;
