const fs = require('fs');
const http = require('http');
const server = http.createServer( (req, res) => {
    fs.readFile('content.html', (err, fileData) => {
        res.writeHead(200, { 'Content-Type': 'text/html'});
        res.write(fileData);
        res.end();
    });
});
server.listen(8081);