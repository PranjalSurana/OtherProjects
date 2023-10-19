const fs = require('fs');

const defaultSortKey = 'Ticker';

let read_json_file = () => {
    let file = './data/etfs.json';
    return fs.readFileSync(file);
}

exports.listAll = () => {
    return JSON.parse(read_json_file());
};

exports.filter_by_arg = (arg) => {
    let json_result = JSON.parse(read_json_file());

    console.log("filter by arg: " + arg );
    if (!arg) {
        arg = defaultSortKey;
    }

    //otherwise try to sort
    if (json_result[0][arg] == undefined) {
        return null;
    }
    // now that we know the argument exists, sort it.
    // sort ticker symbols in ascending order, everything else in descending order.
    json_result.sort((a,b) => (arg == defaultSortKey ? a[arg] > b[arg] : a[arg] < b[arg]) ? 1 : -1); 
    // now we resize
    json_result.length = 5;

    return json_result;

};