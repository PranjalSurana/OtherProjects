
const toControl = document.getElementById('to');
const fromControl = document.getElementById('from');

function validate() {
    const fromDate = new Date(fromControl.value);
    const toDate = new Date(toControl.value);
    if (toDate <= fromDate) {
        toControl.setCustomValidity('Must be later than the "from" date');
    }
}

function resetValidity() {
    toControl.setCustomValidity('');
}

document.getElementsByTagName('button')[0].onclick = validate;
toControl.oninput = resetValidity;
fromControl.oninput = resetValidity;
