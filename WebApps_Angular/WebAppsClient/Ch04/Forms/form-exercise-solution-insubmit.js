
const toControl = document.getElementById('to');
const fromControl = document.getElementById('from');

function validate() {
    const fromDate = new Date(fromControl.value);
    const toDate = new Date(toControl.value);
    if (toDate <= fromDate) {
        document.getElementById('error').classList.remove('hidden');
        return false;
    }
    return true;
}

function processChange() {
    document.getElementById('error').classList.add('hidden');
    validate();
}

function checkSubmit(e) {
    if (!validate()) {
        e.preventDefault();
    }
}

document.getElementsByTagName('form')[0].onsubmit = checkSubmit;
toControl.onchange = processChange;
fromControl.onchange = processChange;
