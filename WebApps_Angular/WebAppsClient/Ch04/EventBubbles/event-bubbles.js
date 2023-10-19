
function init() {
    let divs = document.getElementsByTagName('div');
    for (let i = 0; i < divs.length; i++) {
        divs[i].onclick = function (e) {
            e = e || event;
            let target = e.target || e.srcElement;
            console.log('target=' + target.id + ', this=' + this.id);
        }
    }
}

init();
