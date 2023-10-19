
function processDOM() {
    /*
     * Notice that the elements logged to the console are "live", so they will 
     * adopt the classes even though they are logged before the classes are applied
     */
    var output = '<table><tr><th>Item</th><th>Value</th></tr>';
    var element = document.getElementById('this_one');
    output += recordNode('parentNode', element.parentNode);
    output += recordElement('parentElement', element.parentElement);
    element.parentElement.classList.add('parent-element');
    console.log('childNodes');
    console.log(element.childNodes);
    output += addTableRow('childNodes.length', element.childNodes.length);
    output += recordNode('firstChild', element.firstChild);
    output += recordElement('firstElementChild', element.firstElementChild);
    element.firstElementChild.classList.add('first-child');
    output += recordNode('lastChild', element.lastChild);
    output += recordElement('lastElementChild', element.lastElementChild);
    output += recordNode('nextSibling', element.nextSibling);
    output += recordElement('nextElementSibling', element.nextElementSibling);
    element.nextElementSibling.classList.add('next_sibling');
    output += recordNode('previousSibling', element.previousSibling);
    output += recordElement('previousElementSibling', element.previousElementSibling);
    element.previousElementSibling.classList.add('previous_sibling');

    output += '</table>';
    document.getElementById('output').innerHTML = output;
}

function decodeNodeType(node) {
    return [
        'Element',
        'Attribute',
        'Text',
        'CData',
        'Entity Reference',
        'Entity',
        'Processing Instruction',
        'Comment',
        'Document',
        'Document Type',
        'Document Fragment',
        'Notation'
    ][node.nodeType - 1];
}

function recordElement(name, element) {
    console.log('Element ' + name);
    console.log(element);
    return addTableRow(name + ' tag', element.tagName);
}

function addTableRow(item, value) {
    return '<tr><td>' + item + '</td><td>' + value + '</td></tr>';
}

// For text nodes add the value, otherwise leave it out as it could be huge
function recordNode(name, node) {
    console.log('Node: ' + name);
    console.log(node);
    return addTableRow(name + '.nodeName', node.nodeName)
        + addTableRow(name + '.nodeType', decodeNodeType(node))
        + (node.nodeType == 3
            ? addTableRow(name + '.nodeValue', node.nodeValue) + addTableRow(name + '.nodeValue (hex)', node.nodeValue.hexEncode())
            : '');
}

// This only works for UTF-8 characters. Code point is really 4 hex digits, 
// but here we will represent as 2 for brevity.
String.prototype.hexEncode = function () {
    var hex, i;
    var result = '';
    for (i = 0; i < this.length; i++) {
        hex = this.charCodeAt(i).toString(16);
        result += ('00' + hex).slice(-2);
    }
    return result
}

window.onload = processDOM;
