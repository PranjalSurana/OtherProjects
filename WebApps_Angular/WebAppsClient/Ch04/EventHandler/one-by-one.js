
function OneByOne(initialValue) {
    this.insertStanza = function () {
        let stanza = '<p>';
        if (numZips > 0) {
            stanza += numZips + ' little zipcodes jumping on the bed <br>';
            stanza += '  One fell down and broke his head. <br>';
            --numZips;
            if (numZips == 0) {
                stanza += 'And there were no more zipcodes jumping on the bed! <br>';
            }
        }
        stanza += '</p>';

        // append this stanza
        song.innerHTML += stanza;

        // change song title to reflect number of zip codes
        songTitle.innerHTML = numZips + ' zipcodes!';

        // if finished, indicate it ...
        if (numZips == 0) {
            songTitle.innerHTML = 'No more zipcodes!';
            songTitle.classList.add('highlight');
        }
    }

    this.reset = function () {
        let pref = parseInt(inputField.value);
        numZips = pref;
        song.innerHTML = `What happened next to the ${numZips} zipcodes? 
                (Move your mouse over this line of text)`;
        songTitle.classList.remove('highlight');
        songTitle.innerHTML = numZips + ' little zipcodes';
    }

    this.processChange = function () {
        let pref = parseInt(inputField.value);
        if (!isNaN(pref) && pref != numZips) {
            resetButton.classList.remove('hidden');
        } else {
            resetButton.classList.add('hidden');
        }
    }

    // constructor from here
    let numZips = initialValue;

    const song = document.getElementById('song');
    const songTitle = document.getElementById('song-title');
    const inputField = document.getElementById('num-zips');
    const resetButton = document.getElementById('reset');

    song.onmouseover = this.insertStanza;
    // TODO: add an event handler for the mouseout event
    // TODO: try replacing both event handlers with one for the click event

    inputField.value = 10;
    inputField.oninput = this.processChange;

    resetButton.onclick = this.reset;
    resetButton.classList.add('hidden');

    this.reset();
}

var oneByOne = new OneByOne(10);
