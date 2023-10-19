let express = require('express');
let app = express();
app.get('/', (req, res) => {
    res.send('Hello Express');
});
app.listen(8081, () => {
    console.log('App listening on port 8081');
});