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

function insertNewSong(e) {
    var song = '';
    console.log(e);
    // an alternative so you can continuously change the song on onclick 
    if (counter % 2 === 0) {
        song = 'This is an alternative song.<br>This is an alternative song.<br>This is an alternative song.<br>';
    } else {
        song = createSong(numZips);
    }
    document.getElementById('song').innerHTML = song;
    counter++;
}

function insertNewerSong(parm) {
    // an alternative so you can continuously change the song on onclick 
    // this version takes a parameter
    var song = 'Second div was clicked and passed in ' + parm + '<br>';
    if (counter % 2 === 0) {
        song += 'This is an alternative song.<br>Created by clicking the second div<br>';
    } else {
        song += createSong(numZips);
    }
    document.getElementById('song').innerHTML = song;
    counter++;
}

// global variables 
var counter = 0;
var numZips = 0;
const param = 2;

// or on a different event (onmouseover)
// normally we wouldn't do this and also set it in the HTML, but this works as an example
window.onload = insertSong;
document.getElementById('clickMe1').onclick = insertNewSong;
document.getElementById('clickMe2').onclick = function(e) {
    console.log(e);
    insertNewerSong(param);
}
