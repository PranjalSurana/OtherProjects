const cors = require('cors');
const express = require('express');

const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const port = 9876;

const todos = {
    1: {
        "id": 1,
        "title":"Wash car",
        "description":"Go to Scrub 'n Bubbles", 
        "completed":false
    },
    2: {
        "id": 2,
        "title":"Send birthday card to BeauJo",
        "description":"Find a funny Jib-Jab", 
        "completed":false
    },
    3: {
        "id": 3,
        "title":"Run a marathon",
        "description":"Start slow", 
        "completed":false
    }
};

let nextIndex = 4;

app.use(cors());

app.get('/todos/:id', (req, res) => {
    const id = req.params.id;
    console.log(`GET ${id}`);
    if (!todos.hasOwnProperty(id)) {
        return res.sendStatus(404);
    }
    res.setHeader('Content-Type', 'application/json');
    return res.status(200).send(todos[req.params.id]);
});

app.post('/todos', (req, res) => {
    console.log(`POST ${req.body}`);
    todoItem = req.body;
    todoItem.id = nextIndex;
    todos[nextIndex] = todoItem;
    nextIndex++;
    res.setHeader('Content-Type', 'application/json');
    return res.status(201).send(todoItem);
});

app.put('/todos/:id', (req, res) => {
    const id = req.params.id;
    console.log(`PUT ${id}, ${req.body}`);
    if (!todos.hasOwnProperty(id)) {
        return res.sendStatus(404);
    }
    todoItem = req.body;
    todos[id] = todoItem;
    res.setHeader('Content-Type', 'application/json');
    return res.status(202).send(todoItem);
});

app.delete('/todos/:id', (req, res) => {
    const id = req.params.id;
    console.log(`DELETE ${id}`);
    if (!todos.hasOwnProperty(id)) {
        return res.sendStatus(404);
    }
    delete todos[id];
    return res.sendStatus(204);
});

app.listen(port, () =>
    console.log(`To-do list REST server listening on port ${port}`)
);
